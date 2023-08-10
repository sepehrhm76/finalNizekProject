package org.example.Database.board;

public enum BoardColumns {
    id("id"),
    name("name"),
    project_id("project_id");
    private final String text;

    BoardColumns(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
