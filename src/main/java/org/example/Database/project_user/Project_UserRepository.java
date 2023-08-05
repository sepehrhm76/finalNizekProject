package org.example.Database.project_user;

import org.example.Database.SQLiteWrapper;
import org.example.Database.project.ProjectColumns;
import org.example.Database.user.UserRepository;
import org.example.Log.Logger;
import org.example.Model.Project;
import org.example.Model.User;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Project_UserRepository {
    private static final String TABLE_NAME = "project_user";

    private SQLiteWrapper sqlite = SQLiteWrapper.getInstance();

    public boolean addUserToProject(int userId, int projectId) {
        String query = String.format("INSERT INTO %s " +
                        "(%s, %s) " +
                        "VALUES (?, ?)",
                TABLE_NAME,
                Project_UserColumns.project_id.toString(),
                Project_UserColumns.user_id.toString()
        );

        int rowsAffected = sqlite.executeUpdate(query,
                projectId,
                userId);
        return rowsAffected > 0;
    }

    public boolean removeUserFromProject(int userId, int projectId) {
        String query = String.format("DELETE FROM %s WHERE %s = ? AND %s = ?",
                TABLE_NAME,
                Project_UserColumns.project_id.toString(),
                Project_UserColumns.user_id.toString()
        );
        System.out.println(query);
        int rowsAffected = sqlite.executeUpdate(query,
                projectId,
                userId);
        return rowsAffected > 0;
    }

    public List<User> getUsersByProject(int projectId) {
        ArrayList<User> list = new ArrayList<>();

        String query = String.format("SELECT user.* FROM %s INNER JOIN user ON project_user.user_id = user.id WHERE %s.%s = ?", TABLE_NAME, TABLE_NAME, Project_UserColumns.project_id.toString()) ;
        ResultSet result = sqlite.executeQuery(query, projectId);
        try {
            while (result.next()) {
                list.add(UserRepository.createUserFromResultSet(result));
            }
        } catch (Exception e) {
            Logger.getInstance().logError("Error reading ResultSet: " + e.getMessage());
        }
        return list;
    }
}
