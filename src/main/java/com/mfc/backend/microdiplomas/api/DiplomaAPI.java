package com.mfc.backend.microdiplomas.api;

import com.mfc.backend.microdiplomas.api.dto.DiplomaDTO;
import com.mfc.backend.microdiplomas.api.usecases.ActualizarDiplomaUseCase;
import com.mfc.backend.microdiplomas.api.usecases.BorrarTodosLosDiplomasUseCase;
import com.mfc.backend.microdiplomas.api.usecases.ConsultasDiplomasUseCase;
import com.mfc.infra.controller.BaseRestController;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
        return this.consultasDiplomasUseCase.ejecutar();
    }


    @GetMapping(value = "allDiplomasByCustomerName", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<DiplomaDTO> getAllDiplomasByCustomerName(@RequestParam String name) {
        return this.consultasDiplomasUseCase.ejecutar(name);
    }

    @GetMapping(value = "allDiplomasByCustomerID", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<DiplomaDTO> getAllDiplomasByCustomerID(@RequestParam Long customerid) {
        return this.consultasDiplomasUseCase.ejecutar(customerid);
    }

    @PutMapping(produces=MediaType.APPLICATION_JSON_VALUE)
    public DiplomaDTO update(@RequestBody @NotNull DiplomaDTO diplomaDTO) {
        return this.actualizarDiplomaUseCase.ejecutar(diplomaDTO);
    }

    @DeleteMapping(value = "deleteAll", produces=MediaType.APPLICATION_JSON_VALUE)
    public void deleteAll() {
        this.borrarTodosLosDiplomasUseCase.ejecutar();
    }

    /*** **/

    @GetMapping(value = "getDiplomasDeLaRegionProvenza", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<DiplomaDTO> getDiplomasDeLaRegionProvenza() {
        return this.consultasDiplomasUseCase.getDiplomasDeLaRegionProvenza();
    }


}
