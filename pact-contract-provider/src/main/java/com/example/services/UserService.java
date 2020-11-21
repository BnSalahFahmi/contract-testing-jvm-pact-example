package com.example.services;

import com.example.models.Permission;
import com.example.models.User;
import com.example.models.UserRole;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public User findUser(String userId) {
        return User.builder()
                .id(userId)
                .fullName("Fahmi BEN SALAH")
                .role(UserRole.ADMIN)
                .permission(Permission.builder().id("1").name("Permission 1").build())
                .permission(Permission.builder().id("2").name("Permission 2").build())
                .build();
    }
}
