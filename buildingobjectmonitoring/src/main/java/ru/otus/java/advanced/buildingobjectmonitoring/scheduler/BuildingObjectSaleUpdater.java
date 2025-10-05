package ru.otus.java.advanced.buildingobjectmonitoring.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.otus.java.advanced.buildingobjectmonitoring.client.DictionaryClient;
import ru.otus.java.advanced.buildingobjectmonitoring.controller.MonitoringController;
import ru.otus.java.advanced.buildingobjectmonitoring.mapper.MonitoringMapper;
import ru.otus.java.advanced.buildingobjectmonitoring.repository.MonitoringRepository;

import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
public class BuildingObjectSaleUpdater {

    private final MonitoringRepository monitoringRepository;
    private final DictionaryClient dictionaryClient;
    private final MonitoringMapper monitoringMapper;
    private final MonitoringController monitoringController;

    @Scheduled(cron = "${update.sale.status.scheduler:0 0/1 * * * *}")
    public void dispatch() {
        log.info("Обновление статуса продажи объекта недвижимости");
        monitoringRepository.getOldestMonitoringObject()
                .map(o -> {
                    o.setSaleStatus(UUID.fromString(dictionaryClient.getDictionaryByCode("Building_Object_Status_Sold").getId()));
                    return o;
                })
                .map(monitoringMapper::toBuildingObjectMonitoringDto)
                .ifPresent(monitoringController::updateSaleStatus);
    }
}

