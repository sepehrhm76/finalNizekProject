package org.example.Database.project;

import org.example.Database.SQLiteWrapper;
import org.example.Log.Logger;
import org.example.Model.Project;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProjectRepository {

    private static final String TABLE_NAME = "project";


    private final SQLiteWrapper sqlite = SQLiteWrapper.getInstance();

    public boolean create(Project project) {
        String query = String.format("INSERT INTO %s " +
                        "(%s, %s) " +
                        "VALUES (?, ?)",
                TABLE_NAME,
                ProjectColumns.name,
                ProjectColumns.description
        );

        int rowsAffected = sqlite.executeUpdate(query,
                project.getName(),
                project.getDescription());
        return rowsAffected > 0;
    }

    public boolean update(int id, Project project) {
        String query = String.format("UPDATE %s SET %s = ?, %s = ? WHERE %s = ?",
                TABLE_NAME,
                ProjectColumns.name,
                ProjectColumns.description,
                ProjectColumns.id
        );

        int rowsAffected = sqlite.executeUpdate(query,
                project.getName(),
                project.getDescription(),
                id);
        return rowsAffected > 0;
    }

    public void delete(Project project) {
        delete(project.getId());
    }

    public void delete(int projectId) {
        sqlite.executeUpdate(
                String.format("DELETE FROM %s WHERE %s = ?;", TABLE_NAME, ProjectColumns.id), projectId
        );
    }

    public Project get(int projectId) {

        ResultSet result = sqlite.executeQuery(String.format("SELECT * FROM %s WHERE %s = ?", TABLE_NAME, ProjectColumns.id), projectId);
        try {
            if (result.next()) {
                return this.createProjectFromResultSet(result);
            }
        } catch (Exception e) {
            Logger.getInstance().logError("Error reading ResultSet: " + e.getMessage());
        }
        return null;
    }

    public List<Project> getAll() {
        ArrayList<Project> list = new ArrayList<>();

        String query =String.format("SELECT * FROM %s", TABLE_NAME) ;
        ResultSet result = sqlite.executeQuery(query);
        try {
            while (result.next()) {
                list.add(this.createProjectFromResultSet(result));
            }
        } catch (Exception e) {
            Logger.getInstance().logError("Error reading ResultSet: " + e.getMessage());
        }
        return list;
    }

    public boolean hasAnyProject() {
        int count = sqlite.executeUpdate(String.format("SELECT COUNT(*) FROM %s",TABLE_NAME));
        return count > 0;
    }

    public Project createProjectFromResultSet(ResultSet result) throws Exception {
        int id = result.getInt(ProjectColumns.id.toString());
        String name = result.getString(ProjectColumns.name.toString());
        String description = result.getString(ProjectColumns.description.toString());
        return new Project(id, name, description);
    }
}
