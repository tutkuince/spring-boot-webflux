package com.incetutku.springbootwebflux.controller;

import com.incetutku.springbootwebflux.dto.EmployeeDto;
import com.incetutku.springbootwebflux.service.EmployeeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private EmployeeService employeeService;

    @DisplayName("Unit test for Save Employee Operation")
    @Test
    void givenEmployeeObject_whenSaveEmployee_thenReturnSavedEmployee() {
        // given - precondition or setup
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setName("Tutku");
        employeeDto.setSurname("Ince");
        employeeDto.setEmail("ti@mail.com");
        given(employeeService.saveEmployee(any(EmployeeDto.class))).willReturn(Mono.just(employeeDto));

        // when - action or the behaviour that we are going to test
        WebTestClient.ResponseSpec response = webTestClient.post().uri("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(employeeDto), EmployeeDto.class)
                .exchange();

        // then - verify the output
        response.expectStatus().isCreated()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.name").isEqualTo(employeeDto.getName())
                .jsonPath("$.surname").isEqualTo(employeeDto.getSurname())
                .jsonPath("$.email").isEqualTo(employeeDto.getEmail());


    }
}