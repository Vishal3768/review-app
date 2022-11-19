package com.vishal.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vishal.Controller;
import com.vishal.Employee;
import com.vishal.EmployeeRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(Controller.class)
@ActiveProfiles("Test")
public class ControllerTest {
    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    MockMvc mockMvc;
    @MockBean
    EmployeeRepository employeeRepository;

    @Test
    public void addEmployeeTest() throws Exception {
        Employee emp=createEmployee();
        when(employeeRepository.save(any(Employee.class))).thenReturn(emp);

        mockMvc.perform(
                        post("/review-app/add-employee")
                                .with(csrf())
                                .contentType(APPLICATION_JSON)
                                .content(convertObjectToJsonBytes(emp)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.employeeId", Matchers.equalTo(emp.getEmployeeId())))
                .andExpect(jsonPath("$.firstName", Matchers.equalTo(emp.getFirstName())))
                .andExpect(jsonPath("$.email", Matchers.equalTo(emp.getEmail())))
                .andExpect(jsonPath("$.address", Matchers.equalTo(emp.getAddress())));


    }

    @Test
    public void getEmployeeByIdTest() throws Exception{
        Employee emp=createEmployee();
        when(employeeRepository.findById(1)).thenReturn(Optional.of(emp));
        mockMvc.perform(
                get("/review-app/get-employee/"+1)).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.employeeId",Matchers.equalTo(emp.getEmployeeId())));
    }
    private byte[] convertObjectToJsonBytes(Object object) {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        JavaTimeModule module = new JavaTimeModule();
        mapper.registerModule(module);
        byte[] objectBytes = new byte[10];
        try {
            return mapper.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return objectBytes;
    }

    public Employee createEmployee(){
        Employee employee=new Employee();
        employee.setEmployeeId(1);
        employee.setAddress("Kolkata");
        employee.setEmail("vishalchawla@gmail.com");
        employee.setFirstName("Vishal");
        return employee;
    }
}
