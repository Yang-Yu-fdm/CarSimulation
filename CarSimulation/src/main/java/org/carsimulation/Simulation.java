package org.carsimulation;

import java.util.ArrayList;
import java.util.List;

public class Simulation {

    private List<Car> preSimulationCars = new ArrayList<>();
    private List<Car> postSimulationCars = new ArrayList<>();
    private Grid grid;

    public Simulation(Grid grid) {
        this.grid = grid;
    }

    public void run() {
        List<Car> currentSimulationCars = new ArrayList<>(postSimulationCars);
        int currentInstructionStep = 1;
        while (!currentSimulationCars.isEmpty()) {
            List<Car> inactiveCars = new ArrayList<>();
            for (int i=0; i<currentSimulationCars.size(); i++) {
                Car car = currentSimulationCars.get(i);
                if (car.getInstructions().size() < currentInstructionStep || car.getCollionStep() > 0){
                    inactiveCars.add(car);
                } else {
                    moveCar(car, currentInstructionStep-1);
                }
            }

            for (Car inactiveCar : inactiveCars) {
                currentSimulationCars.remove(inactiveCar);
            }
            currentInstructionStep++;
        }
    }

    private void moveCar(Car car, int instructionStep) {
        MoveInstruction instruction = car.getInstructions().get(instructionStep);
        switch (instruction) {
            case LEFT -> car.setFaceDirection(
                    FaceDirection.fromBearingChange(car.getFaceDirection(), -90.0));
            case RIGHT -> car.setFaceDirection(
                    FaceDirection.fromBearingChange(car.getFaceDirection(), 90.0));
            case FORWARD -> {
                moveForward(car);
                if (grid.hasCollision(car)) {
                    car.setCollionStep(instructionStep+1);
                    Car collidedCar = grid.getFirstCarFromCoordinate(car.getCurrentPosition());
                    car.setCollidedCar(collidedCar);
                    if (collidedCar.getCollidedCar() == null && collidedCar.getCollionStep() == 0) {
                        collidedCar.setCollidedCar(car);
                        collidedCar.setCollionStep(instructionStep+1);
                    }
                }
            }
        }
    }

    private void moveForward(Car car) {
        int xCoord = car.getCurrentPosition().getX();
        int yCoord = car.getCurrentPosition().getY();
        Grid.Coordinate newCoord = new Grid.Coordinate(xCoord, yCoord);
        switch (car.getFaceDirection()) {
            case NORTH -> newCoord.setY(yCoord + 1);
            case SOUTH -> newCoord.setY(yCoord - 1);
            case EAST -> newCoord.setX(xCoord + 1);
            case WEST -> newCoord.setX(xCoord - 1);
        }
        if (grid.isValidCoordinate(newCoord)) {
            grid.removeFromGrid(car);
            car.setCurrentPosition(newCoord);
            grid.addToGrid(car);
        }
    }

    public void addCar(Car dynamicStateCar) {
        Car initalStateCar = new Car(dynamicStateCar.getName(),
                                     dynamicStateCar.getCurrentPosition(),
                                     dynamicStateCar.getFaceDirection(),
                                     dynamicStateCar.getInstructions());
        preSimulationCars.add(initalStateCar);
        postSimulationCars.add(dynamicStateCar);
        grid.addToGrid(dynamicStateCar);
    }

    public List<Car> getPreSimulationCars() {
        return preSimulationCars;
    }

    public List<Car> getPostSimulationCars() {
        return postSimulationCars;
    }

    public Grid getGrid() {
        return grid;
    }
}
