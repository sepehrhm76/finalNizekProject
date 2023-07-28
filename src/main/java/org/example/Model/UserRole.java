package org.example.Model;

public enum UserRole {
    SUPER_ADMIN,
    PO,
    QA,
    DEVELOPER;

    public static UserRole fromString(String input) {
        return switch (input) {
            case "Super Admin" -> SUPER_ADMIN;
            case "Product Owner" -> QA;
            case "Tester" -> QA;
            case "Developer" -> DEVELOPER;
            default -> throw new IllegalArgumentException(String.format("%s is not valid UserRole", input));
        };
    }

    @Override
    public String toString() {
        return switch (this) {
            case SUPER_ADMIN -> "Super Admin";
            case PO -> "Product Owner";
            case QA -> "Tester";
            case DEVELOPER -> "Developer";
        };
    }
}
