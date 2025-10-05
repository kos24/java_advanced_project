package ru.otus.java.advanced.buildingobject.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.java.advanced.buildingobject.dto.BuildingObjectDto;
import ru.otus.java.advanced.buildingobject.dto.BuildingObjectMonitoringDto;
import ru.otus.java.advanced.buildingobject.dto.NewBuildingObjectDto;
import ru.otus.java.advanced.buildingobject.entity.BuildingObject;
import ru.otus.java.advanced.buildingobject.mapper.BuildingObjectMapper;
import ru.otus.java.advanced.buildingobject.repository.BuildingObjectRepository;
import ru.otus.java.advanced.buildingobject.service.decl.BuildingObjectService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BuildingObjectServiceImpl implements BuildingObjectService {

    private final BuildingObjectRepository buildingObjectRepository;
    private final BuildingObjectMapper buildingObjectMapper;

    @Override
    public BuildingObject add(NewBuildingObjectDto newBuildingObjectDto) {
        BuildingObject buildingObject = buildingObjectMapper.fromDto(newBuildingObjectDto);
        return buildingObjectRepository.save(buildingObject);
    }

    @Override
    public Optional<BuildingObject> findById(UUID id) {
        return buildingObjectRepository.findById(id);
    }

    @Override
    public List<BuildingObject> findAll() {
        return buildingObjectRepository.findAll();
    }

    @Override
    public BuildingObject update(BuildingObjectDto buildingObjectDto) {
        return buildingObjectRepository.findById(buildingObjectDto.getId()).map(bo -> {
                    bo.setName(buildingObjectDto.getName());
                    bo.setAddress(buildingObjectDto.getAddress());
                    bo.setObjectTypeId(buildingObjectDto.getObjectTypeId());
                    bo.setPrice(buildingObjectDto.getPrice());
                    bo.setSaleStatus(buildingObjectDto.getSaleStatus());
                    bo.setArea(buildingObjectDto.getArea());
                    bo.setContractId(buildingObjectDto.getContractId());
                    return bo;
                }).map(buildingObjectRepository::save)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public BuildingObject updateSaleStatus(BuildingObjectMonitoringDto buildingObjectMonitoringDto) {
        return buildingObjectRepository.findById(buildingObjectMonitoringDto.getBuildObjectId()).map(bo -> {
            bo.setSaleStatus(buildingObjectMonitoringDto.getSaleStatus());
            return buildingObjectRepository.save(bo);
        }).orElseThrow(() -> new RuntimeException(String.format("Failed to update sale status for building object %s", buildingObjectMonitoringDto.getId().toString())));
    }
}
