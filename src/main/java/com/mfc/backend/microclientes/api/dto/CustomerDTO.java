package com.mfc.backend.microclientes.api.dto;

import lombok.Data;


@Data
public class CustomerDTO {

    private Long id;

    private String country;

    private String name;

    public CustomerDTO() {}
    public CustomerDTO(Long id, String name, String country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

}
