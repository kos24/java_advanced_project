package ru.otus.java.advanced.datagenerator.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.otus.advanced.proto.DictionaryResponse;
import ru.otus.java.advanced.datagenerator.client.BuildingObjectServiceClient;
import ru.otus.java.advanced.datagenerator.client.ClientServiceClient;
import ru.otus.java.advanced.datagenerator.client.ContractServiceClient;
import ru.otus.java.advanced.datagenerator.client.DictionaryClient;
import ru.otus.java.advanced.datagenerator.client.dto.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataGeneratorService {
    private final AtomicInteger counter = new AtomicInteger(1);

    private final ClientServiceClient clientServiceClient;
    private final ContractServiceClient contractServiceClient;
    private final BuildingObjectServiceClient buildingObjectServiceClient;
    private final DictionaryClient dictionaryClient;

    @Scheduled(cron = "${cron.emulate: 0 0/1 * * * *}")
    public void generate() {
        String inn = String.valueOf(ThreadLocalRandom.current().nextLong(1_000_000_000L, 10_000_000_000L));
        int number = counter.getAndIncrement();
        Random random = new Random();
        ResponseEntity<ClientDto> client = clientServiceClient.addClient(
                NewClientDto
                        .builder()
                        .inn(inn)
                        .name("Клиент " + number)
                        .escalationCount(random.nextInt(6))
                        .clientReliability(random.nextInt(6))
                        .creditHistory(random.nextInt(6))
                        .build());
        log.info("Created client: {}", client);

        ResponseEntity<ContractDto> contract = contractServiceClient.createContract(
                NewContractDto
                        .builder()
                        .clientId(client.getBody().getId())
                        .contractType(UUID.fromString(dictionaryClient.getDictionaryByCode("Contract_Type_Construction_Loan").getId()))
                        .amount(BigDecimal.TEN)
                        .name("Договор " + number)
                        .build());
        log.info("Created contract: {}", contract);

        DictionaryResponse buildingObjectTypeAppartment = dictionaryClient.getDictionaryByCode("Building_Object_Type_Appartment");
        DictionaryResponse buildingObjectTypeCottage = dictionaryClient.getDictionaryByCode("Building_Object_Type_Cottage");
        List<DictionaryResponse> buildingObjects = new ArrayList<>();
        buildingObjects.add(buildingObjectTypeAppartment);
        buildingObjects.add(buildingObjectTypeCottage);
        DictionaryResponse buildingObjectStatusNotSold = dictionaryClient.getDictionaryByCode("Building_Object_Status_Not_Sold");
        BigDecimal min = new BigDecimal("50.00");
        BigDecimal max = new BigDecimal("1000.00");
        for (int i = 0; i < 5; i++) {
            double randomDouble = ThreadLocalRandom.current().nextDouble(min.doubleValue(), max.doubleValue());
            BigDecimal randomValue = BigDecimal.valueOf(randomDouble).setScale(2, RoundingMode.HALF_UP);

            DictionaryResponse buildingObject = buildingObjects.get(random.nextInt(buildingObjects.size()));
            ResponseEntity<BuildingObjectDto> bo = buildingObjectServiceClient.addBuildingObject(
                    NewBuildingObjectDto
                            .builder()
                            .name(buildingObject.getName() + " " + number)
                            .address("Главный проспект, дом " + number)
                            .objectTypeId(UUID.fromString(buildingObject.getId()))
                            .area(randomValue)
                            .price(randomValue)
                            .contractId(contract.getBody().getId())
                            .clientId(client.getBody().getId())
                            .saleStatus(UUID.fromString(buildingObjectStatusNotSold.getId()))
                            .build());
            log.info("Created Building Object: {}", bo);
        }
    }
}
