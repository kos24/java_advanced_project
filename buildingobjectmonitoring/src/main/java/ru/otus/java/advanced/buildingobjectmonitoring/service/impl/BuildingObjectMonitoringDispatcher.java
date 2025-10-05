package ru.otus.java.advanced.buildingobjectmonitoring.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.otus.java.advanced.buildingobjectmonitoring.dto.BuildingObjectMonitoringDto;
import ru.otus.java.advanced.buildingobjectmonitoring.entity.OutboxRecord;
import ru.otus.java.advanced.buildingobjectmonitoring.repository.OutboxRecordRepository;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BuildingObjectMonitoringDispatcher {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final OutboxRecordRepository outboxRecordRepository;
    private final ObjectMapper objectMapper;

    @Value("${topic.building-object.sale.request}")
    private String topic;

    public void send(BuildingObjectMonitoringDto payload) {
        try {
            String message = objectMapper.writeValueAsString(payload);
            kafkaTemplate.send(topic, message)
                    .whenComplete((result, ex) -> handleKafkaResult(payload, ex));
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize building object id: {} to JSON for sale notification", payload.getId(), e);
        } catch (Exception e) {
            log.error("Failed to send building object (id: {}) to kafka topic {}", payload.getId(), topic, e);
        }
    }

    private void handleKafkaResult(BuildingObjectMonitoringDto payload, Throwable ex) {
        if (ex == null) {
            log.info("Building object (id: {}) sale notification sent successfully", payload.getId());
            saveOutboxRecord(payload.getId(), OutboxRecord.Status.SENT);
        } else {
            log.error("Failed to send building object (id: {}) sale notification", payload.getId(), ex);
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
