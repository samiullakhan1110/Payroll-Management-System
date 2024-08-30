package com.sks;
import com.sks.entity.Employee;
import com.sks.service.EmployeeService;
import com.sks.exception.EmployeeNotFoundException;
import com.sks.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        employee = new Employee();
        employee.setFirstName("John Doe"); // Set the expected firstName
        // Set other properties if necessary
    }

    @Test
    void testGetEmployeeByIdSuccess() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        Employee foundEmployee = employeeService.getEmployeeById(1L);

        assertNotNull(foundEmployee);
        assertEquals("John Doe", foundEmployee.getFirstName());
    }

    @Test
    void testGetEmployeeByIdNotFound() {
        when(employeeRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployeeById(2L));
    }
    
    @Test
    void testGetAllEmployees() {
        Employee employee2 = new Employee();
        employee2.setFirstName("Jane Doe");
        when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee, employee2));

        List<Employee> employees = employeeService.getAllEmployees();

        assertNotNull(employees);
        assertEquals(2, employees.size());
        assertEquals("John Doe", employees.get(0).getFirstName());
        assertEquals("Jane Doe", employees.get(1).getFirstName());
    }


    @Test
    void testCreateEmployee() {
        when(employeeRepository.save(employee)).thenReturn(employee);

        Employee createdEmployee = employeeService.createEmployee(employee);

        assertNotNull(createdEmployee);
        assertEquals(employee.getFirstName(), createdEmployee.getFirstName());
    }
    
    @Test
    Employee testCreateEmployeeWithNullInput() {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }
        // existing logic to save employee
        return employeeRepository.save(employee);
    }



    @Test
    void testUpdateEmployee() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(employee)).thenReturn(employee);

        Employee updatedEmployee = employeeService.updateEmployee(1L, employee);

        assertNotNull(updatedEmployee);
        assertEquals("John Doe", updatedEmployee.getFirstName());
    }
    
    @Test
    void testUpdateEmployeeNotFound() {
        when(employeeRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, () -> employeeService.updateEmployee(2L, employee));
    }


    @Test
    void testDeleteEmployee() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        doNothing().when(employeeRepository).delete(employee);

        employeeService.deleteEmployeeById(1L);

        verify(employeeRepository, times(1)).delete(employee);
    }
    
    @Test
    void testDeleteEmployeeNotFound() {
        when(employeeRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, () -> employeeService.deleteEmployeeById(2L));
    }

}
