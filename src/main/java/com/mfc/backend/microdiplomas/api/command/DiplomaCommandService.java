package com.mfc.backend.microdiplomas.api.command;

import com.mfc.backend.microdiplomas.api.dto.DiplomaDTO;
import com.mfc.backend.microdiplomas.api.usecases.ActualizarDiplomaUseCase;
import com.mfc.backend.microdiplomas.api.usecases.BorrarTodosLosDiplomasUseCase;
import com.mfc.backend.microdiplomas.api.usecases.ConsultasDiplomasUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiplomaCommandService {

    @Autowired
    private CommandHandler commandHandler;

    @Autowired
    ActualizarDiplomaUseCase actualizarDiplomaUseCase;
    @Autowired
    BorrarTodosLosDiplomasUseCase borrarTodosLosDiplomasUseCase;
    @Autowired
    ConsultasDiplomasUseCase consultasDiplomasUseCase;

    public List<DiplomaDTO> getDiplomasDeLaRegionProvenza() {
        return null;
    }

    public DiplomaDTO actualizarDiploma(DiplomaDTO diplomaDTO) {
        ActualizarDiplomaCommand command = new ActualizarDiplomaCommand(actualizarDiplomaUseCase, diplomaDTO);
        commandHandler.registerCommand(ActualizarDiplomaCommand.class, command);
        return (DiplomaDTO) commandHandler.executeCommand(ActualizarDiplomaCommand.class);
    }

    public void borrarDiplomas() {
        BorrarDiplomaCommand command = new BorrarDiplomaCommand(borrarTodosLosDiplomasUseCase, new DiplomaDTO());
        commandHandler.registerCommand(BorrarDiplomaCommand.class, command);
        commandHandler.executeCommand(BorrarDiplomaCommand.class);
    }

    public List<DiplomaDTO> buscarDiplomasDeCustomer(Long id) {
        DiplomaDTO search = new DiplomaDTO();
        search.setId(id);
        BuscarDiplomasDeCliente command = new BuscarDiplomasDeCliente(consultasDiplomasUseCase, search);
        commandHandler.registerCommand(BuscarDiplomasDeCliente.class, command);
        return (List<DiplomaDTO>) commandHandler.executeCommand(BuscarDiplomasDeCliente.class);
    }

    public List<DiplomaDTO> buscarDiplomasPorNombreCustomer(String name) {
        DiplomaDTO search = new DiplomaDTO();
        search.setName(name);
        BuscarTodosLosDiplomasCommand command = new BuscarTodosLosDiplomasCommand(consultasDiplomasUseCase, search);
        commandHandler.registerCommand(BuscarTodosLosDiplomasCommand.class, command);
        return (List<DiplomaDTO>) commandHandler.executeCommand(BuscarTodosLosDiplomasCommand.class);
    }

    public List<DiplomaDTO> buscarTodosLosDiplomas() {
        BuscarTodosLosDiplomasCommand command = new BuscarTodosLosDiplomasCommand(consultasDiplomasUseCase,
                new DiplomaDTO());
        commandHandler.registerCommand(BuscarTodosLosDiplomasCommand.class, command);
        return (List<DiplomaDTO>) commandHandler.executeCommand(BuscarTodosLosDiplomasCommand.class);
    }


}
