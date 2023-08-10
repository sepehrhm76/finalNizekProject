package org.example.Model;

public enum IssueType {
    story,
    task,
    bug;

    public static IssueType fromString(String input) {
        switch (input) {
            case "story": return story;
            case "task": return task;
            case "bug": return bug;
            default: throw new IllegalArgumentException(String.format("%s is not valid Type", input));
        }
    }

    @Override
    public String toString() {
        switch (this) {
            case story: return "story";
            case task: return "task";
            case bug: return "bug";
        }
        return null;
    }

}
