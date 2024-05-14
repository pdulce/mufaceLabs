package com.arch.mfc.infra.outputport;

import com.arch.mfc.infra.domain.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseEntityRepository extends JpaRepository<BaseEntity, Long> {

}
