package com.incetutku.springbootwebflux.repository;

import com.incetutku.springbootwebflux.entity.Employee;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface EmployeeRepository extends ReactiveMongoRepository<Employee, String> {
}
