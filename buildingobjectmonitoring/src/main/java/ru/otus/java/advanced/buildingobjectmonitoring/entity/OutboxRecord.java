package ru.otus.java.advanced.buildingobjectmonitoring.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@RequiredArgsConstructor

@Entity
@Table(name = "OUTBOX_RECORD")
@EntityListeners(AuditingEntityListener.class)
public class OutboxRecord {

    @Id
    @Column(name = "ID")
    @UuidGenerator
    private UUID id;

    @Column(name = "BUILDING_OBJECT_ID")
    private UUID buildingObjectId;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private Status status;

    @CreatedDate
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    public enum Status {
        PENDING,
        SENT,
        FAILED
    }
}
