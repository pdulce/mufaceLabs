package com.arch.mfc.application.repository;


import com.arch.mfc.application.domain.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerOrderCommandRepository extends JpaRepository<CustomerOrder, Long> {

}
