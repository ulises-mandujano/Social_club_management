package com.lodge_treasury.management.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public class AdminMemberDto extends MemberDto{
    private Boolean deleted;
    private LocalDate deletedAt;
}
