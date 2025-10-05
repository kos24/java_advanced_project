package ru.otus.java.advanced.contract.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.java.advanced.contract.client.DictionaryClient;
import ru.otus.java.advanced.contract.dto.ContractDto;
import ru.otus.java.advanced.contract.dto.NewContractDto;
import ru.otus.java.advanced.contract.entity.Contract;

@Component
@RequiredArgsConstructor
public class ContractMapper {

    private final DictionaryClient dictionaryClient;

    public Contract toEntity(NewContractDto newContractDto) {
        return Contract
                .builder()
                .name(newContractDto.getName())
                .clientId(newContractDto.getClientId())
                .contractType(newContractDto.getContractType())
                .amount(newContractDto.getAmount())
                .build();
    }

    public ContractDto toDto(Contract contract) {
        return ContractDto
                .builder()
                .id(contract.getId())
                .name(contract.getName())
                .contractTypeId(contract.getContractType())
                .contractTypeName(dictionaryClient.getDictionaryById(contract.getContractType()).getName())
                .clientId(contract.getClientId())
                .amount(contract.getAmount())
                .createdAt(contract.getCreatedAt())
                .build();
    }
}
