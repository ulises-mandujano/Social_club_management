package com.lodge_treasury.management.entity;

import com.lodge_treasury.management.enums.DegreeType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "mason_degrees")
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class MasonDegree extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "degree_id", nullable = false)
    private Integer degreeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mason_id", nullable = false)
    private Mason mason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "degree_code", nullable = false)
    private Degree degree;

    @Column(name = "received_date", nullable = false)
    private LocalDate receivedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conferred_by")
    private Mason conferredBy;

    @Column(name = "notes", length = 250)
    private String notes;
}
