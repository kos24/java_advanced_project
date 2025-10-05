package ru.otus.java.advanced.buildingobject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.otus.java.advanced.buildingobject.entity.BuildingObject;

import java.util.List;
import java.util.UUID;

public interface BuildingObjectRepository extends JpaRepository<BuildingObject, UUID> {

    @Query(nativeQuery = true,
            value = "SELECT DISTINCT bo.* FROM building_object bo " +
                    "LEFT JOIN outbox_record o ON bo.id = o.building_object_id " +
                    "WHERE o.id IS NULL OR o.status = 'FAILED'")
    List<BuildingObject> findAllPending();
}
