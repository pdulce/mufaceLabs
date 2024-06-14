package com.mfc.infra.domain;

import java.util.Map;

public class AbstractDTO {

    public Map<String, String> mapaConversion;

    private AbstractDTO() {}
    public AbstractDTO (Map<String, String> mapaConversion) {
        this.mapaConversion = mapaConversion;
    }
}
