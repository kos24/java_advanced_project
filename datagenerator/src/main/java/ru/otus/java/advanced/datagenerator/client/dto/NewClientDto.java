package ru.otus.java.advanced.datagenerator.client.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewClientDto {
    private String name;
    private String inn;
    private Integer escalationCount;
    private Integer creditHistory;
    private Integer clientReliability;
}
