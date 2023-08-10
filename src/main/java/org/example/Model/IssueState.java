package org.example.Model;

public enum IssueState {
    todo,
    inProgress,
    qa,
    done;
    public static IssueState fromString(String input) {
        if (input == null) { return null; }
        switch (input) {
            case "todo": return todo;
            case "inProgress": return inProgress;
            case "qa": return qa;
            case "done": return done;
            default: return null;
        }
    }

    @Override
    public String toString() {
        switch (this) {
            case todo: return "todo";
            case inProgress: return "inProgress";
            case qa: return "qa";
            case done: return "done";
        }
        return null;
    }
}
