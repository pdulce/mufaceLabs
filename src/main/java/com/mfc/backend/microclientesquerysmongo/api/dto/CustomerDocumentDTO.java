package com.mfc.backend.microclientesquerysmongo.api.dto;

import com.mfc.infra.dto.ArqAbstractDTO;
import lombok.Data;

import java.util.Map;

@Data
public class CustomerDocumentDTO extends ArqAbstractDTO {

    private String id;

    private String country;

    private String name;

    public CustomerDocumentDTO() {}


    @Override
    public Map<String, String> getMapaConversion() {
        return null;
    }
}
