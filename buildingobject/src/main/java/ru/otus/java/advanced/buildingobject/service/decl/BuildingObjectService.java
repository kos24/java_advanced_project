package ru.otus.java.advanced.buildingobject.service.decl;

import ru.otus.java.advanced.buildingobject.dto.BuildingObjectMonitoringDto;
import ru.otus.java.advanced.buildingobject.dto.NewBuildingObjectDto;
import ru.otus.java.advanced.buildingobject.dto.BuildingObjectDto;
import ru.otus.java.advanced.buildingobject.entity.BuildingObject;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BuildingObjectService {
    BuildingObject add(NewBuildingObjectDto newBuildingObjectDto);

    Optional<BuildingObject> findById(UUID id);

    List<BuildingObject> findAll();

    BuildingObject update(BuildingObjectDto buildingObjectDto);

    BuildingObject updateSaleStatus(BuildingObjectMonitoringDto buildingObjectMonitoringDto);
}
