package com.project.upi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.upi.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}