package com.example;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit.PactProviderRule;
import au.com.dius.pact.consumer.junit.PactVerification;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.jayway.jsonpath.JsonPath;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ContractTest {

    @Rule
    public PactProviderRule mockProvider = new PactProviderRule("pact-contract-provider", "localhost", 8080, this);

    private RestTemplate restTemplate = new RestTemplate();

    /*** This defines the expected interaction for out test ***/
    @Pact(provider = "pact-contract-provider", consumer = "pact-contract-consumer")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        DslPart body = new PactDslJsonBody()
                .stringType("id", "01")
                .stringType("fullName", "Fahmi BEN SALAH")
                .eachLike("permissions")
                .stringType("id", "001")
                .stringType("name", "permission_001")
                .closeArray();

        return builder
                .given("test GET")
                .uponReceiving("can get user data from user data provider")
                .path("/api/users/01")
                .method("GET")
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body(body)
                .toPact();
    }

    @Test
    @PactVerification(value = "pact-contract-provider", fragment = "createPact")
    public void runTest() {
        System.out.println("MOCK provider URL" + mockProvider.getUrl());
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(mockProvider.getUrl() + "/api/users/01", String.class);
        assertEquals("01", JsonPath.read(responseEntity.getBody(), "$.id"));
        assertEquals("Fahmi BEN SALAH", JsonPath.read(responseEntity.getBody(), "$.fullName"));
        //assertEquals(new JSONArray().appendElement(new Permission("001", "permission_001")), JsonPath.read(responseEntity.getBody(), "$.permissions"));
    }


}
