package org.carsimulation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SimulationRunnerTest {

    private SimulationRunner simulationRunner;

    @Mock
    private UserInterface userInterface;

    @Mock
    private Grid grid;

    @BeforeEach
    void setup() {
        simulationRunner = new SimulationRunner(userInterface);
    }

    @Test
    void shouldRunEntireSimulationAndExit() {
        try (MockedStatic<Grid> mockStaticGrid = mockStatic(Grid.class)){
            when(userInterface.displayIntroduction()).thenReturn("10 10");
            mockStaticGrid.when(() -> Grid.isValidGrid(10, 10)).thenReturn(true);
            when(userInterface.displaySimulationConfig()).thenReturn("1","1", "2");
            when(userInterface.displayQuestionOnCarName()).thenReturn("toyota", "honda");
            when(userInterface.displayQuestionOnCarDirection("toyota")).thenReturn("1 2 N");
            when(userInterface.displayQuestionOnCarDirection("honda")).thenReturn("7 8 W");
            when(userInterface.displayQuestionOnCarInstructions("toyota")).thenReturn("FFRFFFFRRL");
            when(userInterface.displayQuestionOnCarInstructions("honda")).thenReturn("FFLFFFFFFF");
            when(userInterface.displayStartOverOption()).thenReturn("2");

            simulationRunner.start();

            verify(userInterface).displayGridSize(10, 10);
            verify(userInterface, times(3)).displayListOfCars(anyList());
            verify(userInterface).displayPostSimulationCars(anyList());
        }
    }

    @Test
    void shouldDisplayErrorOnInvalidGridSize() {
        try (MockedStatic<Grid> mockStaticGrid = mockStatic(Grid.class)){
            when(userInterface.displayIntroduction()).thenReturn("0 10", "10 10");
            mockStaticGrid.when(() -> Grid.isValidGrid(0, 10)).thenReturn(false);
            mockStaticGrid.when(() -> Grid.isValidGrid(10, 10)).thenReturn(true);
            when(userInterface.displaySimulationConfig()).thenReturn("2");
            when(userInterface.displayStartOverOption()).thenReturn("2");

            simulationRunner.start();

            verify(userInterface, times(2)).displayIntroduction();
            verify(userInterface).displayInputError(anyString());
            verify(userInterface).displayGridSize(10, 10);
        }
    }

    @Test
    void shouldDisplayErrorOnInvalidCarPosition() {
        try (MockedStatic<Grid> mockStaticGrid = mockStatic(Grid.class)){
            when(userInterface.displayIntroduction()).thenReturn("10 10");
            mockStaticGrid.when(() -> Grid.isValidGrid(10, 10)).thenReturn(true);
            when(userInterface.displaySimulationConfig()).thenReturn("1","1", "2");
            when(userInterface.displayQuestionOnCarName()).thenReturn("toyota");
            when(userInterface.displayQuestionOnCarDirection(anyString())).thenReturn("1 -1 N", "7 2 N");
            when(userInterface.displayQuestionOnCarInstructions(anyString())).thenReturn("FFRFFFFRRL");
            when(userInterface.displayStartOverOption()).thenReturn("2");

            simulationRunner.start();

            verify(userInterface).displayGridSize(10, 10);
            verify(userInterface).displayInputError(anyString());
            verify(userInterface, times(2)).displayListOfCars(anyList());
            verify(userInterface).displayPostSimulationCars(anyList());
        }
    }
}