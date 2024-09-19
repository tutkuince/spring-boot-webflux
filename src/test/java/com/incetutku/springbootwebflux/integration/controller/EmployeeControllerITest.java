package com.incetutku.springbootwebflux.integration.controller;

import com.incetutku.springbootwebflux.dto.EmployeeDto;
import com.incetutku.springbootwebflux.integration.AbstractContainerBaseTest;
import com.incetutku.springbootwebflux.service.EmployeeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerITest extends AbstractContainerBaseTest {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private WebTestClient webTestClient;

    @DisplayName("Integration test for Save Employee")
    @Test
    void testSaveEmployee() {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setName("Tutku");
        employeeDto.setSurname("Ince");
        employeeDto.setEmail("ti@mail.com");

        WebTestClient.ResponseSpec response = webTestClient.post().uri("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(employeeDto), EmployeeDto.class)
                .exchange();

        response.expectStatus().isCreated()
                .expectBody().consumeWith(System.out::println)
                .jsonPath("$.name").isEqualTo(employeeDto.getName())
                .jsonPath("$.surname").isEqualTo(employeeDto.getSurname())
                .jsonPath("$.email").isEqualTo(employeeDto.getEmail());

    }
}
