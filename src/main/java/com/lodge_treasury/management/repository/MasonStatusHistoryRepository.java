package com.lodge_treasury.management.repository;

import com.lodge_treasury.management.entity.MasonStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MasonStatusHistoryRepository extends JpaRepository<MasonStatusHistory, Integer> {


}
