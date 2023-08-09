package org.example.Database.board;

public enum BoardColumns {
    id("id"),
    name("name"),
    description("description");

    private final String text;

    BoardColumns(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
