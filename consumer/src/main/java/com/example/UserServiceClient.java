package com.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserServiceClient {

    private final RestTemplate restTemplate;

    public UserServiceClient(@Value("${user-service.base-url}") String baseUrl) {
        this.restTemplate = new RestTemplateBuilder().rootUri(baseUrl).build();
    }

    public User getUser(String id) {
        final LinkedHashMap map = (LinkedHashMap) restTemplate.getForObject("/users/" + id, Object.class);
        final User user = this.promptUser(map);
        Assert.hasText(user.getFullName(), "Name is blank.");
        return user;
    }

    private User promptUser(Map map) {
        return User.builder()
                .id(map.get("id").toString())
                .fullName(map.get("fullName").toString())
                .role(UserRole.valueOf(map.get("role").toString()))
                .permissions((List<Permission>) map.get("permissions"))
                .build();
    }
}
