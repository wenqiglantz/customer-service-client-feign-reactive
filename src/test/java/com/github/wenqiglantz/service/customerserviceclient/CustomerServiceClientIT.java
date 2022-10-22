package com.github.wenqiglantz.service.customerserviceclient;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.wenqiglantz.service.customerserviceclient.data.CustomerVO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(initializers = {WireMockInitializer.class})
class CustomerServiceClientIT {

    @Autowired
    private WebTestClient client;

    @Autowired
    private WireMockServer wireMockServer;

    public static String CUSTOMER_SUCCESSFUL_RESPONSE =
            """
              {
                "customerId": "123",
                "firstName": "John",
                "lastName": "Smith"
              }      
            """;

    public static String UPDATE_CUSTOMER_SUCCESSFUL_RESPONSE =
            """
              {
                "customerId": "123",
                "firstName": "Jane",
                "lastName": "Smith"
              }      
            """;

    @AfterEach
    public void afterEach() {
        this.wireMockServer.resetAll();
    }

    @Test
    void testCreateCustomer() throws Exception {
        wireMockServer.stubFor(post(urlPathMatching("/customers"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-type",
                                "application/json")
                        .withBody(CUSTOMER_SUCCESSFUL_RESPONSE)));

        CustomerVO request = CustomerVO.builder()
                .firstName("John")
                .lastName("Smith")
                .build();

        client.post().uri("/customers").body(Mono.just(request), CustomerVO.class)
                .exchange().expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$.firstName")
                .isEqualTo("John")
                .jsonPath("$.lastName")
                .isEqualTo("Smith")
                .jsonPath("$.customerId")
                .isNotEmpty();
    }

    @Test
    void testGetCustomer() throws Exception {
        wireMockServer.stubFor(get(urlPathMatching("/customers/([a-zA-Z0-9]*)"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-type",
                                "application/json")
                        .withBody(CUSTOMER_SUCCESSFUL_RESPONSE)));

        client.get().uri("/customers/123")
                .exchange().expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$.firstName")
                .isEqualTo("John")
                .jsonPath("$.lastName")
                .isEqualTo("Smith")
                .jsonPath("$.customerId")
                .isNotEmpty();
    }

    @Test
    void testUpdateCustomer() throws Exception {
        wireMockServer.stubFor(put(urlPathMatching("/customers/([a-zA-Z0-9]*)"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-type",
                                "application/json")
                        .withBody(UPDATE_CUSTOMER_SUCCESSFUL_RESPONSE)));

        CustomerVO request = CustomerVO.builder()
                .firstName("Jane")
                .lastName("Smith")
                .build();

        client.put().uri("/customers/123").body(Mono.just(request), CustomerVO.class)
                .exchange().expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$.firstName")
                .isEqualTo("Jane")
                .jsonPath("$.lastName")
                .isEqualTo("Smith")
                .jsonPath("$.customerId")
                .isNotEmpty();
    }

    @Test
    void testDeleteCustomer() throws Exception {
        wireMockServer.stubFor(delete(urlPathMatching("/customers/([a-zA-Z0-9]*)"))
                .willReturn(aResponse()
                        .withStatus(200)));

        client.delete().uri("/customers/123")
                .exchange().expectStatus().is2xxSuccessful()
                .expectBody()
                .isEmpty();
    }
}
