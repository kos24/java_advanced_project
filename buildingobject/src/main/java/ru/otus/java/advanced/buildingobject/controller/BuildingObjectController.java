package ru.otus.java.advanced.buildingobject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.java.advanced.buildingobject.dto.NewBuildingObjectDto;
import ru.otus.java.advanced.buildingobject.dto.BuildingObjectDto;
import ru.otus.java.advanced.buildingobject.mapper.BuildingObjectMapper;
import ru.otus.java.advanced.buildingobject.service.decl.BuildingObjectService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BuildingObjectController {

    private final BuildingObjectService buildingObjectService;
    private final BuildingObjectMapper buildingObjectMapper;

    @Operation(summary = "Создание объекта недвижимости")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное создание объекта недвижимости",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BuildingObjectDto.class)) }),
            @ApiResponse(responseCode = "500", description = "Ошибка",
                    content = @Content) })
    @PostMapping("/api/building-object")
    public ResponseEntity<BuildingObjectDto> addBuildingObject(@RequestBody NewBuildingObjectDto newBuildingObjectDto) {
        log.info("Запрос на создание объекта недвижимости {}", newBuildingObjectDto.getName());
        return ResponseEntity.ok(buildingObjectMapper.toDto(buildingObjectService.add(newBuildingObjectDto)));
    }

    @Operation(summary = "Получение объекта недвижимости")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение объекта недвижимости",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BuildingObjectDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Объект недвижимости не найден",
                    content = @Content) })
    @GetMapping("/api/building-object/{id}")
    public ResponseEntity<BuildingObjectDto> getBuildingObject(@PathVariable("id") UUID id) {
        log.info("Запрос на получение объекта недвижимости {}", id);
        return buildingObjectService.findById(id)
                .map(buildingObject -> ResponseEntity.ok(buildingObjectMapper.toDto(buildingObject)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Получение списка объектов недвижимости")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение объектов недвижимости",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BuildingObjectDto.class)) })
    })
    @GetMapping("/api/building-object")
    public ResponseEntity<List<BuildingObjectDto>> getBuildingObject() {
        log.info("Запрос на получение списка объектов недвижимости");
        return ResponseEntity.ok(buildingObjectService.findAll()
                .stream()
                .map(buildingObjectMapper::toDto)
                .toList());
    }

    @Operation(summary = "Обновление объекта недвижимости")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное обновление объекта недвижимости",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BuildingObjectDto.class)) }),
            @ApiResponse(responseCode = "500", description = "Ошибка",
                    content = @Content) })
    @PutMapping("/api/building-object")
    public ResponseEntity<BuildingObjectDto> updateBuildingObject(@RequestBody BuildingObjectDto buildingObjectDto) {
        log.info("Запрос на обновление договора: {}", buildingObjectDto.getName());
        return ResponseEntity.ok(buildingObjectMapper.toDto(buildingObjectService.update(buildingObjectDto)));
    }
}
