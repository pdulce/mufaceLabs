package com.mfc.backend.microdiplomas.domain.model.command;

import lombok.Data;

@Data
public class CustomerWrapper {
    private Long id;

    private String country;

    private String name;

}