package com.example.demo.service;

import com.example.demo.model.User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private static final Map<Long, User> userMap = new HashMap<>();

    static {
        User user1 = new User(1L, "Ivan", "Ivanov");
        User user2 = new User(2L, "Petro", "Petrov");
        User user3 = new User(3L, "Ilona", "Igorova");

        userMap.put(user1.getId(), user1);
        userMap.put(user2.getId(), user2);
        userMap.put(user3.getId(), user3);
    }

    public User findUserById(Long id) {
        return userMap.get(id);
    }

    public User addUser(User user) {
        userMap.put(user.getId(), user);
        return user;
    }

    public void updateUser(User user) {
        userMap.put(user.getId(), user);
    }

    public boolean deleteUserById(Long id) {
        userMap.remove(id);
        return userMap.containsKey(id);
    }

    public List<User> getAllUsers() {
        Collection<User> c = userMap.values();
        return new ArrayList<>(c);
    }

}