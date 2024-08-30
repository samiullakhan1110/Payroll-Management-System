package com.sks.service;

import com.sks.entity.Employee;
import com.sks.entity.Payroll;
import com.sks.dto.FinancialReport;
import com.sks.exception.EmployeeNotFoundException;
import com.sks.exception.PayrollNotFoundException;
import com.sks.repository.EmployeeRepository;
import com.sks.repository.FinancialReportRepository;
import com.sks.repository.PayrollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PayrollService {

    @Autowired
    private PayrollRepository payrollRepository;

    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private FinancialReportRepository financialReportRepository;

    public Payroll processPayroll(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + employeeId));

        double salary = calculateSalary(employee);
        double tax = calculateTax(salary);

        Payroll payroll = new Payroll();
        payroll.setEmployee(employee);
        payroll.setSalary(salary);
        payroll.setYearlyTax(tax);
        payroll.setNetSalary(salary - (tax ));
        payroll.setPayDate(LocalDate.now());

        return payrollRepository.save(payroll);
    }

    public List<Payroll> getAllPayrolls() {
        return payrollRepository.findAll();
    }

    public Payroll getPayrollById(Long id) {
        return payrollRepository.findById(id)
            .orElseThrow(() -> new PayrollNotFoundException("Payroll not found with id: " + id));
    }
    
    public void deletePayroll(Long id) {
        Payroll payroll = getPayrollById(id);
        payrollRepository.delete(payroll);
    }

    public List<Payroll> getPayrollHistoryForEmployee(Long employeeId) {
        return payrollRepository.findByEmployeeId(employeeId);
    }

    public FinancialReport generateReport(LocalDate startDate, LocalDate endDate) {
        List<Payroll> payrolls = payrollRepository.findByPayDateBetween(startDate, endDate);

        double totalPayrollCost = payrolls.stream().mapToDouble(Payroll::getSalary).sum();
        double totalTaxesPaid = payrolls.stream().mapToDouble(Payroll::getYearlyTax).sum();

        FinancialReport report = new FinancialReport(startDate, endDate, totalPayrollCost, totalTaxesPaid);

        // Save the report to the database
        financialReportRepository.save(report); 

        return report;
    }

    private double calculateSalary(Employee employee) {
        double baseSalary;
        int age = employee.getAge();
        int rating = employee.getRating();
        String designation = employee.getJobTitle();

        if (age >= 21 && age <= 26) {
            if ("Software Developer".equals(designation)) {
                baseSalary = 40000;
            } else {
                throw new IllegalArgumentException("Invalid designation for age group 21-26");
            }
        } else if (age > 26 && age <= 35) {
            if ("Senior Developer".equals(designation)) {
                baseSalary = 80000;
            } else if ("Tech Lead".equals(designation)) {
                baseSalary = 90000;
            } else if ("Architect".equals(designation)) {
                baseSalary = 100000;
            } else {
                throw new IllegalArgumentException("Invalid designation for age group 26-35");
            }
        } else if (age > 35 && age <= 60) {
            if ("Manager".equals(designation)) {
                baseSalary = 150000;
            } else if ("Senior Manager".equals(designation)) {
                baseSalary = 200000;
            } else if ("Delivery Head".equals(designation)) {
                baseSalary = 300000;
            } else {
                throw new IllegalArgumentException("Invalid designation for age group 35-60");
            }
        } else {
            throw new IllegalArgumentException("Invalid age group");
        }

        if (rating == 2) {
            baseSalary -= baseSalary * 0.05;
        } else if (rating == 3) {
            baseSalary -= baseSalary * 0.10;
        } else if (rating == 4) {
            baseSalary -= baseSalary * 0.15;
        } else if (rating == 5) {
            baseSalary -= baseSalary * 0.20;
        }

        return baseSalary;
    }

    private double calculateTax(double salary) {
        double yearlySalary = salary * 12;

        double yearlyTax;
        if (yearlySalary <= 500000) {
            yearlyTax = 0; // No tax
        } else if (yearlySalary <= 700000) {
            yearlyTax = (yearlySalary - 500000) * 0.10; // 10% tax on for yearly salary over 500,000
        } else if (yearlySalary <= 1000000) {
            yearlyTax = (200000 * 0.10) + ((yearlySalary - 700000) * 0.20); // 20% tax on amount over 700,000
        } else {
            yearlyTax = (200000 * 0.10) + (300000 * 0.20) + ((yearlySalary - 1000000) * 0.30); // 30% tax on amount over 1,000,000
        }

        return yearlyTax/12;
    }

    private double calculateTotalPayrollCost(LocalDate startDate, LocalDate endDate) {
        List<Payroll> payrolls = payrollRepository.findByPayDateBetween(startDate, endDate);
        return payrolls.stream()
                       .mapToDouble(Payroll::getSalary)
                       .sum();
    }

    private double calculateTotalTaxesPaid(LocalDate startDate, LocalDate endDate) {
        List<Payroll> payrolls = payrollRepository.findByPayDateBetween(startDate, endDate);
        return payrolls.stream()
                       .mapToDouble(Payroll::getYearlyTax)
                       .sum();
    }

}
