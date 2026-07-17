package com.lodge_treasury.management.repository;

import com.lodge_treasury.management.entity.IncomeConcepts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeConceptsRepository extends JpaRepository<IncomeConcepts,Integer> {


}
