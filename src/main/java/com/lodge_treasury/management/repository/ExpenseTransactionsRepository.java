package com.lodge_treasury.management.repository;

import com.lodge_treasury.management.entity.ExpenseTransactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseTransactionsRepository extends JpaRepository<ExpenseTransactions,Integer> {

}
