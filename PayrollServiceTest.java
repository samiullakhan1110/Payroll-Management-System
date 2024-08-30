package com.sks;

import com.sks.entity.Employee;
import com.sks.entity.Payroll;
import com.sks.service.PayrollService;
import com.sks.dto.FinancialReport;
import com.sks.exception.EmployeeNotFoundException;
import com.sks.repository.EmployeeRepository;
import com.sks.repository.FinancialReportRepository;
import com.sks.repository.PayrollRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PayrollServiceTest {

    @Mock
    private PayrollRepository payrollRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private FinancialReportRepository financialReportRepository;

    @InjectMocks
    private PayrollService payrollService;

    private Employee employee;
    private Payroll payroll;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("John Doe");
        employee.setAge(30);
        employee.setRating(3);
        employee.setJobTitle("Senior Developer");

        payroll = new Payroll();
        payroll.setEmployee(employee);
        payroll.setSalary(80000);
        payroll.setYearlyTax(80000 * 0.10 * 12); 
        payroll.setNetSalary(payroll.getSalary() - (payroll.getYearlyTax() / 12));
        payroll.setPayDate(LocalDate.now());
        
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(payrollRepository.save(any(Payroll.class))).thenReturn(payroll);  // Ensure save is properly mocked
    }


    @Test
    void testProcessPayroll() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(payrollRepository.save(any(Payroll.class))).thenReturn(payroll);

        Payroll processedPayroll = payrollService.processPayroll(1L);

        assertNotNull(processedPayroll);
        assertEquals(employee, processedPayroll.getEmployee());
        assertEquals(80000, processedPayroll.getSalary());
        assertEquals(80000 * 0.10 * 12, processedPayroll.getYearlyTax(), 0.01);
    }
    
    @Test
    void testProcessPayrollAlreadyProcessed() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(payrollRepository.findByEmployeeId(1L)).thenReturn(Collections.singletonList(payroll));

        Payroll existingPayroll = payrollService.processPayroll(1L);

        assertNotNull(existingPayroll);
        assertEquals(payroll, existingPayroll);
    }

    
    @Test
    void testProcessPayrollEmployeeNotFound() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, () -> payrollService.processPayroll(1L));
    }
    
    @Test
    void testProcessPayrollWithInvalidSalary() {
        
        Employee invalidEmployee = new Employee();
        invalidEmployee.setId(1L);
        invalidEmployee.setAge(20); // Invalid age
        invalidEmployee.setJobTitle("Invalid Job Title"); // Invalid designation

        // Mock repository to return the invalid employee
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(invalidEmployee));

        // Assert that an IllegalArgumentException is thrown
        assertThrows(IllegalArgumentException.class, () -> payrollService.processPayroll(1L));
    }

    
    @Test
    void testDeletePayrollById() {
        when(payrollRepository.findById(1L)).thenReturn(Optional.of(payroll));
        doNothing().when(payrollRepository).delete(payroll);  // Mock delete with Payroll object

        payrollService.deletePayroll(1L);

        verify(payrollRepository, times(1)).delete(payroll);  // Verify delete with Payroll object
    }



    
    @Test
    void testGetPayrollHistoryForEmployee() {
        when(payrollRepository.findByEmployeeId(1L)).thenReturn(Collections.singletonList(payroll));

        var payrolls = payrollService.getPayrollHistoryForEmployee(1L);

        assertNotNull(payrolls);
        assertEquals(1, payrolls.size());
        assertEquals(payroll, payrolls.get(0));
    }
    @Test
    void testGenerateReport() {
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 12, 31);

        when(payrollRepository.findByPayDateBetween(startDate, endDate))
                .thenReturn(Collections.singletonList(payroll));
        when(financialReportRepository.save(any(FinancialReport.class))).thenAnswer(invocation -> invocation.getArgument(0));

        FinancialReport report = payrollService.generateReport(startDate, endDate);

        assertNotNull(report);
        assertEquals(startDate, report.getStartDate());
        assertEquals(endDate, report.getEndDate());
        assertEquals(payroll.getSalary(), report.getTotalPayrollCost(), 0.01);
        assertEquals(payroll.getYearlyTax(), report.getTotalTaxesPaid(), 0.01);
    }
    
    @Test
    void testGenerateReportWithNoPayrollData() {
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 12, 31);

        when(payrollRepository.findByPayDateBetween(startDate, endDate))
                .thenReturn(Collections.emptyList());
        when(financialReportRepository.save(any(FinancialReport.class))).thenAnswer(invocation -> invocation.getArgument(0));

        FinancialReport report = payrollService.generateReport(startDate, endDate);

        assertNotNull(report);
        assertEquals(startDate, report.getStartDate());
        assertEquals(endDate, report.getEndDate());
        assertEquals(0, report.getTotalPayrollCost(), 0.01);
        assertEquals(0, report.getTotalTaxesPaid(), 0.01);
    }


}
