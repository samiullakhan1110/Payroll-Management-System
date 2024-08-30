package com.sks.controller;

import com.sks.entity.Employee;
import com.sks.entity.Payroll;
import com.sks.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

	 @Autowired
	    private RestTemplate restTemplate;


    @Autowired
    private EmployeeService employeeService;
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createEmployee(@RequestBody Employee employee) {
        Employee savedEmployee = employeeService.createEmployee(employee);

       
        Payroll payroll = employeeService.processPayroll(savedEmployee.getId());

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("employee", savedEmployee);
        response.put("payroll", payroll);
        

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
        return employeeService.updateEmployee(id, employeeDetails);
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployeeById(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/call-other-service")
    public String callOtherService() {
        String otherServiceUrl = "http://OTHER-SERVICE-NAME/endpoint";
        return restTemplate.getForObject(otherServiceUrl, String.class);
    }

    
    
}
