package com.lodge_treasury.management.repository;

import com.lodge_treasury.management.entity.Mason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MasonsRepository extends JpaRepository<Mason, Integer> {

    Optional<Mason> findByMasonIdAndDeletedFalse(Integer masonId);
    List<Mason> findAllByDeletedFalse();
}
