package org.example.Model;

public enum IssuePriority {
    high,
    medium,
    low;

    public static IssuePriority fromString(String input) {
        switch (input) {
            case "high": return high;
            case "medium": return medium;
            case "low": return low;
            default: throw new IllegalArgumentException(String.format("%s is not valid Priority", input));
        }
    }

    @Override
    public String toString() {
        switch (this) {
            case high: return "high";
            case medium: return "medium";
            case low: return "low";
        }
        return null;
    }
}
