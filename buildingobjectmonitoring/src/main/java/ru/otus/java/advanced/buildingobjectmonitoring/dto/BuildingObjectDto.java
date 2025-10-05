package ru.otus.java.advanced.buildingobjectmonitoring.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class BuildingObjectDto {
    private UUID id;
    private String name;
    private String address;
    private Integer objectTypeId;
    private String objectType;
    private BigDecimal area;
    private BigDecimal price;
    private UUID contractId;
    private UUID clientId;
    private UUID saleStatusId;
    private String saleStatus;
    private Boolean deleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
