package org.example.Database.user;

import org.example.Database.SQLiteWrapper;
import org.example.Log.Logger;
import org.example.Model.User;
import org.example.Model.UserRole;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private SQLiteWrapper sqlite = SQLiteWrapper.getInstance();

    public boolean create(User user) {
        String query = String.format("INSERT INTO USER " +
                        "(FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, ROLE) " +
                        "VALUES ('%s', '%s', '%s', '%s', '%s')",
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole()
        );
        int rowsAffected = sqlite.executeUpdate(query);
        return rowsAffected > 0;
    }

    public boolean update(User user) {
        String query = String.format("UPDATE USER SET " +
                        "FIRST_NAME = '%s', LAST_NAME = '%s', EMAIL = '%s', PASSWORD = '%s', ROLE = '%s' " +
                        "WHERE ID = %d;",
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole(),
                user.getId()
        );
        int rowsAffected = sqlite.executeUpdate(query);
        return rowsAffected > 0;
    }

    public void delete(User user) {
        delete(user.getId());
    }

    public void delete(int userId) {
        sqlite.executeUpdate(
                String.format("SELECT FROM USER WHERE ID = %d;", userId)
        );
    }

    public User get(int userId) {

        String query = "SELECT * FROM USER WHERE ID = " + userId;
        ResultSet result = sqlite.executeQuery(query);
        try {
            if (result.next()) {
                int id = result.getInt("ID");
                String firstName = result.getString("FIRST_NAME");
                String lastName = result.getString("LAST_NAME");
                String email = result.getString("EMAIL");
                String password = result.getString("PASSWORD");
                UserRole role = UserRole.fromString(result.getString("ROLE"));
                return new User(id, firstName, lastName, email, password, role);
            }
        } catch (SQLException e) {
            Logger.getInstance().logError("Error reading ResultSet: " + e.getMessage());
        }
        return null;
    }

    public List<User> getAll() {
        ArrayList<User> list = new ArrayList<>();

        String query = "SELECT * FROM USER";
        ResultSet result = sqlite.executeQuery(query);
        try {
            while (result.next()) {
                int id = result.getInt("ID");
                String firstName = result.getString("FIRST_NAME");
                String lastName = result.getString("LAST_NAME");
                String email = result.getString("EMAIL");
                String password = result.getString("PASSWORD");
                UserRole role = UserRole.fromString(result.getString("ROLE"));
                list.add(new User(id, firstName, lastName, email, password, role));
            }
        } catch (SQLException e) {
            Logger.getInstance().logError("Error reading ResultSet: " + e.getMessage());
        }
        return list;
    }

    public boolean hasAnyUser() {
        int count = sqlite.executeUpdate("SELECT COUNT(1) FROM USER");
        return count > 0;
    }

}
