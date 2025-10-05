package ru.otus.java.advanced.datagenerator.client.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class NewBuildingObjectDto {
    private String name;
    private String address;
    private UUID objectTypeId;
    private BigDecimal area;
    private BigDecimal price;
    private UUID contractId;
    private UUID clientId;
    private UUID saleStatus;
}
