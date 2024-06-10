package com.mfc.backend.microdiplomas.api;

import com.mfc.backend.microdiplomas.domain.model.Diploma;
import com.mfc.backend.microdiplomas.domain.service.DiplomaServiceAdapter;
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
    DiplomaServiceAdapter diplomaServiceAdapter;


    @GetMapping(value = "allDiplomas", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Diploma> getAllDiplomas() {
        return this.diplomaServiceAdapter.findAll();
    }


    @GetMapping(value = "allDiplomasByCustomerName", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Diploma> getAllDiplomasByCustomerName(@RequestParam String name) {
        return this.diplomaServiceAdapter.findAllByFieldvalue("name", name);
    }

    @GetMapping(value = "allDiplomasByCustomerID", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Diploma> getAllDiplomasByCustomerID(@RequestParam Long customerid) {
        return this.diplomaServiceAdapter.findAllByFieldvalue("customerid", customerid);
    }

    @PutMapping(produces=MediaType.APPLICATION_JSON_VALUE)
    public Diploma update(@RequestBody @NotNull Diploma diploma) {
        return diplomaServiceAdapter.update(diploma);
    }

    @DeleteMapping(value = "deleteAll", produces=MediaType.APPLICATION_JSON_VALUE)
    public void deleteAll() {
        this.diplomaServiceAdapter.deleteAll();
    }

}
