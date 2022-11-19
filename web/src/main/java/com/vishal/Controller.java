package com.vishal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/review-app")
public class Controller {
    @Autowired
    EmployeeRepository employeeRepository;

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @RequestMapping(value = "/add-employee",method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Employee addEmployee(@RequestBody Employee employee){
        Employee emp=employeeRepository.save(employee);
        return emp;
    }

    @RequestMapping(value="/update-employee/{id}",method=RequestMethod.PUT)
    public Employee employee(@PathVariable int id,@RequestBody Employee employee){
        employee.setEmployeeId(id);
        if(employeeRepository.existsById(id)){
            employeeRepository.save(employee);
            return employee;
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Wrong ID");
        }
    }

    @RequestMapping(value = "/get-employee/{id}",method = RequestMethod.GET)
    public Employee getEmployee(@PathVariable int id) {

        Optional<Employee> employee=employeeRepository.findById(id);
        if(employee.isPresent()) {
            return employee.get();
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Wrong ID");
        }
    }

    @RequestMapping(value = "/get-allEmployees",method = RequestMethod.GET)
    public List<Employee> getAllEmployees(){
        List<Employee> emp=employeeRepository.findAll();

        if(emp.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No User Found");
        }
        return emp;
    }

    @RequestMapping(value = "/delete-employee/{id}",method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable int id){
        if(employeeRepository.existsById(id))
            employeeRepository.deleteById(id);
    }

}
