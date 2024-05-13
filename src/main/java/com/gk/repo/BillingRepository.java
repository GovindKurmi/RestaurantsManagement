package com.gk.repo;

import com.gk.model.BillingAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillingRepository extends JpaRepository<BillingAddress, Long> {
}
