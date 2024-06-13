package com.mfc.backend.microdiplomas.api.dto;

import lombok.Data;

@Data
public class DiplomaDTO {

    private Long id;

    private Long idcustomer;

    private String name;

    private String titulo;

    private String region;

    public DiplomaDTO() {}

    public DiplomaDTO(Long id, Long idcustomer, String name, String titulo, String region) {
        this.id = id;
        this.idcustomer = idcustomer;
        this.name = name;
        this.titulo = titulo;
        this.region = region;
    }


}
