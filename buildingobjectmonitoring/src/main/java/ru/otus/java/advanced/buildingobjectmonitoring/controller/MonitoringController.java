package ru.otus.java.advanced.buildingobjectmonitoring.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.java.advanced.buildingobjectmonitoring.dto.BuildingObjectMonitoringDto;
import ru.otus.java.advanced.buildingobjectmonitoring.dto.MonitoringDto;
import ru.otus.java.advanced.buildingobjectmonitoring.dto.MonitoringUpdateSaleStatusDto;
import ru.otus.java.advanced.buildingobjectmonitoring.dto.MonitoringUpdateStatusDto;
import ru.otus.java.advanced.buildingobjectmonitoring.mapper.MonitoringMapper;
import ru.otus.java.advanced.buildingobjectmonitoring.service.decl.MonitoringService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MonitoringController {

    private final MonitoringService monitoringService;
    private final MonitoringMapper monitoringMapper;

    @Operation(summary = "Получение списка объектов поставленных на мониторинг по договору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение списка объектов",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MonitoringDto.class))
            })})
    @GetMapping("/api/monitoring/{contractId}")
    public ResponseEntity<List<MonitoringDto>> getMonitoringByContractId(@PathVariable("contractId") UUID contractId) {
        log.info("Запрос на получение списка объектов поставленных на мониторинг по договору {}", contractId);
        return ResponseEntity.ok(monitoringService.findByContractId(contractId)
                .stream()
                .map(monitoringMapper::toDto)
                .toList());
    }

    @Operation(summary = "Обновление статуса мониторинга")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное обновление статуса мониторинга",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MonitoringDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Ошибка",
                    content = @Content) })
    @PatchMapping("/api/monitoring")
    public ResponseEntity<MonitoringDto> updateMonitoringStatus(@RequestBody MonitoringUpdateStatusDto monitoringUpdateStatusDto) {
        log.info("Запрос на обновление статуса мониторинга {}", monitoringUpdateStatusDto.getMonitoringId());
        return monitoringService.updateMonitoringStatus(monitoringUpdateStatusDto)
                .map(monitoring -> ResponseEntity.ok(monitoringMapper.toDto(monitoring)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Обновление cтатуса продажи объекта недвижимости")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное обновление статуса продажи объекта недвижимости",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MonitoringDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Ошибка",
                    content = @Content) })
    @PutMapping("/api/monitoring")
    public ResponseEntity<MonitoringDto> updateSaleStatus(@RequestBody BuildingObjectMonitoringDto buildingObjectMonitoringDto) {
        log.info("Запрос на обновление статуса продажи объекта недвижимости {}", buildingObjectMonitoringDto.getId());
        return monitoringService.updateSaleStatus(buildingObjectMonitoringDto)
                .map(monitoring -> ResponseEntity.ok(monitoringMapper.toDto(monitoring)))
                .orElse(ResponseEntity.notFound().build());
    }

}
