package org.example.Model;

public class Board {
    int id;
    String name;
    int project_id;

    public Board(String name, int project_id) {
        this(-1, name, project_id);
    }

    public Board(int id, String name, int project_id) {
        this.id = id;
        this.name = name;
        this.project_id = project_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    @Override
    public String toString() {
        return "Board{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", project_id=" + project_id +
                '}';
    }
}