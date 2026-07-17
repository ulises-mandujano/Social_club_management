package com.lodge_treasury.management.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "mason_status_history")
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class MasonStatusHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id", unique = true, nullable = false)
    private Integer historyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mason_id", nullable = false)
    private Mason mason;

    @Column(name = "status", nullable = false)
    private Boolean status;

    @Column(name = "change_date", nullable = false)
    private LocalDate changeDate;

    @Column(name = "reason", length = 255)
    private String reason = null;

    @Column(name = "outstanding_debt", precision = 10, scale = 2)
    private BigDecimal outstandingDebt;
}
