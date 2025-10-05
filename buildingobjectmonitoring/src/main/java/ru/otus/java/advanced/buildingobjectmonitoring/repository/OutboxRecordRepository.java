package ru.otus.java.advanced.buildingobjectmonitoring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.java.advanced.buildingobjectmonitoring.entity.OutboxRecord;

import java.util.UUID;

public interface OutboxRecordRepository extends JpaRepository<OutboxRecord, UUID> {
}
