package com.arch.mfc.infra.outputadapter.relational.repository;

import com.arch.mfc.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Customer, Long> {

}