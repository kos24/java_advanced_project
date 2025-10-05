package ru.otus.java.advanced.buildingobject.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.java.advanced.buildingobject.client.DictionaryClient;
import ru.otus.java.advanced.buildingobject.dto.BuildingObjectDto;
import ru.otus.java.advanced.buildingobject.dto.BuildingObjectMonitoringDto;
import ru.otus.java.advanced.buildingobject.dto.NewBuildingObjectDto;
import ru.otus.java.advanced.buildingobject.entity.BuildingObject;

@Component
@RequiredArgsConstructor
public class BuildingObjectMapper {

    private final DictionaryClient dictionaryClient;

    public BuildingObject fromDto(NewBuildingObjectDto newBuildingObjectDto) {
        return BuildingObject
                .builder()
                .name(newBuildingObjectDto.getName())
                .address(newBuildingObjectDto.getAddress())
                .objectTypeId(newBuildingObjectDto.getObjectTypeId())
                .area(newBuildingObjectDto.getArea())
                .price(newBuildingObjectDto.getPrice())
                .contractId(newBuildingObjectDto.getContractId())
                .clientId(newBuildingObjectDto.getClientId())
                .saleStatus(newBuildingObjectDto.getSaleStatus())
                .deleted(false)
                .build();
    }

    public BuildingObjectDto toDto(BuildingObject buildingObject) {
        return BuildingObjectDto
                .builder()
                .id(buildingObject.getId())
                .name(buildingObject.getName())
                .address(buildingObject.getAddress())
                .objectTypeId(buildingObject.getObjectTypeId())
                .objectType(dictionaryClient.getDictionaryById(buildingObject.getObjectTypeId()).getName())
                .area(buildingObject.getArea())
                .price(buildingObject.getPrice())
                .contractId(buildingObject.getContractId())
                .clientId(buildingObject.getClientId())
                .saleStatus(buildingObject.getSaleStatus())
                .saleStatusName(dictionaryClient.getDictionaryById(buildingObject.getSaleStatus()).getName())
                .deleted(buildingObject.getDeleted())
                .createdAt(buildingObject.getCreatedAt())
                .updatedAt(buildingObject.getUpdatedAt())
                .build();
    }

    public BuildingObjectMonitoringDto toBuildingObjectMonitoringDto(BuildingObject buildingObject) {
        return BuildingObjectMonitoringDto
                .builder()
                .id(buildingObject.getId())
                .contractId(buildingObject.getContractId())
                .clientId(buildingObject.getClientId())
                .saleStatus(buildingObject.getSaleStatus())
                .buildObjectId(buildingObject.getId())
                .build();
    }
}
