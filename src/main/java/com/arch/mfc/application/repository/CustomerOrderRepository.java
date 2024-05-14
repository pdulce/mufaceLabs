package com.arch.mfc.application.repository;

import com.arch.mfc.application.domain.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {


}
