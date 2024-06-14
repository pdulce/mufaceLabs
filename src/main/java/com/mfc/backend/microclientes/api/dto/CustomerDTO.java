package com.mfc.backend.microclientes.api.dto;

import com.mfc.infra.dto.ArqAbstractDTO;
import lombok.Data;

import java.util.Map;


@Data
public class CustomerDTO extends ArqAbstractDTO {

    private Long id;

    private String country;

    private String name;

    public CustomerDTO() {}


    @Override
    public Map<String, String> getMapaConversion() {
        return null;
    }
}
