package ru.otus.java.advanced.buildingobject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.java.advanced.buildingobject.entity.OutboxRecord;

import java.util.UUID;

public interface OutboxRecordRepository extends JpaRepository<OutboxRecord, UUID> {
}
