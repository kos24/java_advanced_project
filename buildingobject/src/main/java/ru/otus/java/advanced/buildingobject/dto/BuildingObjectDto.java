package ru.otus.java.advanced.buildingobject.dto;

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
    private UUID objectTypeId;
    private String objectType;
    private BigDecimal area;
    private BigDecimal price;
    private UUID contractId;
    private UUID clientId;
    private UUID saleStatus;
    private String saleStatusName;
    private Boolean deleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
