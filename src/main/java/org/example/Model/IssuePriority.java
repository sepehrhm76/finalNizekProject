package org.example.Model;

public enum IssuePriority {
    high,
    medium,
    low;

    public static IssuePriority fromString(String input) {
        return switch (input) {
            case "high" -> high;
            case "medium" -> medium;
            case "low" -> low;
            default -> throw new IllegalArgumentException(String.format("%s is not valid Priority", input));
        };
    }

    @Override
    public String toString() {
        return switch (this) {
            case high -> "high";
            case medium -> "medium";
            case low -> "low";
        };
    }
}
