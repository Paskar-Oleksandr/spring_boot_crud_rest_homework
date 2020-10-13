package com.example.demo.controllers;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HomeworkController {

    private final UserService service;

    public HomeworkController(UserService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String greeting() {
        return "Hello, it`s a homework";
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> showAllUsers() {
        if (service.getAllUsers().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(service.getAllUsers(), HttpStatus.OK);
    }

    @DeleteMapping("/delete-user-by-id/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        service.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/user-delete/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") Long id) {
        service.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/add-user")
    public void addUser(@RequestParam("id") Long id,
                               @RequestParam(value = "firstName", required = false, defaultValue = "Alex") String firstName,
                               @RequestParam(value = "lastName", required = false, defaultValue = "Alexandrov") String lastName) {
        service.addUser(new User(id, firstName, lastName));
    }
    
    @PutMapping("/user-update/{id}")
    public ResponseEntity<User> updateById(@PathVariable("id") Long id, @RequestBody User user) {
        User current = service.findUserById(id);
        current.setFirstName(user.getFirstName());
        current.setLastName(user.getLastName());
        return new ResponseEntity<>(service.updateUser(current), HttpStatus.OK);
    }

    @PostMapping("/create-user")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            User user1 = service.addUser(new User(user.getId(), user.getFirstName(), user.getLastName()));
            return new ResponseEntity<>(user1, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
