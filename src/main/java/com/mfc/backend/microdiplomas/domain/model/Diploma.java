package com.mfc.backend.microdiplomas.domain.model;

import com.mfc.backend.microdiplomas.api.dto.DiplomaDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Entity
@Data
public class Diploma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long idcustomer;

    @Column
    private String name;

    @Column
    private String titulo;

    @Column
    private String region;

    public Diploma() {}

    public Diploma(DiplomaDTO diplomaDTO) {
        this.id = diplomaDTO.getId();
        this.idcustomer = diplomaDTO.getIdcustomer();
        this.name = diplomaDTO.getName();
        this.titulo = diplomaDTO.getTitulo();
        this.region = diplomaDTO.getRegion();
    }


}
