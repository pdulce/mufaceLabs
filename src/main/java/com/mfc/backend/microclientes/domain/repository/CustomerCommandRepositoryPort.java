package com.mfc.backend.microclientes.domain.repository;

import com.mfc.backend.microclientes.domain.model.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Esta clase la crearemos solo cuando necesite la capa de negocio de la aplicación nuevas operaciones
 * no disponibles desde la arquitectura, por ejemplo, consultas HQL persionalizas con JOINS etc,
 * consultas filtrando por uno o varios campos específicos de la entidad que maneja este Repository
 */

@Repository
public interface CustomerCommandRepositoryPort extends org.springframework.data.jpa.repository.JpaRepository<Customer, Long> {

    // HQL

    // metodos personalizados usando notación intrínseca de JPA
    public List<Customer> findAllByCountryContains(String regexCountry);

}
