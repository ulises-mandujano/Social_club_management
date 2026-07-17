package com.lodge_treasury.management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Immutable
@Table(name = "v_masons_with_status")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class MasonsWithStatus {

    @Id
    @Column(name = "mason_id", nullable = false)
    private Integer masonId;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "second_name", length = 50)
    private String secondName;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(name = "second_last_name", length = 50)
    private String secondLastName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "is_free_member")
    private Boolean isFreeMember;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "created_by", length = 20)
    private String createdBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by", length = 20)
    private String updatedBy;

    @Column(name = "current_status")
    private Boolean currentStatus;

    @Column(name = "last_status_change")
    private LocalDate lastStatusChange;

    @Column(name = "last_status_reason")
    private String lastStatusReason;

    @Column(name = "last_outstanding_debt", precision = 10, scale = 2)
    private BigDecimal lastOutstandingDebt;
}
