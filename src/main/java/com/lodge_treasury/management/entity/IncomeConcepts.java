package com.lodge_treasury.management.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "income_concepts")
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class IncomeConcepts extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "income_id", unique = true, nullable = false)
    private Integer incomeId;

    @Column(name = "description", nullable = false, length = 100)
    private String description;

    @OneToMany(mappedBy = "incomeConcept")
    private List<IncomeTransactions> incomeTransactions;
}
