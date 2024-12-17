package org.carsimulation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GridTest {

    private Grid grid;

    @BeforeEach
    void setup() {
        grid = new Grid(10, 10);
    }

    @Test
    void addToGrid() {
        Car toyotaCar = new Car("toyota", new Grid.Coordinate(3, 3), FaceDirection.NORTH, List.of(MoveInstruction.FORWARD));

        grid.addToGrid(toyotaCar);

        assertEquals(toyotaCar, grid.getFirstCarFromCoordinate(new Grid.Coordinate(3, 3)));
    }

    @Test
    void addToGridOutOfBounds() {
        Car toyotaCar = new Car("toyota", new Grid.Coordinate(10, 10), FaceDirection.NORTH, List.of(MoveInstruction.FORWARD));
        Car hondaCar = new Car("honda", new Grid.Coordinate(-1, -1), FaceDirection.NORTH, List.of(MoveInstruction.FORWARD));

        assertThrows(IllegalArgumentException.class, () -> grid.addToGrid(toyotaCar));
        assertThrows(IllegalArgumentException.class, () -> grid.addToGrid(hondaCar));
    }

    @Test
    void removeFromGrid() {
        Car toyotaCar = new Car("toyota", new Grid.Coordinate(3, 3), FaceDirection.NORTH, List.of(MoveInstruction.FORWARD));
        grid.addToGrid(toyotaCar);

        grid.removeFromGrid(toyotaCar);
        assertNull(grid.getFirstCarFromCoordinate(new Grid.Coordinate(3, 3)));
    }

    @Test
    void removeFromGridOutOfBounds() {
        Car toyotaCar = new Car("toyota", new Grid.Coordinate(11, 11), FaceDirection.NORTH, List.of(MoveInstruction.FORWARD));

        assertThrows(IllegalArgumentException.class, () -> grid.removeFromGrid(toyotaCar));
    }

    @Test
    void removeFromGridDoesNotExist() {
        Car toyotaCar = new Car("toyota", new Grid.Coordinate(3, 3), FaceDirection.NORTH, List.of(MoveInstruction.FORWARD));

        assertThrows(IllegalArgumentException.class, () -> grid.removeFromGrid(toyotaCar));
    }

    @Test
    void getFirstCarFromCoordinateOutOfBounds() {
        assertThrows(IllegalArgumentException.class, () -> grid.getFirstCarFromCoordinate(new Grid.Coordinate(11, 11)));
    }

    @Test
    void getFirstCarFromCoordinateDoesNotExist() {
        assertNull(grid.getFirstCarFromCoordinate(new Grid.Coordinate(3, 3)));
    }

    @Test
    void hasCollision() {
        Car toyotaCar = new Car("toyota", new Grid.Coordinate(3, 3), FaceDirection.SOUTH, List.of(MoveInstruction.FORWARD));
        Car hondaCar = new Car("honda", new Grid.Coordinate(3, 3), FaceDirection.NORTH, List.of(MoveInstruction.FORWARD));
        Car mitsubishiCar = new Car("mitsubishi", new Grid.Coordinate(3, 3), FaceDirection.WEST, List.of(MoveInstruction.FORWARD));
        grid.addToGrid(toyotaCar);
        grid.addToGrid(hondaCar);

        assertTrue(grid.hasCollision(mitsubishiCar));
    }

    @Test
    void hasNoCollision() {
        Car toyotaCar = new Car("toyota", new Grid.Coordinate(3, 3), FaceDirection.SOUTH, List.of(MoveInstruction.FORWARD));

        assertFalse(grid.hasCollision(toyotaCar));
    }

    @Test
    void isValidCoordinate() {
        assertTrue(grid.isValidCoordinate(new Grid.Coordinate(3, 3)));
    }

    @Test
    void isNotValidCoordinate() {
        assertFalse(grid.isValidCoordinate(new Grid.Coordinate(-1, 0)));
        assertFalse(grid.isValidCoordinate(new Grid.Coordinate(0, -1)));
        assertFalse(grid.isValidCoordinate(new Grid.Coordinate(11, 1)));
        assertFalse(grid.isValidCoordinate(new Grid.Coordinate(1, 11)));
    }

    @Test
    void isValidGrid() {
        assertTrue(Grid.isValidGrid(1, 1));
        assertTrue(Grid.isValidGrid(100, 100));
        assertTrue(Grid.isValidGrid(100000, 100000));
    }

    @Test
    void isNotValidGrid() {
        assertFalse(Grid.isValidGrid(1, 0));
        assertFalse(Grid.isValidGrid(0, 1));
        assertFalse(Grid.isValidGrid(-1, 10));
    }
}