package ru.otus.java.advanced.client.service.decl;

import ru.otus.java.advanced.client.dto.ClientDto;
import ru.otus.java.advanced.client.dto.NewClientDto;
import ru.otus.java.advanced.client.entity.Client;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClientService {

    Client add(NewClientDto newClientDto);

    Client update(ClientDto clientDto);

    Optional<Client> findById(UUID id);

    void deleteById(UUID id);

    List<Client> findAll();
}
