package com.mfc.backend.microregalos.api.dto;

import com.mfc.infra.dto.ArqAbstractDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class RegaloDTO extends ArqAbstractDTO {

    private Long id;

    private Long customerid;

    private String color_caja;

    private String texto_tarjeta;

    private BigDecimal valor_bono_regalo;

    public RegaloDTO() {}


    @Override
    public Map<String, String> getMapaConversion() {
        return null;
    }
}
