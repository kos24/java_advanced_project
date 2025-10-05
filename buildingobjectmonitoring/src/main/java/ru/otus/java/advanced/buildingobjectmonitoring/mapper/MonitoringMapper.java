package ru.otus.java.advanced.buildingobjectmonitoring.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.java.advanced.buildingobjectmonitoring.client.DictionaryClient;
import ru.otus.java.advanced.buildingobjectmonitoring.dto.BuildingObjectMonitoringDto;
import ru.otus.java.advanced.buildingobjectmonitoring.dto.MonitoringDto;
import ru.otus.java.advanced.buildingobjectmonitoring.entity.Monitoring;

@Component
@RequiredArgsConstructor
public class MonitoringMapper {

    private final DictionaryClient dictionaryClient;

    public MonitoringDto toDto(Monitoring monitoring) {
        return MonitoringDto
                .builder()
                .id(monitoring.getId())
                .buildingObjectId(monitoring.getBuildingObjectId())
                .contractId(monitoring.getContractId())
                .clientId(monitoring.getClientId())
                .saleStatus(monitoring.getSaleStatus())
                .saleStatusName(dictionaryClient.getDictionaryById(monitoring.getSaleStatus()).getName())
                .clientRiskScore(monitoring.getClientRiskScore())
                .planMonitoringDate(monitoring.getPlanMonitoringDate())
                .monitoringStatus(monitoring.getMonitoringStatus())
                .monitoringStatusName(dictionaryClient.getDictionaryById(monitoring.getMonitoringStatus()).getName())
                .build();
    }

    public BuildingObjectMonitoringDto toBuildingObjectMonitoringDto(Monitoring monitoring) {
        return BuildingObjectMonitoringDto
                .builder()
                .id(monitoring.getId())
                .contractId(monitoring.getContractId())
                .clientId(monitoring.getClientId())
                .buildObjectId(monitoring.getBuildingObjectId())
                .saleStatus(monitoring.getSaleStatus())
                .build();
    }
}
