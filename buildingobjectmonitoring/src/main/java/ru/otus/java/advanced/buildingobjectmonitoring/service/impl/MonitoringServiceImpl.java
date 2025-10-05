package ru.otus.java.advanced.buildingobjectmonitoring.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.java.advanced.buildingobjectmonitoring.client.ClientServiceClient;
import ru.otus.java.advanced.buildingobjectmonitoring.client.ContractServiceClient;
import ru.otus.java.advanced.buildingobjectmonitoring.client.DictionaryClient;
import ru.otus.java.advanced.buildingobjectmonitoring.dto.*;
import ru.otus.java.advanced.buildingobjectmonitoring.entity.Monitoring;
import ru.otus.java.advanced.buildingobjectmonitoring.mapper.MonitoringMapper;
import ru.otus.java.advanced.buildingobjectmonitoring.repository.MonitoringRepository;
import ru.otus.java.advanced.buildingobjectmonitoring.service.decl.MonitoringService;
import ru.otus.java.advanced.buildingobjectmonitoring.service.decl.PlanDateCalcService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MonitoringServiceImpl implements MonitoringService {

    private final MonitoringRepository monitoringRepository;
    private final ContractServiceClient contractServiceClient;
    private final ClientServiceClient clientServiceClient;
    private final PlanDateCalcService planDateCalcService;
    private final DictionaryClient dictionaryClient;
    private final BuildingObjectMonitoringDispatcher buildingObjectMonitoringDispatcher;

    @Override
    public Monitoring createMonitoringRecord(BuildingObjectMonitoringDto buildingObjectMonitoringDto) {
        return Optional.ofNullable(contractServiceClient.getContract(buildingObjectMonitoringDto.getContractId()).getBody())
                .map(contractDto -> clientServiceClient.getRiskScore(contractDto.getClientId()).getBody())
                .map(riskScoreDto -> {
                    Integer riskScore = riskScoreDto.getRiskScore();
                    LocalDate planDate = planDateCalcService.calculateMonitoringPlanDate(riskScore);
                    Monitoring monitoring = Monitoring.builder()
                            .buildingObjectId(buildingObjectMonitoringDto.getId())
                            .contractId(buildingObjectMonitoringDto.getContractId())
                            .clientId(buildingObjectMonitoringDto.getClientId())
                            .saleStatus(buildingObjectMonitoringDto.getSaleStatus())
                            .monitoringStatus(UUID.fromString(dictionaryClient.getDictionaryByCode("Category_Monitoring_In_Process").getId()))
                            .clientRiskScore(riskScore)
                            .planMonitoringDate(planDate)
                            .build();
                    log.info("Creating new building object for monitoring: {} ", monitoring.toString());
                    return monitoringRepository.save(monitoring);
                })
                .orElseThrow(() -> new RuntimeException("Failed to create monitoring object"));
    }

    @Override
    public List<Monitoring> findByContractId(UUID id) {
        return monitoringRepository.findAllByContractId(id);
    }

    @Override
    public List<Monitoring> updateClientRiskAndMonitoringPlanDate(UUID clientId) {
        return monitoringRepository.findByClientId(clientId).stream().map(monitoring -> {
            ClientRiskScoreDto riskScoreDto = clientServiceClient.getRiskScore(clientId).getBody();
            monitoring.setClientRiskScore(riskScoreDto.getRiskScore());
            monitoring.setPlanMonitoringDate(planDateCalcService.updateMonitoringPlanDate(riskScoreDto.getRiskScore(), monitoring.getPlanMonitoringDate()));
            log.info("Updated monitoring plan date for building object id: {} ", monitoring.getBuildingObjectId().toString());
            return monitoringRepository.save(monitoring);
        }).toList();
    }


    @Override
    public Optional<Monitoring> updateMonitoringStatus(MonitoringUpdateStatusDto monitoringUpdateStatusDto) {
        return monitoringRepository.findById(monitoringUpdateStatusDto.getMonitoringId()).map(m -> {
            m.setMonitoringStatus(monitoringUpdateStatusDto.getStatus());
            return monitoringRepository.save(m);
        });
    }

    @Override
    @Transactional
    public Optional<Monitoring> updateSaleStatus(BuildingObjectMonitoringDto buildingObjectMonitoringDto) {
        return monitoringRepository.findById(buildingObjectMonitoringDto.getId()).map(m -> {
            m.setSaleStatus(buildingObjectMonitoringDto.getSaleStatus());
            Monitoring saved = monitoringRepository.save(m);
            buildingObjectMonitoringDispatcher.send(buildingObjectMonitoringDto);
            return saved;
        });
    }
}
