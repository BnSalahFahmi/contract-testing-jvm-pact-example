package com.example;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import com.example.rules.RandomPortRule;
import io.pactfoundation.consumer.dsl.LambdaDsl;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE,
        properties = "user-service.base-url:http://localhost:${RANDOM_PORT}",
        classes = UserServiceClient.class)
public class ContractTest {

    private static final String FULL_NAME = "Fahmi BEN SALAH";
    private static final UserRole ROLE = UserRole.ADMIN;
    private static final List<Permission> PERMISSIONS = new ArrayList<Permission>() {{
        add(new Permission("001", "permission_001"));
    }};

    @ClassRule
    public static RandomPortRule randomPort = new RandomPortRule();

    @Rule
    public PactProviderRuleMk2 provider = new PactProviderRuleMk2("user-service", null, randomPort.getPort(), this);

    @Rule
    public ExpectedException expandException = ExpectedException.none();

    @Autowired
    private UserServiceClient userServiceClient;

    @Pact(consumer = "pact-contract-consumer")
    public RequestResponsePact pactUserExists(PactDslWithProvider builder) {

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");


        DslPart body = LambdaDsl.newJsonBody((o) -> o
                .stringType("id", "01")
                .stringType("fullName", FULL_NAME)
                .stringMatcher("role", "ADMIN|USER", "ADMIN")
                .minArrayLike("permissions", 0, 1, permission -> permission
                        .stringType("id", PERMISSIONS.get(0).getId())
                        .stringType("name", PERMISSIONS.get(0).getName())
                )).build();

        return builder
                .given("User 01 exists")
                .uponReceiving("A request to /users/01")
                .path("/users/01")
                .method("GET")
                .willRespondWith()
                .headers(headers)
                .status(200)
                .body(body)
                .toPact();
    }

    @Pact(consumer = "pact-contract-consumer")
    public RequestResponsePact pactUserDoesNotExist(PactDslWithProvider builder) {

        return builder
                .given("User 02 does not exist")
                .uponReceiving("A request to /users/02")
                .path("/users/02")
                .method("GET")
                .willRespondWith()
                .status(404)
                .toPact();
    }

    @Test
    @PactVerification(fragment = "pactUserExists")
    public void userExists() {
        final User user = userServiceClient.getUser("01");

        assertThat(user.getFullName()).isEqualTo(FULL_NAME);
        assertThat(user.getRole()).isEqualTo(ROLE);
        assertThat(user.getPermissions()).hasSize(1);
    }

    @Test
    @PactVerification(fragment = "pactUserDoesNotExist")
    public void userDoesNotExist() {
        expandException.expect(HttpClientErrorException.class);
        expandException.expectMessage("404 Not Found");

        userServiceClient.getUser("02");
    }
}
