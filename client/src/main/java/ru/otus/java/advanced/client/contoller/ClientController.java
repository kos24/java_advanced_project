package ru.otus.java.advanced.client.contoller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.java.advanced.client.dto.ClientDto;
import ru.otus.java.advanced.client.dto.ClientRiskScoreDto;
import ru.otus.java.advanced.client.dto.NewClientDto;
import ru.otus.java.advanced.client.mapper.ClientMapper;
import ru.otus.java.advanced.client.service.decl.ClientRiskScoreService;
import ru.otus.java.advanced.client.service.decl.ClientService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final ClientRiskScoreService clientRiskScoreService;
    private final ClientMapper clientMapper;


    @Operation(summary = "Получение списка клиентов")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение списка клиентов",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClientDto.class))}),
            @ApiResponse(responseCode = "404", description = "Клиент не найден",
                    content = @Content)})
    @GetMapping("/api/client")
    public ResponseEntity<List<ClientDto>> getClient() {
        log.info("Запрос на получение списка клиентов");
        return ResponseEntity.ok(clientService.findAll()
                .stream()
                .map(clientMapper::toDto)
                .toList());
    }

    @Operation(summary = "Получение клиента")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение клиента",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClientDto.class))}),
            @ApiResponse(responseCode = "404", description = "Клиент не найден",
                    content = @Content)})
    @GetMapping("/api/client/{id}")
    public ResponseEntity<ClientDto> getClient(@PathVariable("id") UUID id) {
        log.info("Запрос на получение клиента {}", id);
        return clientService.findById(id)
                .map(client -> ResponseEntity.ok(clientMapper.toDto(client)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Создание клиента")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное создание клиента",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClientDto.class))}),
            @ApiResponse(responseCode = "500", description = "Ошибка",
                    content = @Content)})
    @PostMapping("/api/client")
    public ResponseEntity<ClientDto> addClient(@RequestBody NewClientDto newClientDto) {
        log.info("Запрос на создание клиента {}", newClientDto.getName());
        return ResponseEntity.ok(clientMapper.toDto(clientService.add(newClientDto)));
    }

    @Operation(summary = "Обновление клиента")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное обновление клиента",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClientDto.class))}),
            @ApiResponse(responseCode = "500", description = "Ошибка",
                    content = @Content)})
    @PutMapping("/api/client")
    public ResponseEntity<ClientDto> updateClient(@RequestBody ClientDto clientDto) {
        log.info("Запрос на обновление клиента {}", clientDto.getName());
        return ResponseEntity.ok(clientMapper.toDto(clientService.update(clientDto)));
    }

    @Operation(summary = "Получение рейтинговой оценки риска")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение рейтинговой оценки риска",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClientDto.class))}),
            @ApiResponse(responseCode = "404", description = "Рейтинговой оценки риска не найдена",
                    content = @Content)})
    @GetMapping("/api/client/{id}/risk")
    public ResponseEntity<ClientRiskScoreDto> getRiskScore(@PathVariable("id") UUID id) {
        log.info("Запрос на получение получение рейтинговой оценки риска {}", id);
        return clientService.findById(id)
                .map(clientRiskScoreService::calculateRiskScore)
                .map(c -> ResponseEntity.ok().body(c))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
