package com.vishal.repository;

import com.vishal.Employee;
import com.vishal.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@EnableJpaAuditing
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RunWith(SpringRunner.class)
@ActiveProfiles("Test")
public class EmployeeRepositoryTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    private DataSource Datasource;

    @Autowired
    private JdbcTemplate Jdbctemplate;

    @Autowired
    private EntityManager Entitymanager;


    @Test
    public void ComponentsAreNotnull()
    {
        assertThat(Datasource).isNotNull();
        assertThat(Jdbctemplate).isNotNull();
        assertThat(Entitymanager).isNotNull();
        assertThat(employeeRepository).isNotNull();
    }

    @Test
    void addEmployeeTest(){
        Employee employee=new Employee();
        employee.setFirstName("Vishal");
        employee.setLastName("Chawla");
        employee.setAddress("Kolkata");

        Employee emp=employeeRepository.save(employee);

        assertThat(emp).isNotNull();
        assertThat(emp.getEmployeeId()).isNotNull();
        assertThat(emp.getFirstName()).isEqualTo("Vishal");
        assertThat(emp.getAddress()).isEqualTo("Kolkata");
        assertThat(emp.getLastName()).isEqualTo("Chawla");
        assertThat(emp.getPhoneNumber()).isNotNull();
        assertThat(emp.getEmail()).isNull();
        assertThat(emp.getPassword()).isNull();

    }

    @Test
    void getEmployeeTest(){
        Employee employee=new Employee();
        employee.setFirstName("Vishal");
        employee.setLastName("Chawla");
        employee.setAddress("Kolkata");

        Employee emp=employeeRepository.save(employee);
        Employee getEmployee=employeeRepository.findById(emp.getEmployeeId()).get();

        assertThat(getEmployee).isNotNull();
        assertThat(getEmployee.getEmployeeId()).isNotNull();
        assertThat(getEmployee.getFirstName()).isEqualTo("Vishal");
        assertThat(getEmployee.getAddress()).isEqualTo("Kolkata");
        assertThat(getEmployee.getLastName()).isEqualTo("Chawla");
        assertThat(emp.getPhoneNumber()).isNotNull();
        assertThat(emp.getEmail()).isNull();
        assertThat(emp.getPassword()).isNull();
    }

    @Test
    void getAllEmployeeTest(){
        Employee employee1=new Employee();
        employee1.setFirstName("Vishal");
        employee1.setLastName("Chawla");
        employee1.setAddress("Kolkata");

        Employee employee2=new Employee();
        employee2.setFirstName("Vishal");
        employee2.setLastName("Chawla");
        employee2.setAddress("Kolkata");

        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        List<Employee> employeeList=employeeRepository.findAll();

        assertThat(employeeList.size()).isEqualTo(2);
        //assertThat(employeeList.size()).isNotNegative();
    }

    @Test
    void updateEmployeeTest(){
        Employee employee=new Employee();
        employee.setFirstName("Vishal");
        employee.setLastName("Chawla");
        employee.setAddress("Kolkata");

        Employee emp=employeeRepository.save(employee);

        Employee employee2=new Employee();
        employee2.setFirstName("Vish");
        employee2.setLastName("Chaw");
        employee2.setAddress("Kol");

        employee2.setEmployeeId(emp.getEmployeeId());

        Employee emp2=employeeRepository.save(employee2);
        Employee emp3=employeeRepository.findById(emp.getEmployeeId()).get();

        assertThat(emp2.getEmployeeId()).isEqualTo(emp.getEmployeeId());
        assertThat(emp3.getFirstName()).isEqualTo("Vish");

    }

    @Test
    void deleteEmployeeTest(){
        Employee employee=new Employee();
        employee.setFirstName("Vishal");
        employee.setLastName("Chawla");
        employee.setAddress("Kolkata");

        Employee emp=employeeRepository.save(employee);

        int id=emp.getEmployeeId();

        employeeRepository.deleteById(id);

        Optional<Employee> opemp=employeeRepository.findById(id);

        assertThat(opemp.isPresent()).isEqualTo(false);
    }

}


