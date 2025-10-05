package ru.otus.java.advanced.buildingobjectmonitoring.service.decl;

import ru.otus.java.advanced.buildingobjectmonitoring.dto.BuildingObjectMonitoringDto;
import ru.otus.java.advanced.buildingobjectmonitoring.dto.MonitoringUpdateStatusDto;
import ru.otus.java.advanced.buildingobjectmonitoring.entity.Monitoring;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MonitoringService {
    Monitoring createMonitoringRecord(BuildingObjectMonitoringDto buildingObjectMonitoringDto);

    List<Monitoring> findByContractId(UUID id);

    List<Monitoring> updateClientRiskAndMonitoringPlanDate(UUID clientId);

    Optional<Monitoring> updateMonitoringStatus(MonitoringUpdateStatusDto monitoringUpdateStatusDto);

    Optional<Monitoring> updateSaleStatus(BuildingObjectMonitoringDto buildingObjectMonitoringDto);
}
