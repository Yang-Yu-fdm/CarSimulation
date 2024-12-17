package org.carsimulation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Grid {

    private int width;
    private int height;

    private static final int MAX_WIDTH = 1000000;
    private static final int MAX_HEIGHT = 1000000;
    private List[][] grid;

    public Grid(int width, int height) {
        this.width = width;
        this.height = height;
        grid = new List[width][height];
    }

    public void addToGrid(Car car) {
        int xCoord = car.getCurrentPosition().getX();
        int yCoord = car.getCurrentPosition().getY();
        if (!isValidCoordinate(car.getCurrentPosition())) {
            throw new IllegalArgumentException("Coordinates (%d, %d) out of bounds for grid size (%d, %d)".formatted(
                    car.getCurrentPosition().getX(), car.getCurrentPosition().getY(), width-1, height-1));
        }

        if (grid[xCoord][yCoord] == null) {
            grid[xCoord][yCoord] = new ArrayList<>();
        }
        grid[xCoord][yCoord].add(car);
    }

    public void removeFromGrid(Car car) {
        int xCoord = car.getCurrentPosition().getX();
        int yCoord = car.getCurrentPosition().getY();
        if (!isValidCoordinate(car.getCurrentPosition())) {
            throw new IllegalArgumentException("Coordinates (%d, %d) out of bounds for grid size (%d, %d)".formatted(
                    car.getCurrentPosition().getX(), car.getCurrentPosition().getY(), width-1, height-1));
        } else if (grid[xCoord][yCoord] == null) {
            throw new IllegalArgumentException("Car at coordinates (%d, %d) does not exist in grid".formatted(
                    car.getCurrentPosition().getX(), car.getCurrentPosition().getY()));
        }

        grid[xCoord][yCoord].remove(car);
    }

    public Car getFirstCarFromCoordinate(Grid.Coordinate coordinate) {
        if (!isValidCoordinate(coordinate)) {
            throw new IllegalArgumentException("Coordinates (%d, %d) out of bounds for grid size (%d, %d)".formatted(
                    coordinate.getX(), coordinate.getY(), width-1, height-1));
        }

        List<Car> cars = grid[coordinate.getX()][coordinate.getY()];
        if (cars == null || cars.isEmpty()) {
            return null;
        }
        return (Car) grid[coordinate.getX()][coordinate.getY()].getFirst();
    }

    public boolean hasCollision(Car car) {
        int xCoord = car.getCurrentPosition().getX();
        int yCoord = car.getCurrentPosition().getY();
        return grid[xCoord][yCoord] != null && grid[xCoord][yCoord].size() > 1;
    }

    public boolean isValidCoordinate(Grid.Coordinate coordinate) {
        int xCoord = coordinate.getX();
        int yCoord = coordinate.getY();
        if (xCoord < 0 || xCoord > width-1 ||
                yCoord < 0 || yCoord > height-1) {
            return false;
        }
        return true;
    }

    public static boolean isValidGrid(int width, int height) {
        if (width <= 0 || width > MAX_WIDTH-1 ||
                height <= 0 || height > MAX_HEIGHT-1) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Grid{" +
                "width=" + width +
                ", height=" + height +
                ", grid=" + Arrays.toString(grid) +
                '}';
    }

    public static class Coordinate {
        private int x;
        private int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coordinate that = (Coordinate) o;
            return x == that.x && y == that.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "Coordinate{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }
}