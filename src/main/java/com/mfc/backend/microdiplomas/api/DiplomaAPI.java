package com.mfc.backend.microdiplomas.api;

import com.mfc.backend.microdiplomas.domain.model.command.Diploma;
import com.mfc.backend.microdiplomas.domain.service.command.DiplomaCommandStepSagaAdapter;
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
    DiplomaCommandStepSagaAdapter diplomaCommandStepSagaAdapter;


    @GetMapping(value = "getAllFromCommandDB", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Diploma> getAllFromCommandDB() {
        return this.diplomaCommandStepSagaAdapter.findAll();
    }


    @GetMapping(value = "getAllByDiplomasFromDB", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Diploma> getAllByDiplomasFromDB(@RequestParam String name) {
        return this.diplomaCommandStepSagaAdapter.findAllByFieldvalue("name", name);
    }

    @GetMapping(value = "getAllByDiplomasByIdcustomerFromDB", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Diploma> getAllByDiplomasFromDB(@RequestParam Long idcustomer) {
        return this.diplomaCommandStepSagaAdapter.findAllByFieldvalue("idcustomer", idcustomer);
    }

    @PutMapping(produces=MediaType.APPLICATION_JSON_VALUE)
    public Diploma update(@RequestBody @NotNull Diploma diploma) {
        try{
            return diplomaCommandStepSagaAdapter.update(diploma);
        } catch (Throwable exc) {
            return null;
        }
    }

    @DeleteMapping(value = "deleteAll", produces=MediaType.APPLICATION_JSON_VALUE)
    public void deleteAll() {
        this.diplomaCommandStepSagaAdapter.deleteAll();
    }

}
