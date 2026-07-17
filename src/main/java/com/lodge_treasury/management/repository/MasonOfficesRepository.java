package com.lodge_treasury.management.repository;

import com.lodge_treasury.management.entity.MasonOffices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MasonOfficesRepository extends JpaRepository<MasonOffices, Integer> {

    @Query("SELECT mo FROM MasonOffices mo "+
            "WHERE mo.office.officeName = :officeName " +
            "AND (mo.endDate IS NULL OR mo.endDate >= CURRENT_DATE)")
    Optional<MasonOffices> findCurrentByOfficeName(@Param("officeName") String officeName);
}
