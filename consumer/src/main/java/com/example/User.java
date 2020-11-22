package com.example;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class User {

    private String id;
    private String fullName;
    private UserRole role;
    private List<Permission> permissions;

}
