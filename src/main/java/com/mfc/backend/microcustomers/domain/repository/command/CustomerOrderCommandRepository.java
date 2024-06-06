package com.mfc.backend.microcustomers.domain.repository.command;


import com.mfc.backend.microcustomers.domain.model.command.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerOrderCommandRepository extends JpaRepository<CustomerOrder, Long> {

}
