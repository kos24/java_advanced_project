package ru.otus.java.advanced.buildingobject.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.otus.java.advanced.buildingobject.mapper.BuildingObjectMapper;
import ru.otus.java.advanced.buildingobject.repository.BuildingObjectRepository;


@Slf4j
@Service
@RequiredArgsConstructor
public class SchedulerService {

    private final BuildingObjectRepository buildingObjectRepository;
    private final BuildingObjectMapper buildingObjectMapper;
    private final DirectChannel outboundMonitoringChannel;

    @Scheduled(fixedRate = 10000)
    public void dispatch() {
        log.info("Dispatching building objects to Building Monitoring Service - started");
        buildingObjectRepository.findAllPending()
                .stream()
                .map(buildingObjectMapper::toBuildingObjectMonitoringDto)
                .map(GenericMessage::new)
                .forEach(outboundMonitoringChannel::send);
        log.info("Dispatching building objects to Building Monitoring Service - completed");
    }
}
