package ru.otus.java.advanced.buildingobjectmonitoring.client;

import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.otus.java.advanced.buildingobjectmonitoring.dto.ClientRiskScoreDto;

import java.util.UUID;

@FeignClient("client-service")
public interface ClientServiceClient {

    @GetMapping("/api/client/{id}/risk")
    @Retry(name = "default")
    ResponseEntity<ClientRiskScoreDto> getRiskScore(@PathVariable("id") UUID clientId);
}
