package com.mfc.backend.microregalos.api;

import com.mfc.backend.microregalos.api.dto.RegaloDTO;
import com.mfc.backend.microregalos.domain.model.Regalo;
import com.mfc.backend.microregalos.domain.service.RegaloCommandServicePort;
import com.mfc.infra.controller.BaseRestController;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "regalo")
public class RegaloAPI extends BaseRestController {
    @Autowired
    RegaloCommandServicePort regaloCommandAdapter;


    @GetMapping(value = "allRegalos", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<RegaloDTO> getAllRegalos() {
        List<RegaloDTO> regalos = new ArrayList<>();
        this.regaloCommandAdapter.findAll().forEach((regalo -> {
            regalos.add(new RegaloDTO(regalo));
        }));
        return regalos;
    }


    @GetMapping(value = "allRegalosByCustomerId", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<RegaloDTO> getAllRegalosByCustomerId(@RequestParam Long customerid) {
        List<RegaloDTO> regalos = new ArrayList<>();
        this.regaloCommandAdapter.findAllByFieldvalue("customerid", customerid).forEach((regalo -> {
            regalos.add(new RegaloDTO(regalo));
        }));
        return regalos;
    }

    @PutMapping(produces=MediaType.APPLICATION_JSON_VALUE)
    public RegaloDTO update(@RequestBody @NotNull RegaloDTO regaloDTO) {
        Regalo regalo = new Regalo(regaloDTO);
        return new RegaloDTO(this.regaloCommandAdapter.update(regalo));
    }

    @DeleteMapping(value = "deleteAll", produces=MediaType.APPLICATION_JSON_VALUE)
    public void deleteAll() {
        this.regaloCommandAdapter.deleteAll();
    }

}
