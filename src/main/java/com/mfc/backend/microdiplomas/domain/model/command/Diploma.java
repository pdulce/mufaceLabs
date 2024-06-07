package com.mfc.backend.microdiplomas.domain.model.command;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Diploma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long idcustomer;

    @Column
    private String name;

    @Column
    private String titulo;

    @Column
    private String region;


}
