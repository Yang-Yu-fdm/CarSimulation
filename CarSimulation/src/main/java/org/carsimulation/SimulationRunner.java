package org.carsimulation;

import java.util.List;

public class SimulationRunner {

    private Simulation simulation;
    private UserInterface userInterface;

    public SimulationRunner(UserInterface userInterface) {
        this.userInterface = userInterface;
    }

    public void start() {
        while (true) {

            String[] gridSizeInput = userInterface.displayIntroduction().split(" ");
            int width = Integer.parseInt(gridSizeInput[0]);
            int height = Integer.parseInt(gridSizeInput[1]);
            if (!Grid.isValidGrid(width, height)) {
                userInterface.displayInputError("Invalid grid width and/or height.");
                continue;
            }
            this.simulation = new Simulation(
                    new Grid(width, height)
            );

            userInterface.displayGridSize(width, height);

            while (true) {
                String userInputOption = userInterface.displaySimulationConfig();
                if ("1".equals(userInputOption)) {

                    String carName = userInterface.displayQuestionOnCarName();
                    String[] carDirectionInput = userInterface.displayQuestionOnCarDirection(carName).split(" ");
                    int xCoord = Integer.parseInt(carDirectionInput[0]);
                    int yCoord = Integer.parseInt(carDirectionInput[1]);
                    FaceDirection faceDirection = FaceDirection.fromString(carDirectionInput[2]);
                    List<MoveInstruction> instructions = MoveInstruction.fromString(
                            userInterface.displayQuestionOnCarInstructions(carName)
                    );

                    if (!simulation.getGrid().isValidCoordinate(new Grid.Coordinate(xCoord, yCoord))) {
                        userInterface.displayInputError("Coordinates (%d, %d) out of bounds for grid size (%d, %d)".formatted(
                                xCoord, yCoord, width-1, height-1));
                        continue;
                    }
                    simulation.addCar(new Car(carName, new Grid.Coordinate(xCoord, yCoord), faceDirection, instructions));
                    userInterface.displayListOfCars(simulation.getPreSimulationCars());
                } else if ("2".equals(userInputOption)){

                    simulation.run();
                    userInterface.displayListOfCars(simulation.getPreSimulationCars());
                    userInterface.displayPostSimulationCars(simulation.getPostSimulationCars());

                    userInputOption = userInterface.displayStartOverOption();
                    if ("1".equals(userInputOption)) {
                        break;
                    } else if ("2".equals(userInputOption)) {
                        return;
                    } else {
                        userInterface.displayInputError("Option %s is not available.".formatted(userInputOption));
                    }
                } else {
                    userInterface.displayInputError("Option %s is not available.".formatted(userInputOption));
                }
            }
        }
    }
}