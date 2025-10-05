package ru.otus.java.advanced.buildingobject.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.core.GenericHandler;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;
import ru.otus.java.advanced.buildingobject.dto.BuildingObjectMonitoringDto;
import ru.otus.java.advanced.buildingobject.entity.OutboxRecord;
import ru.otus.java.advanced.buildingobject.repository.OutboxRecordRepository;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BuildingObjectDispatcher implements GenericHandler<BuildingObjectMonitoringDto> {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final OutboxRecordRepository outboxRecordRepository;
    private final ObjectMapper objectMapper;

    @Value("${topic.building-object.monitoring.request}")
    private String topic;

    @Override
    public Object handle(BuildingObjectMonitoringDto payload, MessageHeaders headers) {
        try {
            String message = objectMapper.writeValueAsString(payload);
            kafkaTemplate.send(topic, message)
                    .whenComplete((result, ex) -> handleKafkaResult(payload, ex));
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize building object id: {} to JSON", payload.getId(), e);
        } catch (Exception e) {
            log.error("Failed to send building object id: {}", payload.getId(), e);
        }
        return null;
    }

    private void handleKafkaResult(BuildingObjectMonitoringDto payload, Throwable ex) {
        if (ex == null) {
            log.info("Building object id: {} sent successfully", payload.getId());
            saveOutboxRecord(payload.getId(), OutboxRecord.Status.SENT);
        } else {
            log.error("Failed to send building object id: {}", payload.getId(), ex);
            saveOutboxRecord(payload.getId(), OutboxRecord.Status.FAILED);
        }
    }

    private void saveOutboxRecord(UUID buildingObjectId, OutboxRecord.Status status) {
        outboxRecordRepository.save(
                OutboxRecord.builder()
                        .buildingObjectId(buildingObjectId)
                        .status(status)
                        .build()
        );
        log.info("Outbox record with buildingObjectId {} was saved with status {}", buildingObjectId, status);
    }
}
