package com.sks.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "report")
public class FinancialReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "total_payroll_cost", nullable = false)
    private double totalPayrollCost;

    @Column(name = "total_taxes_paid", nullable = false)
    private double totalTaxesPaid;
    
    
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public double getTotalPayrollCost() {
		return totalPayrollCost;
	}
	public void setTotalPayrollCost(double totalPayrollCost) {
		this.totalPayrollCost = totalPayrollCost;
	}
	public double getTotalTaxesPaid() {
		return totalTaxesPaid;
	}
	public void setTotalTaxesPaid(double totalTaxesPaid) {
		this.totalTaxesPaid = totalTaxesPaid;
	}
	public FinancialReport(LocalDate startDate, LocalDate endDate, double totalPayrollCost, double totalTaxesPaid) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.totalPayrollCost = totalPayrollCost;
		this.totalTaxesPaid = totalTaxesPaid;
	}
	public FinancialReport() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "FinancialReport [startDate=" + startDate + ", endDate=" + endDate + ", totalPayrollCost="
				+ totalPayrollCost + ", totalTaxesPaid=" + totalTaxesPaid + "]";
	}
    
    
    
}
