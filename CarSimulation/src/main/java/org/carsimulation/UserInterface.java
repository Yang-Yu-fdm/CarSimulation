package org.carsimulation;

import java.util.List;
import java.util.Scanner;

public class UserInterface {

    private Scanner scanner;

    public UserInterface(Scanner scanner) {
        this.scanner = scanner;
    }

    public String displayIntroduction() {
        System.out.println("""
            Welcome to Auto Driving Car Simulation!
                        
            Please enter the width and height of the simulation field in x y format:""");
        return scanner.nextLine().trim();
    }

    public void displayGridSize(int width, int height) {
        System.out.printf("You have created a field of %s x %s.%n", width, height);
    }

    public String displaySimulationConfig() {
        System.out.println("""
                \nPlease choose from the following options:
                [1] Add a car to field
                [2] Run simulation
                """);
        return scanner.nextLine().trim();
    }

    public String displayQuestionOnCarName() {
        System.out.println("Please enter the name of the car:");
        return scanner.nextLine().trim();
    }

    public String displayQuestionOnCarDirection(String carName) {
        System.out.printf("Please enter initial position of car %s in x y Direction format:%n", carName);
        return scanner.nextLine().trim();
    }

    public String displayQuestionOnCarInstructions(String carName) {
        System.out.printf("Please enter the commands for car %s:%n", carName);
        return scanner.nextLine().trim();
    }

    public void displayListOfCars(List<Car> cars) {
        System.out.println("Your current list of cars are:");
        cars.forEach(car -> System.out.printf("- %s, (%d,%d) %s, %s%n",
                car.getName(),
                car.getCurrentPosition().getX(),
                car.getCurrentPosition().getY(),
                car.getFaceDirection().getAlias(),
                MoveInstruction.fromMoveInstructions(car.getInstructions())));
    }

    public void displayPostSimulationCars(List<Car> cars) {
        System.out.println("\nAfter simulation, the result is:");
        cars.forEach(car -> {
            if (car.getCollionStep() > 0) {
                System.out.printf("- %s, collides with %s at (%d, %d) at step %d%n",
                        car.getName(),
                        car.getCollidedCar().getName(),
                        car.getCurrentPosition().getX(),
                        car.getCurrentPosition().getY(),
                        car.getCollionStep());
            } else {
                System.out.printf("- %s, (%d,%d) %s%n",
                        car.getName(),
                        car.getCurrentPosition().getX(),
                        car.getCurrentPosition().getY(),
                        car.getFaceDirection().getAlias());
            }
        });
        System.out.println();
    }

    public String displayStartOverOption() {
        System.out.println("Please choose from the following options:\n" +
                "[1] Start over\n" +
                "[2] Exit");
        return scanner.nextLine().trim();
    }

    public void displayInputError(String errorMessage) {
        System.out.println("Invalid Input:");
        System.out.println(errorMessage);
        System.out.println("Please try again.\n");
    }
}