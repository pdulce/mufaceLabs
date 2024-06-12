package com.mfc.backend.microdiplomas.api.dto;

import com.mfc.backend.microdiplomas.domain.model.Diploma;
import lombok.Data;

@Data
public class DiplomaDTO {

    private Long id;

    private Long idcustomer;

    private String name;

    private String titulo;

    private String region;

    public DiplomaDTO() {}

    public DiplomaDTO(Diploma diploma) {
        this.id = diploma.getId();
        this.idcustomer = diploma.getIdcustomer();
        this.name = diploma.getName();
        this.titulo = diploma.getTitulo();
        this.region = diploma.getRegion();
    }


}
