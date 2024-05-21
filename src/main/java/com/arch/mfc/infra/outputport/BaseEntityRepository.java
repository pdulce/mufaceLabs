package com.arch.mfc.infra.outputport;

import com.arch.mfc.infra.domain.IEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseEntityRepository extends JpaRepository<IEntity, Long> {

}
