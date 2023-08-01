package org.example.Database.user;

enum UserColumns {

    id("id"),
    firstname("firstname"),
    lastname("lastname"),
    email("email"),
    password("password"),
    role("role");

    private final String text;

    UserColumns(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
