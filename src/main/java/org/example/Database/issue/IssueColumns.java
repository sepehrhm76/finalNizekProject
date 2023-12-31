package org.example.Database.issue;

public enum IssueColumns {
    id("id"),
    title("title"),
    description("description"),
    tag("tags"),
    type("type"),
    priority("priority"),
    user_id("user_id"),
    project_id("project_id"),
    cDate("cDate"),
    status("status");

    private final String text;

    IssueColumns(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

}
