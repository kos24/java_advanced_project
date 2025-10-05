package ru.otus.java.advanced.datagenerator.client;

import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;
import ru.otus.advanced.proto.*;

import java.util.UUID;

@Component
@Slf4j
public class DictionaryClient {

    @GrpcClient(value = "dictionary-service-grpc")
    private DictionaryServiceGrpc.DictionaryServiceBlockingStub dictionaryService;

    public DictionaryResponse getDictionaryById(UUID id) {
        log.info("Fetching dictionary with id: {}", id);
        return dictionaryService.getDictionaryById(
                DictionaryByIdRequest
                        .newBuilder()
                        .setId(id.toString())
                        .build());
    }

    public DictionaryResponse getDictionaryByCode(String code) {
        log.info("Fetching dictionary with code: {}", code);
        return dictionaryService.getDictionaryByCode(
                DictionaryByCodeRequest
                        .newBuilder()
                        .setCode(code)
                        .build());
    }

}
