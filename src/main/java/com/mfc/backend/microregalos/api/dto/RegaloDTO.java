package com.mfc.backend.microregalos.api.dto;

import com.mfc.backend.microregalos.domain.model.Regalo;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RegaloDTO {

    private Long id;

    private Long customerid;

    private String color_caja;

    private String texto_tarjeta;

    private BigDecimal valor_bono_regalo;

    public RegaloDTO() {}

    public RegaloDTO(Regalo regalo) {
        this.id = regalo.getId();
        this.customerid = regalo.getCustomerid();
        this.color_caja = regalo.getColor_caja();
        this.texto_tarjeta = regalo.getTexto_tarjeta();
        this.valor_bono_regalo = regalo.getValor_bono_regalo();
    }


}
