package com.mfc.infra.output.port;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface GenericRepositoryPort<T, ID> extends JpaRepository<T, ID> {

}
