package com.mfc.infra.output.port;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GenericRepositoryPort<T, ID> extends JpaRepository<T, ID> {

}
