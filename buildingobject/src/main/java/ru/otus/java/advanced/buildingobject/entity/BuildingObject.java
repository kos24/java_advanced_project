package ru.otus.java.advanced.buildingobject.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@RequiredArgsConstructor

@Entity
@Table(name = "BUILDING_OBJECT")
@EntityListeners(AuditingEntityListener.class)
public class BuildingObject {

    @Id
    @Column(name = "ID")
    @UuidGenerator
    private UUID id;

    @Column(name="NAME")
    private String name;

    @Column(name="ADDRESS")
    private String address;

    @Column(name="OBJECT_TYPE_ID")
    private UUID objectTypeId;

    @Column(name="AREA")
    private BigDecimal area;

    @Column(name="PRICE")
    private BigDecimal price;

    @Column(name="CONTRACT_ID")
    private UUID contractId;

    @Column(name="CLIENT_ID")
    private UUID clientId;

    @Column(name="SALE_STATUS")
    private UUID saleStatus;

    @Column(name="DELETED")
    private Boolean deleted;

    @CreatedDate
    @Column(name="CREATED_AT")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name="UPDATED_AT")
    private LocalDateTime updatedAt;

}
