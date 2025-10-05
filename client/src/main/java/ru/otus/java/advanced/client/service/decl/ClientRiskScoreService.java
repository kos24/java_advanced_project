package ru.otus.java.advanced.client.service.decl;

import ru.otus.java.advanced.client.dto.ClientRiskScoreDto;
import ru.otus.java.advanced.client.entity.Client;

public interface ClientRiskScoreService {

    ClientRiskScoreDto calculateRiskScore(Client client);
}
