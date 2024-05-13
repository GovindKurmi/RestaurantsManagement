package com.gk.repo;

import com.gk.model.TransactionDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionDetails, Long> {
}
