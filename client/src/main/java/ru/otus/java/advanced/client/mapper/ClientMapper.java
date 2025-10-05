package ru.otus.java.advanced.client.mapper;

import org.springframework.stereotype.Component;
import ru.otus.java.advanced.client.dto.ClientDto;
import ru.otus.java.advanced.client.dto.NewClientDto;
import ru.otus.java.advanced.client.entity.Client;

@Component
public class ClientMapper {

    public ClientDto toDto(Client client) {
        return ClientDto
                .builder()
                .id(client.getId())
                .name(client.getName())
                .inn(client.getInn())
                .escalationCount(client.getEscalationCount())
                .creditHistory(client.getCreditHistory())
                .clientReliability(client.getClientReliability())
                .createdAt(client.getCreatedAt())
                .build();
    }

    public Client toEntity(NewClientDto newClientDto) {
        return Client
                .builder()
                .name(newClientDto.getName())
                .inn(newClientDto.getInn())
                .escalationCount(newClientDto.getEscalationCount())
                .creditHistory(newClientDto.getCreditHistory())
                .clientReliability(newClientDto.getClientReliability())
                .deleted(false)
                .build();
    }
}
