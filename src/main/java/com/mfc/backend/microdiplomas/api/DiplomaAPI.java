package com.mfc.backend.microdiplomas.api;

import com.mfc.backend.microdiplomas.api.dto.DiplomaDTO;
import com.mfc.backend.microdiplomas.domain.model.Diploma;
import com.mfc.backend.microdiplomas.domain.service.DiplomaServicePort;
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
    DiplomaServicePort diplomaServiceAdapter;

    @GetMapping(value = "allDiplomas", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<DiplomaDTO> getAllDiplomas() {
        List<DiplomaDTO> diplomas = new ArrayList<>();
        this.diplomaServiceAdapter.findAll().forEach((diploma -> {
            diplomas.add(new DiplomaDTO(diploma));
        }));
        return diplomas;
    }


    @GetMapping(value = "allDiplomasByCustomerName", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<DiplomaDTO> getAllDiplomasByCustomerName(@RequestParam String name) {
        List<DiplomaDTO> diplomas = new ArrayList<>();
        this.diplomaServiceAdapter.findAllByFieldvalue("name", name).forEach((diploma -> {
            diplomas.add(new DiplomaDTO(diploma));
        }));
        return diplomas;
    }

    @GetMapping(value = "allDiplomasByCustomerID", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<DiplomaDTO> getAllDiplomasByCustomerID(@RequestParam Long customerid) {
        List<DiplomaDTO> diplomas = new ArrayList<>();
        this.diplomaServiceAdapter.findAllByFieldvalue("customerid", customerid).forEach((diploma -> {
            diplomas.add(new DiplomaDTO(diploma));
        }));
        return diplomas;
    }

    @PutMapping(produces=MediaType.APPLICATION_JSON_VALUE)
    public DiplomaDTO update(@RequestBody @NotNull DiplomaDTO diplomaDTO) {
        Diploma diploma = new Diploma(diplomaDTO);
        return new DiplomaDTO(diplomaServiceAdapter.update(diploma));
    }

    @DeleteMapping(value = "deleteAll", produces=MediaType.APPLICATION_JSON_VALUE)
    public void deleteAll() {
        this.diplomaServiceAdapter.deleteAll();
    }

    /*** **/

    @GetMapping(value = "getDiplomasDeLaRegionProvenza", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<DiplomaDTO> getDiplomasDeLaRegionProvenza() {
        List<DiplomaDTO> diplomas = new ArrayList<>();
        this.diplomaServiceAdapter.getDiplomasDeLaRegionProvenza().forEach((diploma -> {
            diplomas.add(new DiplomaDTO(diploma));
        }));
        return diplomas;
    }


}
