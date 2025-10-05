package ru.otus.java.advanced.buildingobjectmonitoring.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class MonitoringUpdateStatusDto {
    private UUID monitoringId;
    private UUID status;
}
