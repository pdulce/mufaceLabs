package com.mfc.backend.microregalos.domain.repository;

import com.mfc.backend.microregalos.domain.model.Regalo;
import com.mfc.infra.output.port.GenericRepositoryPort;
import org.springframework.stereotype.Repository;

/**
 * Esta clase la crearemos solo cuando necesite la capa de negocio de la aplicación nuevas operaciones
 * no disponibles desde la arquitectura, por ejemplo, consultas HQL persionalizas con JOINS etc,
 * consultas filtrando por uno o varios campos específicos de la entidad que maneja este Repository
 */

@Repository
public interface RegaloCommandRepository extends GenericRepositoryPort<Regalo, Long> {


}
