package com.lodge_treasury.management.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Builder
@Table(name = "masons")
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class Mason extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Builder.Default
    @Column(name = "mason_id", unique = true, nullable = false)
    private Integer masonId = null;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "second_name", length = 50)
    private String secondName;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(name = "second_last_name", length = 50)
    private String secondLastName;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "is_free_member")
    private Boolean isFreeMember = false;

    @OneToMany(mappedBy = "mason")
    private List<MasonOffices> masonOffices;

    @OneToMany(mappedBy = "approvedBy")
    private List<ExpenseTransactions> approvedExpenses;

    @OneToMany(mappedBy = "mason", fetch = FetchType.LAZY)
    private List<MasonDegree> masonDegrees;

    @OneToOne(mappedBy = "mason", fetch = FetchType.LAZY)
    private MasonContact contact;

    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;
}
