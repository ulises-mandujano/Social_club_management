package com.lodge_treasury.management.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "lodge_offices")
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class LodgeOffices extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "office_id", unique = true, nullable = false)
    private Integer officeId;

    @Column(name = "office_name", nullable = false, unique = true, length = 50)
    private String officeName;

    @OneToMany(mappedBy = "office")
    private List<MasonOffices> masonOffices;
}
