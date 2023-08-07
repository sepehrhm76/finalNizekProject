package org.example.Model;

public enum IssueType {
    STORY,
    TASK,
    BUG;

    public static IssueType fromString(String input) {
        return switch (input) {
            case "Story" -> STORY;
            case "Task" -> TASK;
            case "Bug" -> BUG;
            default -> throw new IllegalArgumentException(String.format("%s is not valid Type", input));
        };
    }

    @Override
    public String toString() {
        return switch (this) {
            case STORY -> "Story";
            case TASK -> "Task";
            case BUG -> "Bug";
        };
    }

}
