package com.arch.mfc.backendA.domain.repository;


import com.arch.mfc.backendA.domain.model.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerOrderCommandRepository extends JpaRepository<CustomerOrder, Long> {

}
