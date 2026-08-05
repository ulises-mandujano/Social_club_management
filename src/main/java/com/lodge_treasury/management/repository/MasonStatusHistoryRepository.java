package com.lodge_treasury.management.repository;

import com.lodge_treasury.management.entity.MasonStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MasonStatusHistoryRepository extends JpaRepository<MasonStatusHistory, Integer> {

    @Query("SELECT h FROM MasonStatusHistory h WHERE h.mason.masonId = :masonId AND h.status = false ORDER BY " +
            "h.changeDate DESC, h.historyId DESC")
    Optional<MasonStatusHistory> findLatestInactiveByMasonId(@Param("masonId") Integer masonId);
}
