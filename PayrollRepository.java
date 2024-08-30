package com.sks.repository;

import com.sks.entity.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PayrollRepository extends JpaRepository<Payroll, Long> {

    // Find payrolls by employee id
    List<Payroll> findByEmployeeId(Long employeeId);

    // Find payrolls by pay date between two dates
    List<Payroll> findByPayDateBetween(LocalDate startDate, LocalDate endDate);
}
