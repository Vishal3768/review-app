package com.vishal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping(value = "/review-app")
public class Controller {
    @Autowired
    EmployeeServiceImpl service;

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @RequestMapping(value = "/add-employee",method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Employee addEmployee(@RequestBody Employee employee){
        service.add(employee);
        return employee;
    }

    @RequestMapping(value="/update-employee/{id}",method=RequestMethod.PUT)
    public Employee employee(@PathVariable int id,@RequestBody Employee employee){
        Employee emp=service.updateEmployee(id,employee);

        System.out.println("-----------------------");
        System.out.println(emp.getFirstName());
        System.out.println(emp.getAddress());

        if(emp==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Wrong ID");
        }
        return emp;
    }

    @RequestMapping(value = "/get-employee/{id}",method = RequestMethod.GET)
    public Employee getEmployee(@PathVariable int id) {
        Employee emp=service.getEmployee(id);
        if(emp==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Wrong ID");
        }
        return emp;
    }

    @RequestMapping(value = "/get-allEmployees",method = RequestMethod.GET)
    public List<Employee> getAllEmployees(){
        List<Employee> emp=service.getAllEmployees();
        if(emp.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No User Found");
        }
        return emp;
    }

    @RequestMapping(value = "/delete-employee/{id}",method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable int id){
        service.deleteEmployee(id);
    }

}
