package com.sks.service;

import com.sks.entity.Employee;
import com.sks.entity.Payroll;
import com.sks.exception.EmployeeNotFoundException;
import com.sks.repository.EmployeeRepository;
import com.sks.repository.PayrollRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PayrollRepository payrollRepository;

    @Autowired
    private PayrollService payrollService;

    @Transactional
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Transactional
    public Payroll processPayroll(Long employeeId) {
        return payrollService.processPayroll(employeeId);
    }

    public Employee updateEmployee(Long id, Employee employeeDetails) {
        Employee employee = getEmployeeById(id);
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmail(employeeDetails.getEmail());
        employee.setJobTitle(employeeDetails.getJobTitle());
        employee.setDepartment(employeeDetails.getDepartment());
        employee.setSalary(employeeDetails.getSalary());
        employee.setAge(employeeDetails.getAge());
        employee.setRating(employeeDetails.getRating());

        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
            .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Transactional
    public void deleteEmployeeById(Long id) {
        Employee employee = getEmployeeById(id); // Ensure employee exists before deleting
        employeeRepository.delete(employee);
    }
}
