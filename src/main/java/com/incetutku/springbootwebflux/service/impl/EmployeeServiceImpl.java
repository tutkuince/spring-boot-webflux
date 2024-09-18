package com.incetutku.springbootwebflux.service.impl;

import com.incetutku.springbootwebflux.dto.EmployeeDto;
import com.incetutku.springbootwebflux.entity.Employee;
import com.incetutku.springbootwebflux.mapper.EmployeeMapper;
import com.incetutku.springbootwebflux.repository.EmployeeRepository;
import com.incetutku.springbootwebflux.service.EmployeeService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
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

    @Override
    public Mono<EmployeeDto> getEmployeeById(String id) {
        Mono<Employee> savedEmployee = employeeRepository.findById(id);
        return savedEmployee.map(EmployeeMapper::mapToEmployeeDto);
    }

    @Override
    public Flux<EmployeeDto> getAllEmployees() {
        return employeeRepository.findAll().map(EmployeeMapper::mapToEmployeeDto).switchIfEmpty(Flux.empty());
    }

    @Override
    public Mono<EmployeeDto> updateEmployee(String id, EmployeeDto employeeDto) {
        Mono<Employee> savedEmployee = employeeRepository.findById(id);
        Mono<Employee> updatedEmployee = savedEmployee.flatMap((existingEmployee) -> {
            existingEmployee.setName(employeeDto.getName());
            existingEmployee.setSurname(employeeDto.getSurname());
            existingEmployee.setEmail(employeeDto.getEmail());

            return employeeRepository.save(existingEmployee);
        });
        return updatedEmployee.map(EmployeeMapper::mapToEmployeeDto);
    }

    @Override
    public Mono<Void> deleteEmployeeById(String id) {
        return employeeRepository.deleteById(id);
    }
}
