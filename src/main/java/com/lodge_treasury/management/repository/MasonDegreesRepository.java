package com.lodge_treasury.management.repository;

import com.lodge_treasury.management.entity.MasonDegree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MasonDegreesRepository extends JpaRepository<MasonDegree,Integer> {


}
