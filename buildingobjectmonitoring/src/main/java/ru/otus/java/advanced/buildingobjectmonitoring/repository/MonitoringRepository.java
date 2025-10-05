package ru.otus.java.advanced.buildingobjectmonitoring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.otus.java.advanced.buildingobjectmonitoring.entity.Monitoring;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MonitoringRepository extends JpaRepository<Monitoring, UUID> {

    List<Monitoring> findAllByContractId(UUID contractId);
    List<Monitoring> findByClientId(UUID clientId);

    @Query(nativeQuery = true,
            value = """
            SELECT *
            FROM building_object_monitoring.monitoring m
            ORDER BY m.created_at
            LIMIT 1
       """)
    Optional<Monitoring> getOldestMonitoringObject();
}
