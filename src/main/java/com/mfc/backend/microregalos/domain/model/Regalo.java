package com.mfc.backend.microregalos.domain.model;

import com.mfc.infra.utils.ConstantMessages;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
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
    @Min(value = 20,  message = "{" + ConstantMessages.MIN_VALUE_REQUIRED + "}")
    private BigDecimal valor_bono_regalo;


}
