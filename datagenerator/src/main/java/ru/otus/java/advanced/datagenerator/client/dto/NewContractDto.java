package ru.otus.java.advanced.datagenerator.client.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class NewContractDto {
    private String name;
    private UUID clientId;
    private UUID contractType;
    private BigDecimal amount;
}
