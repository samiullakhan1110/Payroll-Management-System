package com.sks.entity;
import java.time.LocalDate;


import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "payrolls")
public class Payroll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    @JsonBackReference
    private Employee employee;

    @Column(name = "salary", nullable = false)
    private double salary;

    @Column(name = "tax", nullable = false)
    private double yearlyTax;

    @Column(name = "net_salary", nullable = false)
    private double netSalary;

    @Column(name = "pay_date")
    private LocalDate payDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public double getYearlyTax() {
		return yearlyTax;
	}

	public void setYearlyTax(double yearlyTax) {
		this.yearlyTax = yearlyTax;
	}

	public double getNetSalary() {
		return netSalary;
	}

	public void setNetSalary(double netSalary) {
		this.netSalary = netSalary;
	}

	public LocalDate getPayDate() {
		return payDate;
	}

	public void setPayDate(LocalDate payDate) {
		this.payDate = payDate;
	}

	public Payroll(Long id, Employee employee, double salary, double yearlyTax, double netSalary, LocalDate payDate) {
		super();
		this.id = id;
		this.employee = employee;
		this.salary = salary;
		this.yearlyTax = yearlyTax;
		this.netSalary = netSalary;
		this.payDate = payDate;
	}

	public Payroll() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Payroll [id=" + id + ", employee=" + employee + ", salary=" + salary + ", yearlyTax=" + yearlyTax
				+ ", netSalary=" + netSalary + ", payDate=" + payDate + "]";
	}

    
   }
