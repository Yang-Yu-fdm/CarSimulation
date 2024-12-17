package org.carsimulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public enum MoveInstruction {

    FORWARD("F"),
    LEFT("L"),
    RIGHT("R");
    private final String alias;
    private final static Map<String, MoveInstruction> aliasMap = initializeAliasMap();

    private MoveInstruction(String alias) {
        this.alias = alias;
    }

    public static String fromMoveInstructions(List<MoveInstruction> instructions) {
        List<String> instructionAliases = instructions.stream().map(instruction -> instruction.alias).collect(Collectors.toList());
        return String.join("", instructionAliases);
    }

    public static List<MoveInstruction> fromString(String inputString) {
        List<MoveInstruction> instructions = new ArrayList<>();
        char[] inputCharArray = inputString.toCharArray();

        for (char c : inputCharArray) {
            String instructionAlias = String.valueOf(c).toUpperCase();
            if (aliasMap.containsKey(instructionAlias)) {
                instructions.add(aliasMap.get(instructionAlias));
            } else {
                throw new IllegalArgumentException("Invalid instruction: %s".formatted(instructionAlias));
            }
        }
        return instructions;
    }

    private static Map<String, MoveInstruction> initializeAliasMap() {
        Map<String, MoveInstruction> enumMap = new HashMap<>();
        for (MoveInstruction instruction : MoveInstruction.values()) {
            enumMap.put(instruction.alias, instruction);
        }
        return enumMap;
    }
}
