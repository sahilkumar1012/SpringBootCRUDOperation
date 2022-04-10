package com.ducat.springboot.rest.controller;


import com.ducat.springboot.rest.model.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MyControllerIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @Sql("/data.sql")
    public void getEmployeeByID() {

        ResponseEntity<Employee> response = testRestTemplate.getForEntity("/employee/1001", Employee.class);
        assertEquals(1001, response.getBody().getId());
    }

    @Test
    public void createEmployeeTest(){
        Employee employee = new Employee();
        employee.setName("new-employee");
        employee.setDepartment("technology");
        employee.setSalary(30000.0);

        HttpEntity<Employee> request = new HttpEntity<>(employee);
        ResponseEntity<Employee> response = testRestTemplate.postForEntity("/employee/add", request, Employee.class);

        assertNotNull(response.getBody().getId());
        assertEquals("new-employee", response.getBody().getName());
    }

}