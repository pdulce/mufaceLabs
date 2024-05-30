package com.arch.mfc.application.repository;

import com.arch.mfc.application.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Esta clase la crearemos solo cuando necesite la capa de negocio de la aplicación nuevas operaciones
 * no disponibles desde la arquitectura, por ejemplo, consultas HQL persionalizas con JOINS etc,
 * consultas filtrando por uno o varios campos específicos de la entidad que maneja este Repository
 */

@Repository
public interface CustomerCommandRepository extends JpaRepository<Customer, Long> {


}
