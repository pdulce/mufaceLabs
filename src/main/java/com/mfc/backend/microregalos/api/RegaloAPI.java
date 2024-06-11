package com.mfc.backend.microregalos.api;

import com.mfc.backend.microregalos.domain.model.Regalo;
import com.mfc.backend.microregalos.domain.service.RegaloCommandServicePort;
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
    RegaloCommandServicePort regaloCommandAdapter;


    @GetMapping(value = "allRegalos", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Regalo> getAllRegalos() {
        return this.regaloCommandAdapter.findAll();
    }


    @GetMapping(value = "allRegalosByCustomerId", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Regalo> getAllRegalosByCustomerId(@RequestParam Long customerid) {
        return this.regaloCommandAdapter.findAllByFieldvalue("customerid", customerid);
    }

    @PutMapping(produces=MediaType.APPLICATION_JSON_VALUE)
    public Regalo update(@RequestBody @NotNull Regalo regalo) {
        return this.regaloCommandAdapter.update(regalo);
    }

    @DeleteMapping(value = "deleteAll", produces=MediaType.APPLICATION_JSON_VALUE)
    public void deleteAll() {
        this.regaloCommandAdapter.deleteAll();
    }

}
