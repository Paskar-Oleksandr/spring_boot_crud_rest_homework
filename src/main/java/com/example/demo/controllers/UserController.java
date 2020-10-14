package com.example.demo.controllers;

import com.example.demo.model.User;
import com.example.demo.model.UpdateUserRequest;
import com.example.demo.service.UserService;
import lombok.var;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/")
    ResponseEntity<String> greeting() {
        return ResponseEntity.ok("Hello, it`s a homework!");
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> showAllUsers() {
        return ResponseEntity.ok(service.getAllUsers());
    }

    @DeleteMapping("/delete-user-by-id/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        var isRemoved = service.deleteUserById(id);
        if (!isRemoved) {
            ResponseEntity.badRequest();
        }
        return ResponseEntity.ok(id);
    }

    @PutMapping("/user-update/{id}")
    public ResponseEntity<User> updateById(@RequestBody UpdateUserRequest request) {
        User current = service.findUserById(request.getId());
        current.setFirstName(request.getFirstName());
        current.setLastName(request.getLastName());
        service.updateUser(current);
        return ResponseEntity.ok(current);
    }

    @PostMapping("/create-user")
    public ResponseEntity.BodyBuilder createUser(@RequestBody User user) {
        User user1 = service.addUser(new User(user.getId(), user.getFirstName(), user.getLastName()));
        service.addUser(user1);
        return ResponseEntity.status(201);
    }
}
