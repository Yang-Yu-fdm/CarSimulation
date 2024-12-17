package org.carsimulation;

import java.util.List;
import java.util.Objects;

public class Car {

    private String name;
    private Grid.Coordinate currentPosition;
    private FaceDirection faceDirection;
    private List<MoveInstruction> instructions;
    private int collionStep;
    private Car collidedCar;

    public Car(String name, Grid.Coordinate currentPosition, FaceDirection faceDirection, List<MoveInstruction> instructions) {
        this.name = name;
        this.currentPosition = currentPosition;
        this.faceDirection = faceDirection;
        this.instructions = instructions;
    }

    public String getName() {
        return name;
    }

    public Grid.Coordinate getCurrentPosition() {
        return currentPosition;
    }

    public FaceDirection getFaceDirection() {
        return faceDirection;
    }

    public List<MoveInstruction> getInstructions() {
        return instructions;
    }


    public Car getCollidedCar() {
        return collidedCar;
    }

    public void setCurrentPosition(Grid.Coordinate currentPosition) {
        this.currentPosition = currentPosition;
    }

    public void setFaceDirection(FaceDirection faceDirection) {
        this.faceDirection = faceDirection;
    }

    public int getCollionStep() {
        return collionStep;
    }

    public void setCollionStep(int collionStep) {
        this.collionStep = collionStep;
    }

    public void setCollidedCar(Car collidedCar) {
        this.collidedCar = collidedCar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return collionStep == car.collionStep &&
                Objects.equals(name, car.name) &&
                Objects.equals(currentPosition, car.currentPosition) &&
                faceDirection == car.faceDirection &&
                Objects.equals(instructions, car.instructions) &&
                Objects.equals(collidedCar, car.collidedCar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, currentPosition, faceDirection, instructions, collionStep);
    }

    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + '\'' +
                ", currentPosition=" + currentPosition +
                ", faceDirection=" + faceDirection +
                ", instructions=" + instructions +
                ", collionStep=" + collionStep +
                '}';
    }
}