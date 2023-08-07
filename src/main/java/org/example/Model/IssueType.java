package org.example.Model;

public enum IssueType {
    story,
    task,
    bug;

    public static IssueType fromString(String input) {
        return switch (input) {
            case "story" -> story;
            case "task" -> task;
            case "bug" -> bug;
            default -> throw new IllegalArgumentException(String.format("%s is not valid Type", input));
        };
    }

    @Override
    public String toString() {
        return switch (this) {
            case story -> "story";
            case task -> "task";
            case bug -> "bug";
        };
    }

}
