package com.mfc.backend.microregalos.api;

import com.mfc.backend.microregalos.domain.model.Regalo;
import com.mfc.backend.microregalos.domain.service.RegaloCommandStepSagaAdapter;
import com.mfc.infra.controller.BaseRestController;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "regalo")
public class RegaloAPI extends BaseRestController {
    @Autowired
    RegaloCommandStepSagaAdapter regaloCommandStepSagaAdapter;


    @GetMapping(value = "allRegalos", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Regalo> getAllRegalos() {
        return this.regaloCommandStepSagaAdapter.findAll();
    }


    @GetMapping(value = "allRegalosByCustomerId", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Regalo> getAllRegalosByCustomerId(@RequestParam Long customerid) {
        return this.regaloCommandStepSagaAdapter.findAllByFieldvalue("customerid", customerid);
    }

    @PutMapping(produces=MediaType.APPLICATION_JSON_VALUE)
    public Regalo update(@RequestBody @NotNull Regalo regalo) {
        try{
            return this.regaloCommandStepSagaAdapter.update(regalo);
        } catch (Throwable exc) {
            return null;
        }
    }

    @DeleteMapping(value = "deleteAll", produces=MediaType.APPLICATION_JSON_VALUE)
    public void deleteAll() {
        this.regaloCommandStepSagaAdapter.deleteAll();
    }

}
