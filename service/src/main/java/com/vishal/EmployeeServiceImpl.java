package com.vishal;

import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Service
public class EmployeeServiceImpl implements EmployeeService{

    static Map<Integer,Employee> employeeList=new HashMap<>();

    static {
        employeeList.put(1,new Employee(1,"akjs","akas","iahs","iahs","jasb",122212,"ksnmks"));
    }
    public void add(Employee employee){
        employeeList.put(employee.getId(),employee);
    }

    public Employee getEmployee(int id) {
        if(!employeeList.containsKey(id)){
            return null;
        }
        return employeeList.get(id);
    }

    public List<Employee> getAllEmployees() {
        if(employeeList.isEmpty()){
            return null;
        }
        List<Employee> list=new ArrayList<>();
        for(int i:employeeList.keySet()){
            list.add(employeeList.get(i));
        }
        return list;
    }

    public Employee updateEmployee(int id,Employee employee) {
        if(!employeeList.containsKey(id))
            return null;
        employeeList.put(id,employee);
        return employee;
    }

    public void deleteEmployee(int id) {
        employeeList.remove(id);
    }
}