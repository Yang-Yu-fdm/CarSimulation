package org.carsimulation;

public enum FaceDirection {

    NORTH("N", 0.0),
    SOUTH("S", 180.0),
    EAST("E", 90.0),
    WEST("W", 270.0);

    private final String alias;
    private final double bearing;

    private FaceDirection(String alias, double bearing) {
        this.alias = alias;
        this.bearing = bearing;
    }

    public static FaceDirection fromBearingChange(FaceDirection initialDirection, double bearingChange) {
        double newBearing = (initialDirection.bearing + bearingChange) % 360;

        if (newBearing < 0) {
            newBearing += 360;
        }
        for (FaceDirection direction : FaceDirection.values()) {
            if (Math.abs(direction.bearing - newBearing) < 0.001) {
                return direction;
            }
        }

        throw new IllegalArgumentException("Invalid value for bearingChange: %.1f. The input must be a multiple of 90.0".formatted(bearingChange));
    }

    public static FaceDirection fromString(String inputString) {
        for (FaceDirection direction : FaceDirection.values()) {
            if (direction.alias.equalsIgnoreCase(inputString)) {
                return direction;
            }
        }
        throw new IllegalArgumentException("Invalid direction: %s".formatted(inputString));
    }

    public String getAlias() {
        return alias;
    }
}
