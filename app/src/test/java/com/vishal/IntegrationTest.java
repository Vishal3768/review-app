package com.vishal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("Test")
public class IntegrationTest {
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void addEmployee() throws Exception {
        Employee emp=createEmployee();

        mockMvc.perform(
                        post( "/review-app/add-employee").contentType(APPLICATION_JSON).content(convertObjectToJsonBytes(emp)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", Matchers.equalTo(emp.getFirstName())));
        Optional<Employee> employee=employeeRepository.findByFirstName(emp.getFirstName());
        assertTrue(employee.isPresent());
        assertThat(employee).isNotNull();
        assertThat(employee.get().getEmployeeId()).isNotNull();
        assertEquals(employee.get().getAddress(),emp.getAddress());
        assertEquals(employee.get().getEmail(),emp.getEmail());
        assertEquals(employee.get().getFirstName(),emp.getFirstName());
    }
    @Test
    public void updateEmployeeTest() throws Exception {
        Employee emp = new Employee();
        Optional<Employee> employee = employeeRepository.findByFirstName("Vishal");
        int id = employee.get().getEmployeeId();
        emp.setFirstName("Ronhan");
        emp.setEmail("rohan.chawla@xyz.com");
        emp.setAddress("Kolkata");
        mockMvc.perform(
                put("/review-app/update-employee/"+id)
                        .contentType(APPLICATION_JSON)
                        .content(convertObjectToJsonBytes(emp))
        ).andExpect(status().isOk());
    }
    @Test
    public void getEmployeeByIdTest() throws Exception {
        Optional<Employee> employee = employeeRepository.findByFirstName("Rohan");
        int id = employee.get().getEmployeeId();
        mockMvc.perform(get("/review-app/get-employee/"+id)).andExpect(status().isOk());
    }
    @Test
    public void getAllEmployeesTest() throws Exception {
        mockMvc.perform(get("/review-app/get-allEmployees")).andExpect(status().isOk());
    }
    @Test
    public void deleteEmployeeTest() throws Exception {
        Optional<Employee> employee = employeeRepository.findByFirstName("Rohan");
        int id = employee.get().getEmployeeId();
        mockMvc.perform(delete("/review-app/delete-employee/"+id)).andExpect(status().isNoContent());
    }
    public Employee createEmployee(){
        Employee employee=new Employee();
        employee.setAddress("Kolkata");
        employee.setEmail("vishalchawla@gmail.com");
        employee.setFirstName("Vishal");
        return employee;
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

}
