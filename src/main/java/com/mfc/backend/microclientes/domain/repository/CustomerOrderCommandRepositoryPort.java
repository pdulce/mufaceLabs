package com.mfc.backend.microclientes.domain.repository;


import com.mfc.backend.microclientes.domain.model.CustomerOrder;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerOrderCommandRepositoryPort extends org.springframework.data.jpa.repository.JpaRepository<CustomerOrder, Long> {

}
