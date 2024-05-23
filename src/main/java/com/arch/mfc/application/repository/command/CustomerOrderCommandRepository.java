package com.arch.mfc.application.repository.command;

import com.arch.mfc.application.domain.entities.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerOrderCommandRepository extends JpaRepository<CustomerOrder, Long> {

}
