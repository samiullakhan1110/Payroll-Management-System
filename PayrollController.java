package com.sks.controller;

import com.sks.dto.FinancialReport;
import com.sks.entity.Payroll;
import com.sks.service.PayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PayrollController {

    @Autowired
    private PayrollService payrollService;
    
    @Autowired
    private RestTemplate restTemplate;
    


    @PostMapping("/payroll/process/{employeeId}")
    public Payroll processPayroll(@PathVariable Long employeeId) {
        return payrollService.processPayroll(employeeId);
    }


    @GetMapping("/payroll/{id}")
    public Payroll getPayrollById(@PathVariable Long id) {
        return payrollService.getPayrollById(id);
    }

     @GetMapping("/payroll")
    public List<Payroll> getAllPayrolls() {
        return payrollService.getAllPayrolls();
    }


     @DeleteMapping("/payroll/{id}")
     public void deletePayroll(@PathVariable Long id) {
         payrollService.deletePayroll(id);
     }



    @GetMapping("/payroll/history/{employeeId}")
    public List<Payroll> getPayrollHistory(@PathVariable Long employeeId) {
        return payrollService.getPayrollHistoryForEmployee(employeeId);
    }

    @GetMapping("/payroll/report")
    public FinancialReport generateReport(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        return payrollService.generateReport(startDate, endDate);
    }


    
    
}
