package com.mfc.backend.microregalos.domain.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class Regalo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long customerid;

    @Column
    private String color_caja;

    @Column
    private String texto_tarjeta;

    @Column
    private BigDecimal valor_bono_regalo;


}
