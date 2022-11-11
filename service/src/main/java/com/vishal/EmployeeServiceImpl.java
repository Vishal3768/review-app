package com.vishal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class EmployeeServiceImpl{

    @Autowired
    EmployeeRepository employeeRepository;
    public void add(Employee employee){
        employeeRepository.save(employee);
    }

    public Employee getEmployee(int id) {
        Optional<Employee> employee=employeeRepository.findById(id);
        if(employee.isPresent())
            return employee.get();
        return null;
    }

    public List<Employee> getAllEmployees() {
       List<Employee> employeeList=employeeRepository.findAll();
       return employeeList;
    }

    public Employee updateEmployee(int id,Employee employee) {
        if(employeeRepository.existsById(id)){
            employeeRepository.deleteById(id);
            employeeRepository.save(employee);
            return employee;
        }
        return null;
    }

    public void deleteEmployee(int id) {
        if(employeeRepository.existsById(id))
            employeeRepository.deleteById(id);
    }
}
