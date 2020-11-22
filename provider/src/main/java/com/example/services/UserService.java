package com.example.services;

import com.example.models.Permission;
import com.example.models.User;
import com.example.models.UserRole;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {

    public User findUser(String userId) {
        return User.builder()
                .id(userId)
                .fullName("Fahmi BEN SALAH")
                .role(UserRole.ADMIN)
                .permissions(new ArrayList<Permission>() {{
                    add(
                            Permission.builder()
                                    .id("001")
                                    .name("permission_001")
                                    .build()
                    );
                }})
                .build();
    }
}
