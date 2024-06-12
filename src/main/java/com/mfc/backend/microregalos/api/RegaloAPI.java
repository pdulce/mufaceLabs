package com.mfc.backend.microregalos.api;

import com.mfc.backend.microregalos.api.dto.RegaloDTO;
import com.mfc.backend.microregalos.api.usecases.ActualizarRegaloUseCase;
import com.mfc.backend.microregalos.api.usecases.BorrarTodosLosRegalosUseCase;
import com.mfc.backend.microregalos.api.usecases.ConsultasRegalosUseCase;
import com.mfc.backend.microregalos.domain.model.Regalo;
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
    ActualizarRegaloUseCase actualizarRegaloUseCase;
    @Autowired
    BorrarTodosLosRegalosUseCase borrarTodosLosRegalosUseCase;
    @Autowired
    ConsultasRegalosUseCase consultasRegalosUseCase;


    @GetMapping(value = "allRegalos", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<RegaloDTO> getAllRegalos() {
        return this.consultasRegalosUseCase.ejecutar();
    }


    @GetMapping(value = "allRegalosByCustomerId", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<RegaloDTO> getAllRegalosByCustomerId(@RequestParam Long customerid) {
        return this.consultasRegalosUseCase.ejecutar(customerid);
    }

    @PutMapping(produces=MediaType.APPLICATION_JSON_VALUE)
    public RegaloDTO update(@RequestBody @NotNull RegaloDTO regaloDTO) {
        return this.actualizarRegaloUseCase.ejecutar(regaloDTO);
    }

    @DeleteMapping(value = "deleteAll", produces=MediaType.APPLICATION_JSON_VALUE)
    public void deleteAll() {
        this.borrarTodosLosRegalosUseCase.ejecutar();
    }

}
