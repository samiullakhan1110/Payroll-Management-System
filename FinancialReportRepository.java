package com.sks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sks.dto.FinancialReport;
import org.springframework.stereotype.Repository;

@Repository
public interface FinancialReportRepository extends JpaRepository<FinancialReport, Long> {
}

