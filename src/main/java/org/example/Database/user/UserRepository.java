package org.example.Database.user;

import org.example.Database.SQLiteWrapper;
import org.example.Log.Logger;
import org.example.Model.User;
import org.example.Model.UserRole;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;



public class UserRepository {

    private static final String TABLE_NAME = "user";

    private SQLiteWrapper sqlite = SQLiteWrapper.getInstance();

    public boolean create(User user) {
        String query = String.format("INSERT INTO %s " +
                        "(%s, %s, %s, %s, %s) " +
                        "VALUES (?, ?, ?, ?, ?)",
                TABLE_NAME,
                Columns.firstname.toString(),
                Columns.lastname.toString(),
                Columns.email.toString(),
                Columns.password.toString(),
                Columns.role.toString()
        );

        int rowsAffected = sqlite.executeUpdate(query,
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole().toString());
        return rowsAffected > 0;
    }

    public boolean update(int id, User user) {
        String query = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?",
                TABLE_NAME,
                Columns.firstname.toString(),
                Columns.lastname.toString(),
                Columns.email.toString(),
                Columns.password.toString(),
                Columns.role.toString(),
                Columns.id.toString()
        );

        int rowsAffected = sqlite.executeUpdate(query,
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole().toString(),
                id);
        return rowsAffected > 0;
    }

    public void delete(User user) {
        delete(user.getId());
    }

    public void delete(int userId) {
        sqlite.executeUpdate(
                String.format("DELETE FROM %s WHERE %s = ?;", TABLE_NAME, Columns.id.toString()), userId
        );
    }

    public User get(int userId) {

        ResultSet result = sqlite.executeQuery(String.format("SELECT * FROM %s WHERE %s = ?", TABLE_NAME, Columns.id.toString()), userId);
        try {
            if (result.next()) {
                return this.createUserFromResultSet(result);
            }
        } catch (Exception e) {
            Logger.getInstance().logError("Error reading ResultSet: " + e.getMessage());
        }
        return null;
    }

    public List<User> getAll() {
        ArrayList<User> list = new ArrayList<>();

        String query =String.format("SELECT * FROM %s", TABLE_NAME) ;
        ResultSet result = sqlite.executeQuery(query);
        try {
            while (result.next()) {
                list.add(this.createUserFromResultSet(result));
            }
        } catch (Exception e) {
            Logger.getInstance().logError("Error reading ResultSet: " + e.getMessage());
        }
        return list;
    }

    public boolean hasAnyUser() {
        int count = sqlite.executeUpdate(String.format("SELECT COUNT(*) FROM %s",TABLE_NAME));
        return count > 0;
    }


    private User createUserFromResultSet(ResultSet result) throws Exception {
        int id = result.getInt(Columns.id.toString());
        String firstName = result.getString(Columns.firstname.toString());
        String lastName = result.getString(Columns.lastname.toString());
        String email = result.getString(Columns.email.toString());
        String password = result.getString(Columns.password.toString());
        UserRole role = UserRole.fromString(result.getString(Columns.role.toString()));
        return new User(id, firstName, lastName, email, password, role);
    }
}
