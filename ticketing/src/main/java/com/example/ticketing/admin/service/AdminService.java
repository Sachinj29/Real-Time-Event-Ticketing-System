package com.example.ticketing.admin.service;

import com.example.ticketing.admin.domain.AuditLog;
import com.example.ticketing.admin.repo.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AuditLogRepository logs;

    public void log(Long actorId, String action, String entity, String entityId, String metadata) {
        AuditLog l = new AuditLog();
        l.setActorId(actorId);
        l.setAction(action);
        l.setEntity(entity);
        l.setEntityId(entityId);
        l.setMetadata(metadata);
        logs.save(l);
    }
}
