package com.lodge_treasury.management.repository;

import com.lodge_treasury.management.entity.Degree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DegreeRepository  extends JpaRepository<Degree, String> {

    Optional<Degree> findByDegreeCode(String degreeCode);
}
