package com.incetutku.springbootwebflux.service;

import com.incetutku.springbootwebflux.dto.EmployeeDto;
import reactor.core.publisher.Mono;

public interface EmployeeService {
    Mono<EmployeeDto> saveEmployee(EmployeeDto employeeDto);

}
