package com.mfc.backend.microclientes.api.dto;

import com.mfc.infra.dto.ArqAbstractDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class CustomerOrderDTO extends ArqAbstractDTO {

    private Long id;

    private Long customerId;

    private String customerName;

    private BigDecimal total;

    public CustomerOrderDTO() { }


    @Override
    public Map<String, String> getMapaConversion() {
        return null;
    }
}
