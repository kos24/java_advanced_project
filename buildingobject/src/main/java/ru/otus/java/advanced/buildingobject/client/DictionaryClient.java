package ru.otus.java.advanced.buildingobject.client;

import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;
import ru.otus.advanced.proto.DictionaryByCodeRequest;
import ru.otus.advanced.proto.DictionaryByIdRequest;
import ru.otus.advanced.proto.DictionaryResponse;
import ru.otus.advanced.proto.DictionaryServiceGrpc;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class DictionaryClient {

    private final Map<UUID, DictionaryResponse> cacheById = new ConcurrentHashMap<>();
    private final Map<String, DictionaryResponse> cacheByCode = new ConcurrentHashMap<>();

    @GrpcClient(value = "dictionary-service-grpc")
    private DictionaryServiceGrpc.DictionaryServiceBlockingStub dictionaryService;

    public DictionaryResponse getDictionaryById(UUID id) {
        log.info("Fetching dictionary with id: {}", id);
        return cacheById.computeIfAbsent(id, id1 -> dictionaryService.getDictionaryById(
                DictionaryByIdRequest
                        .newBuilder()
                        .setId(id1.toString())
                        .build()));
    }

    public DictionaryResponse getDictionaryByCode(String code) {
        log.info("Fetching dictionary with code: {}", code);
        return cacheByCode.computeIfAbsent(code, code1 -> dictionaryService.getDictionaryByCode(
                DictionaryByCodeRequest
                        .newBuilder()
                        .setCode(code1)
                        .build()));
    }
}
