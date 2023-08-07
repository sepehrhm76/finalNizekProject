package org.example.Model;

public enum IssuePriority {
    High,
    Medium,
    Low;

    public static IssuePriority fromString(String input) {
        return switch (input) {
            case "High" -> High;
            case "Medium" -> Medium;
            case "Low" -> Low;
            default -> throw new IllegalArgumentException(String.format("%s is not valid Priority", input));
        };
    }

    @Override
    public String toString() {
        return switch (this) {
            case High -> "High";
            case Medium -> "Medium";
            case Low -> "Low";
        };
    }
}
