package com.mfc.backend.microclientesquerysmongo.api.dto;

import lombok.Data;

@Data
public class CustomerDocumentDTO {

    private String id;

    private String country;

    private String name;

    public CustomerDocumentDTO() {}
    public CustomerDocumentDTO(String id, String name, String country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

}
