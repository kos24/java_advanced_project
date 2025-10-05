package ru.otus.java.advanced.buildingobject.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.otus.java.advanced.buildingobject.dto.BuildingObjectMonitoringDto;
import ru.otus.java.advanced.buildingobject.service.decl.BuildingObjectService;

@Component
@Slf4j
@RequiredArgsConstructor
public class BuildingObjectListener {

    private final ObjectMapper objectMapper;
    private final BuildingObjectService buildingObjectService;

    @KafkaListener(topics = "${topic.building-object.sale.request}", groupId = "building-object")
    public void listen(@Payload String message) {
        log.info("Received message from Building object monitoring service for sale status update: {}", message);
        try {
            BuildingObjectMonitoringDto buildingObjectMonitoringDto = objectMapper.readValue(message, BuildingObjectMonitoringDto.class);
            buildingObjectService.updateSaleStatus(buildingObjectMonitoringDto);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
    }
}
