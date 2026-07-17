package com.lodge_treasury.management.repository;

import com.lodge_treasury.management.entity.Mason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MasonsRepository extends JpaRepository<Mason, Integer> {


}
