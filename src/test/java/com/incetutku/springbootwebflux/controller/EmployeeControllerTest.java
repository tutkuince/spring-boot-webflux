package com.incetutku.springbootwebflux.controller;

import com.incetutku.springbootwebflux.dto.EmployeeDto;
import com.incetutku.springbootwebflux.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private EmployeeService employeeService;

    private EmployeeDto employeeDto;

    @BeforeEach
    void setUp() {
        employeeDto = new EmployeeDto();
        employeeDto.setName("Tutku");
        employeeDto.setSurname("Ince");
        employeeDto.setEmail("ti@mail.com");
    }

    @DisplayName("Unit test for Save Employee Operation")
    @Test
    void givenEmployeeObject_whenSaveEmployee_thenReturnSavedEmployeeObject() {
        // given - precondition or setup
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

    @DisplayName("Unit test for Get Employee By Id Operation")
    @Test
    void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() {
        // given - precondition or setup
        String employeeId = "123";
        given(employeeService.getEmployeeById(employeeId)).willReturn(Mono.just(employeeDto));

        // when - action or the behaviour that we are going to test
        WebTestClient.ResponseSpec response = webTestClient.get()
                .uri("/api/v1/employees/{id}", Collections.singletonMap("id", employeeId))
                .exchange();

        // then - verify the output
        response.expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.name").isEqualTo(employeeDto.getName())
                .jsonPath("$.surname").isEqualTo(employeeDto.getSurname())
                .jsonPath("$.email").isEqualTo(employeeDto.getEmail());
    }

    @DisplayName("Unit test for Get All Employee Operation")
    @Test
    void givenListOfEmployee_whenGetAllEmployees_thenReturnListOfEmployees() {
        // given - precondition or setup
        List<EmployeeDto> employeeDtoList = new ArrayList<>();
        employeeDtoList.add(employeeDto);

        EmployeeDto employeeDto1 = new EmployeeDto();
        employeeDto1.setName("Utku");
        employeeDto1.setSurname("Ince");
        employeeDto1.setEmail("utku@mail.com");
        employeeDtoList.add(employeeDto1);

        Flux<EmployeeDto> employeeDtoFlux = Flux.fromIterable(employeeDtoList);

        given(employeeService.getAllEmployees()).willReturn(employeeDtoFlux);

        // when - action or the behaviour that we are going to test
        WebTestClient.ResponseSpec response = webTestClient.get().uri("/api/v1/employees").exchange();

        // then - verify the output
        response.expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.size()", employeeDtoList.size());
    }

    @DisplayName("Unit test for Update Employee By Id Operation")
    @Test
    void givenEmployeeIdAndEmployee_whenUpdateEmployee_thenReturnUpdatedEmployeeObject() {
        // given - precondition or setup
        String employeeId = "123";
        given(employeeService.updateEmployee(any(String.class), any(EmployeeDto.class))).willReturn(Mono.just(employeeDto));

        // when - action or the behaviour that we are going to test
        WebTestClient.ResponseSpec response = webTestClient.put()
                .uri("/api/v1/employees/{id}", Collections.singletonMap("id", employeeId))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(employeeDto), EmployeeDto.class)
                .exchange();

        // then - verify the output
        response.expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.name").isEqualTo(employeeDto.getName())
                .jsonPath("$.surname").isEqualTo(employeeDto.getSurname())
                .jsonPath("$.email").isEqualTo(employeeDto.getEmail());
    }

    @DisplayName("Unit test for Delete Employee By Id Operation")
    @Test
    void givenEmployeeId_whenDeleteEmployeeId_thenReturnNothing() {
        // given - precondition or setup
        String employeeId = "123";
        given(employeeService.deleteEmployeeById(any(String.class))).willReturn(Mono.empty());

        // when - action or the behaviour that we are going to test
        WebTestClient.ResponseSpec response = webTestClient.delete()
                .uri("/api/v1/employees/{id}", Collections.singletonMap("id", employeeId))
                .exchange();

        // then - verify the output
        response.expectStatus().isNoContent()
                .expectBody()
                .consumeWith(System.out::println);
    }
}