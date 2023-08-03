package org.example.Database.project_user;

public enum Project_UserColumns {
    id("id"),
    project_id("project_id"),
    user_id("user_id");

    private final String text;

    Project_UserColumns(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
