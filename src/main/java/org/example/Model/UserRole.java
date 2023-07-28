package org.example.Model;

public enum UserRole {
    SUPER_ADMIN,
    PO,
    QA,
    DEVELOPER;

    public static UserRole fromString(String input) {
        return switch (input) {
            case "sa" -> SUPER_ADMIN;
            case "po" -> PO;
            case "qa" -> QA;
            case "dev" -> DEVELOPER;
            default -> throw new IllegalArgumentException(String.format("%s is not valid UserRole", input));
        };
    }

    @Override
    public String toString() {
        return switch (this) {
            case SUPER_ADMIN -> "sa";
            case PO -> "po";
            case QA -> "qa";
            case DEVELOPER -> "dev";
        };
    }
}
