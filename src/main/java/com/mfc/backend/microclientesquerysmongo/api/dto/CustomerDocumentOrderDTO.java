package com.mfc.backend.microclientesquerysmongo.api.dto;

import com.mfc.infra.dto.ArqAbstractDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class CustomerDocumentOrderDTO extends ArqAbstractDTO {

    private String id;

    private Long customerId;

    private String customerName;

    private BigDecimal total;

    public CustomerDocumentOrderDTO() { }


    @Override
    public Map<String, String> getMapaConversion() {
        return null;
    }
}
