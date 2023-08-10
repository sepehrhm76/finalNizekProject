package org.example.Model;


public enum UserRole {
    SUPER_ADMIN,
    PO,
    QA,
    DEVELOPER;

    public static UserRole fromString(String input) {
        switch (input) {
            case "sa": return SUPER_ADMIN;
            case "po": return PO;
            case "qa": return QA;
            case "dev": return DEVELOPER;
            default: throw new IllegalArgumentException(String.format("%s is not valid UserRole", input));
        }
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
        switch (this) {
            case SUPER_ADMIN: return "sa";
            case PO: return "po";
            case QA: return "qa";
            case DEVELOPER: return "dev";
        }
        return null;
    }
}
