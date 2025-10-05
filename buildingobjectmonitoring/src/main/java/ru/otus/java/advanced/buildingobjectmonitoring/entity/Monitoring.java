package ru.otus.java.advanced.buildingobjectmonitoring.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@RequiredArgsConstructor

@Entity
@Table(name = "MONITORING")
@EntityListeners(AuditingEntityListener.class)
public class Monitoring {

    @Id
    @Column(name = "ID")
    @UuidGenerator
    private UUID id;

    @Column(name="BUILDING_OBJECT_ID")
    private UUID buildingObjectId;

    @Column(name="CONTRACT_ID")
    private UUID contractId;

    @Column(name="CLIENT_ID")
    private UUID clientId;

    @Column(name = "SALE_STATUS")
    private UUID saleStatus;

    @Column(name = "CLIENT_RISK_SCORE")
    private Integer clientRiskScore;

    @Column(name = "PLAN_MONITORING_DATE")
    private LocalDate planMonitoringDate;

    @Column(name = "MONITORING_STATUS")
    private UUID monitoringStatus;

    @CreatedDate
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

}