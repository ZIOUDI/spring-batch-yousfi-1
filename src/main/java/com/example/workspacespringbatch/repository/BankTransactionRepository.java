package com.example.workspacespringbatch.repository;

import com.example.workspacespringbatch.model.BankTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankTransactionRepository extends JpaRepository<BankTransaction,Long> {
}
