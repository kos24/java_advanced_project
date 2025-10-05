package ru.otus.java.advanced.contract.dto;

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
