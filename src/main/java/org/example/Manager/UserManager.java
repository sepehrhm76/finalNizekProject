package org.example.Manager;

import org.example.Database.user.UserRepository;
import org.example.Model.User;
import org.example.Model.UserRole;
import java.util.List;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserManager {

    private UserRepository userRepository = new UserRepository();

    public UserManager(){
    }

    public boolean hasAnyUser() {
        return userRepository.hasAnyUser();
    }

    public void addUser(String firstName, String lastName, String email, String password, UserRole role) {
        if (isValidEmail(email) && isValidPassword(password)) {
            User user = new User(firstName, lastName, email, password, role);
            userRepository.create(user);
        } else {
            throw new IllegalArgumentException("Email or password is not valid");
        }
    }

    public void updateUser(int id, String firstName, String lastName, String email, String password, UserRole role) {
            User user = new User(firstName, lastName, email, password, role);
            userRepository.update(id, user);
        }


    public void removeUser(User user) {
        if (user != null) {
            userRepository.delete(user);
        }
    }

    public List<User> getAllUser() {
       return userRepository.getAll();
    }

    public boolean isValidEmail(String email) {
        String emailRegex = "^.*$";
//        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean isValidPassword(String password) {
        // Regular expression to validate password format
        /**
         * The password must contain at least one uppercase letter.
         * The password must contain at least one lowercase letter.
         * The password must contain at least one digit.
         * The password must be at least 8 characters long
         **/

//        String passwordRegex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$";
        String passwordRegex = "^.*$";
        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public boolean validateUserLogin(String email, String password) {
        for (User user : userRepository.getAll()) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
}
