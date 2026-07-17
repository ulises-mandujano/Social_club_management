package com.lodge_treasury.management.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MemberDto {
    private Integer id;
    private String name;
    private String secondName;
    private String lastName;
    private String secondLastName;
    private LocalDate birthDate;
    private String currentDegree;
}
