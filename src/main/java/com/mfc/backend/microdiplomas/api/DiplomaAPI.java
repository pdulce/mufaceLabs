package com.mfc.backend.microdiplomas.api;

import com.mfc.backend.microdiplomas.api.dto.DiplomaDTO;
import com.mfc.backend.microdiplomas.api.usecases.ActualizarDiplomaUseCase;
import com.mfc.backend.microdiplomas.api.usecases.BorrarTodosLosDiplomasUseCase;
import com.mfc.backend.microdiplomas.api.usecases.ConsultasDiplomasUseCase;
import com.mfc.backend.microdiplomas.domain.model.Diploma;
import com.mfc.infra.controller.BaseRestController;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "diploma")
public class DiplomaAPI extends BaseRestController {
    @Autowired
    ConsultasDiplomasUseCase consultasDiplomasUseCase;
    @Autowired
    BorrarTodosLosDiplomasUseCase borrarTodosLosDiplomasUseCase;
    @Autowired
    ActualizarDiplomaUseCase actualizarDiplomaUseCase;

    @GetMapping(value = "allDiplomas", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<DiplomaDTO> getAllDiplomas() {
        List<DiplomaDTO> diplomas = new ArrayList<>();
        this.consultasDiplomasUseCase.ejecutar().forEach((diploma -> {
            diplomas.add(new DiplomaDTO(diploma));
        }));
        return diplomas;
    }


    @GetMapping(value = "allDiplomasByCustomerName", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<DiplomaDTO> getAllDiplomasByCustomerName(@RequestParam String name) {
        List<DiplomaDTO> diplomas = new ArrayList<>();
        this.consultasDiplomasUseCase.ejecutar(name).forEach((diploma -> {
            diplomas.add(new DiplomaDTO(diploma));
        }));
        return diplomas;
    }

    @GetMapping(value = "allDiplomasByCustomerID", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<DiplomaDTO> getAllDiplomasByCustomerID(@RequestParam Long customerid) {
        List<DiplomaDTO> diplomas = new ArrayList<>();
        this.consultasDiplomasUseCase.ejecutar(customerid).forEach((diploma -> {
            diplomas.add(new DiplomaDTO(diploma));
        }));
        return diplomas;
    }

    @PutMapping(produces=MediaType.APPLICATION_JSON_VALUE)
    public DiplomaDTO update(@RequestBody @NotNull DiplomaDTO diplomaDTO) {
        Diploma diploma = new Diploma(diplomaDTO);
        return new DiplomaDTO(this.actualizarDiplomaUseCase.ejecutar(diploma));
    }

    @DeleteMapping(value = "deleteAll", produces=MediaType.APPLICATION_JSON_VALUE)
    public void deleteAll() {
        this.borrarTodosLosDiplomasUseCase.ejecutar();
    }

    /*** **/

    @GetMapping(value = "getDiplomasDeLaRegionProvenza", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<DiplomaDTO> getDiplomasDeLaRegionProvenza() {
        List<DiplomaDTO> diplomas = new ArrayList<>();
        this.consultasDiplomasUseCase.getDiplomasDeLaRegionProvenza().forEach((diploma -> {
            diplomas.add(new DiplomaDTO(diploma));
        }));
        return diplomas;
    }


}
