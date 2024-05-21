package com.arch.mfc.infra.domain;

import jakarta.persistence.Entity;

public interface IEntity extends Entity {

    void setId(Long id);

    Long getId();

}
