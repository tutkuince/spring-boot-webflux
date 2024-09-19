package com.incetutku.springbootwebflux.integration.controller;

import com.incetutku.springbootwebflux.dto.EmployeeDto;
import com.incetutku.springbootwebflux.integration.AbstractContainerBaseTest;
import com.incetutku.springbootwebflux.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Collections;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerITest extends AbstractContainerBaseTest {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private WebTestClient webTestClient;

    private EmployeeDto employeeDto;

    @BeforeEach
    void setUp() {
        employeeDto = new EmployeeDto();
        employeeDto.setName("Tutku");
        employeeDto.setSurname("Ince");
        employeeDto.setEmail("ti@mail.com");
    }

    @DisplayName("Integration test for Save Employee")
    @Test
    void testSaveEmployee() {

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

    @DisplayName("Integration test for Get Employee By Id")
    @Test
    void testGetEmployeeById() {
        EmployeeDto savedEmployee = employeeService.saveEmployee(employeeDto).block();

        assert savedEmployee != null;
        WebTestClient.ResponseSpec response = webTestClient.get().uri("/api/v1/employees/{id}", Collections.singletonMap("id", savedEmployee.getId()))
                .exchange();

        response.expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.name").isEqualTo(savedEmployee.getName())
                .jsonPath("$.surname").isEqualTo(savedEmployee.getSurname())
                .jsonPath("$.email").isEqualTo(savedEmployee.getEmail());
    }

    @DisplayName("Integration test for Get All Employees")
    @Test
    void testGetAllEmployee() {
        EmployeeDto employeeDto1 = new EmployeeDto();
        employeeDto1.setName("Utku");
        employeeDto1.setSurname("Ince");
        employeeDto1.setEmail("ui@mail.com");
        employeeService.saveEmployee(employeeDto).block();
        employeeService.saveEmployee(employeeDto1).block();

        WebTestClient.ResponseSpec response = webTestClient.get().uri("/api/v1/employees")
                .exchange();

        response.expectStatus().isOk()
                .expectBody().consumeWith(System.out::println)
                .jsonPath("$.size()").isEqualTo(2);
    }
}
