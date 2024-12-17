package org.carsimulation;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MoveInstructionTest {

    @Test
    void fromMoveInstructions() {
        List<MoveInstruction> instructions = List.of(MoveInstruction.FORWARD, MoveInstruction.LEFT, MoveInstruction.RIGHT);

        assertEquals("FLR", MoveInstruction.fromMoveInstructions(instructions));
    }

    @Test
    void fromString() {
        assertEquals(List.of(MoveInstruction.FORWARD, MoveInstruction.LEFT, MoveInstruction.RIGHT),
                     MoveInstruction.fromString("FLR"));
    }

    @Test
    void fromStringInvalidString() {
        assertThrows(IllegalArgumentException.class, () ->
                MoveInstruction.fromString("ABC"));
    }
}