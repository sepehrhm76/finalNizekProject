package org.example;

import org.example.Database.SQLiteWrapper;
import org.example.Database.user.UserRepository;
import org.example.Model.User;
import org.example.Model.UserRole;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        UserRepository userRepository = new UserRepository();

//        userRepository.create(new User("Parham", "Soltani", "p.soltani@nizek.com", "123456", UserRole.SUPER_ADMIN));
//        userRepository.create(new User("Sepehr", "HM", "s.hajimohammadi@nizek.com", "123456", UserRole.SUPER_ADMIN));
//        User sepehr = userRepository.get(2);
//        sepehr.setLastName("HajiMohammadi");
//        userRepository.update(sepehr);

        System.out.println(userRepository.getAll());


    }
}