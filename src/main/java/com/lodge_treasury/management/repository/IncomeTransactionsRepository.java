package com.lodge_treasury.management.repository;

import com.lodge_treasury.management.entity.IncomeTransactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncomeTransactionsRepository extends JpaRepository<IncomeTransactions,Integer> {

    List<IncomeTransactions> findByMason_MasonId(Integer masonId);
}
