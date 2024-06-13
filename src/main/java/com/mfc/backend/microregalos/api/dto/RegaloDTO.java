package com.mfc.backend.microregalos.api.dto;

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

    public RegaloDTO(Long id, Long customerid, String colorCaja, String textotarjeta, BigDecimal valorBono) {
        this.id = id;
        this.customerid = customerid;
        this.color_caja = colorCaja;
        this.texto_tarjeta = textotarjeta;
        this.valor_bono_regalo = valorBono;
    }


}
