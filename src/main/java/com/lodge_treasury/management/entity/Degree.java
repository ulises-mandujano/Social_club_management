package com.lodge_treasury.management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "degrees")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Degree extends BaseEntity{
    @Id
    @Column(name = "degree_code", length = 2, nullable = false)
    private String degreeCode;

    @Column(name = "degree_name", length = 50, nullable = false, unique = true)
    private String degreeName;

    @Column(name = "degree_level", nullable = false, unique = true)
    private Short degreeLevel;

    @Column(columnDefinition = "TEXT")
    private String description;
}
