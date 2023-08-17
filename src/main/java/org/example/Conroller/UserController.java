package org.example.Conroller;

import org.example.Database.user.UserRepository;
import org.example.Model.User;
import org.example.Model.UserRole;
import org.example.view.MainPanel;
import org.example.view.Projects;

import java.util.List;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserController {
    private static UserController instance = null;
    private final UserRepository userRepository = new UserRepository();
    private User currentUser;

    private UserController(){
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
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

    public User getUserById(int userId) {
        for (User user : userRepository.getAll()) {
            if (user.getId() == userId) {
                return user;
            }
        }
        return null;
    }

    public List<User> getAllUsersNotInProject(int projectId) {
        return userRepository.getAllUsersNotInProject(projectId);
    }

    public boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean isValidPassword(String password) {
        /**
         * The password must contain at least one uppercase letter.
         * The password must contain at least one lowercase letter.
         * The password must contain at least one digit.
         * The password must be at least 8 characters long
         **/

        String passwordRegex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public boolean validateUserLogin(String email, String password) {
        for (User user : userRepository.getAllUsers()) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                currentUser = user;  // Set the currently logged-in user
                return true;
            }
        }
        return false;
    }

    public void login(String email, String password) {
        for (User user : userRepository.getAll()) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                currentUser = user;
            }
        }
        MainPanel.getInstance().updateCurrentUserLabel();
        Projects.getInstance().updateProjectsPage();
    }

    public void logout() {
        currentUser = null;
        MainPanel.reset();
    }

    public static UserController getInstance() {
        if (instance == null)
            instance = new UserController();
        return instance;
    }
}
