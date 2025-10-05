package ru.otus.java.advanced.client.dto;

import lombok.Data;

@Data
public class NewClientDto {
    private String name;
    private String inn;
    private Integer escalationCount;
    private Integer creditHistory;
    private Integer clientReliability;
}
