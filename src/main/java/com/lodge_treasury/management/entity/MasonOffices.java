package com.lodge_treasury.management.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "mason_offices")
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class MasonOffices extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mason_office_id", unique = true, nullable = false)
    private Integer masonOfficeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mason_id", nullable = false)
    private Mason mason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "office_id", nullable = false)
    private LodgeOffices office;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate = null;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointed_by", nullable = false)
    private Mason appointor;
}
