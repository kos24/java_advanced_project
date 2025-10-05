package ru.otus.java.advanced.datagenerator.client;

import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.java.advanced.datagenerator.client.dto.ContractDto;
import ru.otus.java.advanced.datagenerator.client.dto.NewContractDto;

@FeignClient("contract-service")
public interface ContractServiceClient {

    @PostMapping("/api/contract")
    @Retry(name = "default")
    ResponseEntity<ContractDto> createContract(NewContractDto newContractDto);
}
