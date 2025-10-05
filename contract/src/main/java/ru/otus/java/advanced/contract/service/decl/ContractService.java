package ru.otus.java.advanced.contract.service.decl;

import ru.otus.java.advanced.contract.dto.ContractDto;
import ru.otus.java.advanced.contract.dto.NewContractDto;
import ru.otus.java.advanced.contract.entity.Contract;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContractService {

    Contract add(NewContractDto newContractDto);

    Contract update(ContractDto contractDto);

    Optional<Contract> findById(UUID id);

    List<Contract> findAll();
}
