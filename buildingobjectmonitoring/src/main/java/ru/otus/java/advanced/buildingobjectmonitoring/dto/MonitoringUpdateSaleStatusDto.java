package ru.otus.java.advanced.buildingobjectmonitoring.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class MonitoringUpdateSaleStatusDto {
    private UUID monitoringId;
    private UUID saleStatus;
}
