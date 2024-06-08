package com.mfc.backend.microclientes.domain.repository.command;


import com.mfc.backend.microclientes.domain.model.command.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerOrderCommandRepository extends JpaRepository<CustomerOrder, Long> {

}
