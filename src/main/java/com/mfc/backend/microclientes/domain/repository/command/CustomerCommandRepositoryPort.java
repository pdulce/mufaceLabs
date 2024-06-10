package com.mfc.backend.microclientes.domain.repository.command;

import com.mfc.backend.microclientes.domain.model.command.Customer;
import com.mfc.infra.output.port.GenericRepositoryPort;
import org.springframework.stereotype.Repository;

/**
 * Esta clase la crearemos solo cuando necesite la capa de negocio de la aplicación nuevas operaciones
 * no disponibles desde la arquitectura, por ejemplo, consultas HQL persionalizas con JOINS etc,
 * consultas filtrando por uno o varios campos específicos de la entidad que maneja este Repository
 */

@Repository
public interface CustomerCommandRepositoryPort extends GenericRepositoryPort<Customer, Long> {


}
