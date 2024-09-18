package com.incetutku.springbootwebflux.service.impl;

import com.incetutku.springbootwebflux.dto.EmployeeDto;
import com.incetutku.springbootwebflux.entity.Employee;
import com.incetutku.springbootwebflux.mapper.EmployeeMapper;
import com.incetutku.springbootwebflux.repository.EmployeeRepository;
import com.incetutku.springbootwebflux.service.EmployeeService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Mono<EmployeeDto> saveEmployee(EmployeeDto employeeDto) {
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
        Mono<Employee> savedEmployee = employeeRepository.save(employee);
        return savedEmployee.map(EmployeeMapper::mapToEmployeeDto);
    }
}
