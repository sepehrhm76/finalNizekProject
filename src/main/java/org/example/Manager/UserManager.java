package org.example.Manager;

import org.example.Database.user.UserRepository;
import org.example.Model.UserRole;

public class UserManager {

    private UserRepository userRepository = new UserRepository();

    public boolean hasAnyUser() {
        return userRepository.hasAnyUser();
    }

    public void addUser(String firstName, String lastName, String email, String password, UserRole userRole) {
        // add all validation here
        // throw exception if validations are not ok
        // hash your password here
        // add user object into the repository
    }

    public void loginUser(String email, String password) {
        // validation
        // hash your password to check with your repository
        // add getByEmailAndPassword in userRepository
    }


}
