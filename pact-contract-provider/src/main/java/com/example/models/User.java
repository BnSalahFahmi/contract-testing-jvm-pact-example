package com.example.models;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;

@Data
@Builder
public class User {

    private String id;
    private String fullName;
    private UserRole role;
    @Singular
    private List<Permission> permissions;

}
