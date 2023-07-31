package org.example.Model;

import java.util.Arrays;

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

    public static UserRole[] rolesWithoutSuperAdmin() {
        UserRole[] roles = new UserRole[values().length - 1];
        roles[0] = PO;
        roles[1] = QA;
        roles[2] = DEVELOPER;
        return roles;
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
