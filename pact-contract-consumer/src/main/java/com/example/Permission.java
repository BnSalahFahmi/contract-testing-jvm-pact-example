package com.example;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Permission {
    private String id;
    private String name;
}
