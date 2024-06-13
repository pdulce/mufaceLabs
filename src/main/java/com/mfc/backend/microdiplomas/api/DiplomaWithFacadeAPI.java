package com.mfc.backend.microdiplomas.api;

import com.mfc.backend.microdiplomas.api.dto.DiplomaDTO;
import com.mfc.backend.microdiplomas.api.facade.DiplomaFacade;
import com.mfc.infra.controller.BaseRestController;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "diploma")
public class DiplomaWithFacadeAPI extends BaseRestController {
    @Autowired
    DiplomaFacade diplomaFacade;

    @GetMapping(value = "allDiplomas", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<DiplomaDTO> getAllDiplomas() {
        return this.diplomaFacade.consultarTodosLosDiplomas();
    }


    @GetMapping(value = "allDiplomasByCustomerName", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<DiplomaDTO> getAllDiplomasByCustomerName(@RequestParam String name) {
        return this.diplomaFacade.consultaDiplomasDeClientesConNombre(name);
    }

    @GetMapping(value = "allDiplomasByCustomerID", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<DiplomaDTO> getAllDiplomasByCustomerID(@RequestParam Long customerid) {
        return this.diplomaFacade.consultaDiplomasDeCliente(customerid);
    }

    @PutMapping(produces=MediaType.APPLICATION_JSON_VALUE)
    public DiplomaDTO update(@RequestBody @NotNull DiplomaDTO diplomaDTO) {
        return this.diplomaFacade.actualizarDiploma(diplomaDTO);
    }

    @DeleteMapping(value = "deleteAll", produces=MediaType.APPLICATION_JSON_VALUE)
    public void deleteAll() {
        this.diplomaFacade.borrarTodosLosDiplomas();
    }

    /*** **/

    @GetMapping(value = "getDiplomasDeLaRegionProvenza", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<DiplomaDTO> getDiplomasDeLaRegionProvenza() {
        return this.diplomaFacade.consultaDiplomasDeRegionProvenza();
    }


}
