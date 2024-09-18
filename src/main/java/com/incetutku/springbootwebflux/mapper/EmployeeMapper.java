package com.incetutku.springbootwebflux.mapper;

import com.incetutku.springbootwebflux.dto.EmployeeDto;
import com.incetutku.springbootwebflux.entity.Employee;

public class EmployeeMapper {

    public static EmployeeDto mapToEmployeeDto(Employee employee) {
        return new EmployeeDto(
                employee.getId(),
                employee.getName(),
                employee.getSurname(),
                employee.getEmail()
        );
    }

    public static Employee mapToEmployee(EmployeeDto employeeDto) {
        return new Employee(
                employeeDto.getId(),
                employeeDto.getName(),
                employeeDto.getSurname(),
                employeeDto.getEmail()
        );
    }
}
