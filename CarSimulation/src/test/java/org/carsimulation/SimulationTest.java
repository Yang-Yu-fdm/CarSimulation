package org.carsimulation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SimulationTest {

    private Simulation simulation;

    @Mock
    private Grid grid;

    @BeforeEach
    void setup() {
        simulation = new Simulation(grid);
    }

    @Test
    void addSingleCar() {
        Car toyotaCar = new Car("toyota", new Grid.Coordinate(0, 0), FaceDirection.NORTH, List.of(MoveInstruction.FORWARD));

        simulation.addCar(toyotaCar);

        verify(grid).addToGrid(Mockito.any(Car.class));
        assertEquals(toyotaCar, simulation.getPreSimulationCars().getFirst());
    }

    @Test
    void addMultipleCarsToSameSpot() {
        Car toyotaCar = new Car("toyota", new Grid.Coordinate(0, 0), FaceDirection.NORTH, List.of(MoveInstruction.FORWARD));
        Car hondaCar = new Car("honda", new Grid.Coordinate(0, 0), FaceDirection.NORTH, List.of(MoveInstruction.FORWARD));
        List<Car> expected = List.of(toyotaCar, hondaCar);

        simulation.addCar(toyotaCar);
        simulation.addCar(hondaCar);

        verify(grid, times(2)).addToGrid(Mockito.any(Car.class));
        assertEquals(expected, simulation.getPreSimulationCars());
    }

    @Test
    void runSimulationSingleCar() {
        Car toyotaCar = new Car("toyota", new Grid.Coordinate(1, 2), FaceDirection.NORTH,
                List.of(MoveInstruction.FORWARD, MoveInstruction.FORWARD,
                        MoveInstruction.RIGHT, MoveInstruction.FORWARD,
                        MoveInstruction.FORWARD, MoveInstruction.FORWARD,
                        MoveInstruction.FORWARD, MoveInstruction.RIGHT,
                        MoveInstruction.RIGHT, MoveInstruction.FORWARD,
                        MoveInstruction.LEFT));
        simulation.addCar(toyotaCar);
        when(grid.hasCollision(Mockito.any(Car.class))).thenReturn(false);
        when(grid.isValidCoordinate(Mockito.any(Grid.Coordinate.class))).thenReturn(true);

        simulation.run();

        assertAll("Position and direction car is facing should change",
                () -> assertEquals(new Grid.Coordinate(4, 4), toyotaCar.getCurrentPosition()),
                () -> assertEquals(FaceDirection.SOUTH, toyotaCar.getFaceDirection()));
        assertAll("Should have no collision",
                () -> assertEquals(0, toyotaCar.getCollionStep()),
                () -> assertNull(toyotaCar.getCollidedCar()));
    }

    @Test
    void runSimulationMultipleCarsWithCollision() {
        Car toyotaCar = new Car("toyota", new Grid.Coordinate(0, 0), FaceDirection.NORTH, List.of(MoveInstruction.FORWARD));
        Car hondaCar = new Car("honda", new Grid.Coordinate(0, 2), FaceDirection.SOUTH, List.of(MoveInstruction.FORWARD));
        simulation.addCar(toyotaCar);
        simulation.addCar(hondaCar);
        when(grid.hasCollision(Mockito.any(Car.class))).thenReturn(false, true);
        when(grid.isValidCoordinate(Mockito.any(Grid.Coordinate.class))).thenReturn(true);
        when(grid.getFirstCarFromCoordinate(Mockito.any(Grid.Coordinate.class))).thenReturn(toyotaCar);

        simulation.run();

        assertAll("Both cars should collide",
                () -> assertEquals(1, toyotaCar.getCollionStep()),
                () -> assertEquals(1, hondaCar.getCollionStep()),
                () -> assertEquals(toyotaCar, hondaCar.getCollidedCar()),
                () -> assertEquals(hondaCar, toyotaCar.getCollidedCar()));
    }

    @Test
    void runSimulationInvalidCarMovement() {
        Car toyotaCar = new Car("toyota", new Grid.Coordinate(0, 0), FaceDirection.SOUTH, List.of(MoveInstruction.FORWARD));
        simulation.addCar(toyotaCar);
        when(grid.isValidCoordinate(Mockito.any(Grid.Coordinate.class))).thenReturn(false);
        when(grid.hasCollision(Mockito.any(Car.class))).thenReturn(false);

        simulation.run();

        assertEquals(new Grid.Coordinate(0, 0), toyotaCar.getCurrentPosition());
    }
}