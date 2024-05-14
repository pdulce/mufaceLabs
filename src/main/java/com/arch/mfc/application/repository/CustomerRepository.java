package com.arch.mfc.application.repository;

import com.arch.mfc.application.domain.Customer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    public List<Customer> findByName(String name);

}
