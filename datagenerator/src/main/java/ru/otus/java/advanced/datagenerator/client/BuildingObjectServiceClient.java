package ru.otus.java.advanced.datagenerator.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.java.advanced.datagenerator.client.dto.BuildingObjectDto;
import ru.otus.java.advanced.datagenerator.client.dto.NewBuildingObjectDto;

@FeignClient("building-object-service")
public interface BuildingObjectServiceClient {

    @PostMapping("/api/building-object")
    ResponseEntity<BuildingObjectDto> addBuildingObject(NewBuildingObjectDto newBuildingObjectDto);
}
