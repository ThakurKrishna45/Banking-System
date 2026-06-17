package com.krishna.banking.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IdempotencyRecord {

    @Id
    private String idempotencyKey;

    private String referenceId;

    @Enumerated(EnumType.STRING)
    private IdempotencyStatus status;

    private LocalDateTime createdAt;
}