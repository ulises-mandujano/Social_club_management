package com.lodge_treasury.management.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "charity_bag_circulations")
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class CharityBagCirculations extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "circulation_id", unique = true, nullable = false)
    private Integer circulationId;

    @Column(name = "circulation_date", nullable = false)
    private LocalDate circulationDate;

    @Column(name = "amount_collected", nullable = false, precision = 10, scale = 2)
    private BigDecimal amountCollected;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "handled_by", nullable = false)
    private Mason handledBy;

    @Column(name = "notes")
    private String notes;
}
