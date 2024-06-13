package com.mfc.backend.microregalos.domain.service;

import com.mfc.backend.microregalos.api.dto.RegaloDTO;
import com.mfc.backend.microregalos.domain.model.Regalo;
import com.mfc.backend.microregalos.domain.repository.RegaloCommandRepository;
import com.mfc.infra.output.adapter.RelationalOperationsAdapter;
import com.mfc.infra.output.port.GenericRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegaloAdapterService extends RelationalOperationsAdapter<Regalo, Long>
        implements RegaloServicePort {

    @Autowired
    private RegaloCommandRepository repository;

    protected GenericRepositoryPort<Regalo, Long> getRepository() {
        return this.repository;
    }

    public RegaloDTO actualizarRegalo(RegaloDTO regaloDTO) {
        Regalo regalo = this.actualizar(new Regalo(regaloDTO));
        return new RegaloDTO(regalo.getId(), regalo.getCustomerid(), regalo.getColor_caja(), regalo.getTexto_tarjeta(),
                regalo.getValor_bono_regalo());
    }

    public List<RegaloDTO> consultarRegalosDeCustomer(Long customerId) {
        List<RegaloDTO> regalos = new ArrayList<>();
        this.buscarPorCampoValor("customerid", customerId).forEach((regalo -> {
            regalos.add(new RegaloDTO(regalo.getId(), regalo.getCustomerid(), regalo.getColor_caja(),
                    regalo.getTexto_tarjeta(), regalo.getValor_bono_regalo()));
        }));
        return regalos;
    }

    public List<RegaloDTO> consultarTodosLosRegalodEntregados() {
        List<RegaloDTO> regalos = new ArrayList<>();
        this.buscar().forEach((regalo -> {
            regalos.add(new RegaloDTO(regalo.getId(), regalo.getCustomerid(), regalo.getColor_caja(),
                    regalo.getTexto_tarjeta(), regalo.getValor_bono_regalo()));
        }));
        return regalos;
    }


}
