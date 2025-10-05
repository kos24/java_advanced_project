package ru.otus.java.advanced.client.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.java.advanced.client.dto.ClientRiskScoreDto;
import ru.otus.java.advanced.client.entity.Client;
import ru.otus.java.advanced.client.service.decl.ClientRiskScoreService;

@Service
public class ClientRiskScoreServiceImpl implements ClientRiskScoreService {

    @Override
    public ClientRiskScoreDto calculateRiskScore(Client client) {
        int rawScore = client.getCreditHistory() * 7 + client.getClientReliability() * 3 + client.getEscalationCount() * 5;
        int riskScore = rawScore <= 25 ? 1 : rawScore <= 50 ? 2 : 3;
        return ClientRiskScoreDto.builder()
                .id(client.getId())
                .riskScore(riskScore)
                .build();
    }
}
