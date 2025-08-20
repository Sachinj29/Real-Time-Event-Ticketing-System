package com.example.ticketing.admin.repo;

import com.example.ticketing.admin.domain.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {}
