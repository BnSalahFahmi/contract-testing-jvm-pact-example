package com.example.rest;

import com.example.models.User;
import com.example.services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{userId}")
    public User getUser(@PathVariable String userId) {
        return userService.findUser(userId);
    }

}
