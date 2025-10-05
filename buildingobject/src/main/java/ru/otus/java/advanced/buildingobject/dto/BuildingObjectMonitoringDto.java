package ru.otus.java.advanced.buildingobject.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class BuildingObjectMonitoringDto {
    private UUID id;
    private UUID contractId;
    private UUID clientId;
    private UUID buildObjectId;
    private UUID saleStatus;
}
