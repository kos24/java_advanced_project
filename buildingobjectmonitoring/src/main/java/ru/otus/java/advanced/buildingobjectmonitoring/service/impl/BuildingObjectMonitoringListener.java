package ru.otus.java.advanced.buildingobjectmonitoring.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.otus.java.advanced.buildingobjectmonitoring.dto.BuildingObjectMonitoringDto;
import ru.otus.java.advanced.buildingobjectmonitoring.service.decl.MonitoringService;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class BuildingObjectMonitoringListener {

    private final ObjectMapper objectMapper;
    private final MonitoringService monitoringService;

    @KafkaListener(topics = "${topic.building-object.monitoring.request}", groupId = "building-monitoring")
    public void listenBuildingObject(@Payload String message) {
        log.info("Received message from Building object service: {}", message);
        try {
            BuildingObjectMonitoringDto buildingObjectMonitoringDto = objectMapper.readValue(message, BuildingObjectMonitoringDto.class);
            monitoringService.createMonitoringRecord(buildingObjectMonitoringDto);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
    }

    @KafkaListener(topics = "${topic.alert.risk.score}", groupId = "client-monitoring")
    public void listenClientRiskAlert(@Payload String message) {
        log.info("Received message from Client service: {}", message);
            UUID clientId = UUID.fromString(message);
            monitoringService.updateClientRiskAndMonitoringPlanDate(clientId);
    }
}
