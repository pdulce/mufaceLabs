package com.mfc.backend.microregalos.domain.service;

import com.mfc.backend.microregalos.api.dto.RegaloDTO;
import com.mfc.backend.microregalos.domain.model.Regalo;
import com.mfc.backend.microregalos.domain.repository.RegaloCommandRepository;
import com.mfc.infra.output.adapter.CommandServiceAdapter;
import com.mfc.infra.output.port.GenericRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegaloAdapterService extends CommandServiceAdapter<Regalo, Long>
        implements RegaloServicePort {

    @Autowired
    private RegaloCommandRepository repository;

    protected GenericRepositoryPort<Regalo, Long> getRepository() {
        return this.repository;
    }

    public RegaloDTO actualizarRegalo(RegaloDTO regaloDTO) {
        Regalo regalo = new Regalo(regaloDTO);
        return new RegaloDTO(this.actualizar(regalo));
    }

    public List<RegaloDTO> consultarRegalosDeCustomer(Long customerId) {
        List<RegaloDTO> regalos = new ArrayList<>();
        this.buscarPorCampoValor("customerid", customerId).forEach((regalo -> {
            regalos.add(new RegaloDTO(regalo));
        }));
        return regalos;
    }

    public List<RegaloDTO> consultarTodosLosRegalodEntregados() {
        List<RegaloDTO> regalos = new ArrayList<>();
        this.buscar().forEach((regalo -> {
            regalos.add(new RegaloDTO(regalo));
        }));
        return regalos;
    }


}
