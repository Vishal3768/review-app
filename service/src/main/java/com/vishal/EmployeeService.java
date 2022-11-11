package com.vishal;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface EmployeeService {
    void add(Employee employee);
    Employee getEmployee(int id);
    List<Employee> getAllEmployees();
    Employee updateEmployee(int id,Employee employee);
    void deleteEmployee(int id);
}
