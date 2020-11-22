package com.example;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import au.com.dius.pact.provider.spring.SpringRestPactRunner;
import au.com.dius.pact.provider.spring.target.SpringBootHttpTarget;
import com.example.exceptions.NotFoundException;
import com.example.models.Permission;
import com.example.models.User;
import com.example.models.UserRole;
import com.example.services.UserService;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRestPactRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@PactFolder("../consumer/target/pacts")
@Provider("user-service")
public class MockedUserServiceContractTest {

    @MockBean
    private UserService userService;

    @TestTarget
    public final Target target = new SpringBootHttpTarget();

    @State("User 01 exists")
    public void user1Exists() {
        when(userService.findUser(any())).thenReturn(User.builder()
                .id("01")
                .fullName("Fahmi BEN SALAH")
                .role(UserRole.ADMIN)
                .permission(Permission.builder().id("001").name("permission_001").build())
                .build());
    }

    @State("User 02 does not exist")
    public void user2DoesNotExist() {
        when(userService.findUser(any())).thenThrow(NotFoundException.class);
    }
}
