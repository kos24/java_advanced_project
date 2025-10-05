package ru.otus.java.advanced.buildingobjectmonitoring.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@Data
public class MonitoringDto {
    private UUID id;
    private UUID buildingObjectId;
    private UUID contractId;
    private UUID clientId;
    private UUID saleStatus;
    private String saleStatusName;
    private Integer clientRiskScore;
    private LocalDate planMonitoringDate;
    private UUID monitoringStatus;
    private String monitoringStatusName;
}
