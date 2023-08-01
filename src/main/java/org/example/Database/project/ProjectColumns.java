package org.example.Database.project;

public enum ProjectColumns {
    id("id"),
    name("name"),
    description("description");

    private final String text;

    ProjectColumns(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
