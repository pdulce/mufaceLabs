package com.arch.mfc.application.repository;

import com.arch.mfc.application.domain.command.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerOrderCommandRepository extends JpaRepository<CustomerOrder, Long> {

}
