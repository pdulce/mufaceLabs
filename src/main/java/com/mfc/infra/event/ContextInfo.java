package com.mfc.infra.event;

import lombok.Data;

import java.io.Serializable;

@Data
public class ContextInfo implements Serializable {
    private String almacen;
    private String author;
    private String applicationId;

    public ContextInfo(){}

    public ContextInfo(String almacen, String author, String applicationId) {
        this.almacen = almacen;
        this.author = author;
        this.applicationId = applicationId;
    }
}
