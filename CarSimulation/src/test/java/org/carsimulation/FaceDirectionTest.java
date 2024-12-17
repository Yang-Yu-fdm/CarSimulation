package org.carsimulation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FaceDirectionTest {

    @Test
    void fromBearingChange() {
        assertEquals(FaceDirection.WEST,
                FaceDirection.fromBearingChange(FaceDirection.EAST, -180.0));
        assertEquals(FaceDirection.SOUTH,
                FaceDirection.fromBearingChange(FaceDirection.WEST, -90.0));
        assertEquals(FaceDirection.NORTH,
                FaceDirection.fromBearingChange(FaceDirection.SOUTH, 180.0));
        assertEquals(FaceDirection.EAST,
                FaceDirection.fromBearingChange(FaceDirection.NORTH, 450.0));
    }

    @Test
    void fromBearingChangeInvalidBearing() {
        assertThrows(IllegalArgumentException.class, () ->
                FaceDirection.fromBearingChange(FaceDirection.NORTH, 50.0));
    }

    @Test
    void fromStringValidString() {
        assertEquals(FaceDirection.NORTH, FaceDirection.fromString("N"));
        assertEquals(FaceDirection.SOUTH, FaceDirection.fromString("S"));
        assertEquals(FaceDirection.EAST, FaceDirection.fromString("E"));
        assertEquals(FaceDirection.WEST, FaceDirection.fromString("W"));
    }

    @Test
    void fromStringInvalidString() {
        assertThrows(IllegalArgumentException.class, () ->
                FaceDirection.fromString("A"));
    }
}