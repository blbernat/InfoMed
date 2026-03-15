package com.fiap.infomed.controller;

import com.fiap.infomed.dto.*;
import com.fiap.infomed.entities.UsuarioEntity;
import com.fiap.infomed.service.AgendamentoService;
import com.fiap.infomed.service.UsuarioService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AgendamentoController {
    private final AgendamentoService agendamentoService;
    private final UsuarioService usuarioService;

    public AgendamentoController(AgendamentoService agendamentoService, UsuarioService usuarioService) {
        this.agendamentoService = agendamentoService;
        this.usuarioService = usuarioService;
    }

    @QueryMapping
    @PreAuthorize("hasPermission(#patientId, 'Patient', 'read')")
    public List<AgendamentoResponseDTO> patientAppointments(@Argument Long patientId) {
        return agendamentoService.patientAppointments(patientId);
    }

    @QueryMapping
    @PreAuthorize("hasPermission(#patientId, 'Patient', 'read')")
    public List<AgendamentoResponseDTO> futureAppointments(@Argument Long patientId) {
        return agendamentoService.futureAppointments(patientId);
    }

    @MutationMapping
    @PreAuthorize("hasAnyRole('MEDICO', 'ENFERMEIRO')")
    public AgendamentoResponseDTO createAppointment(@Argument AgendamentoCreateDTO agendamentoCreateDTO) {
        return agendamentoService.createAgendamento(agendamentoCreateDTO);
    }

    @MutationMapping
    @PreAuthorize("hasAnyRole('MEDICO', 'ENFERMEIRO')")
    public AgendamentoResponseDTO updateAppointment(@Argument AgendamentoUpdateDTO agendamentoUpdateDTO) {
        return agendamentoService.updateAgendamento(agendamentoUpdateDTO);
    }

    @MutationMapping
    @PreAuthorize("hasAnyRole('MEDICO', 'ENFERMEIRO')")
    public void deleteAppointment(@Argument Long id) {
        agendamentoService.deleteAppointment(id);
    }

    @QueryMapping
    @PreAuthorize("hasAnyRole('MEDICO', 'ENFERMEIRO')")
    public List<AgendamentoResponseDTO> findByMedicoId (@Argument Long medicoId) {
        return agendamentoService.findByMedicoId(medicoId);
    }

    @QueryMapping
    @PreAuthorize("hasPermission(#consultaId, 'Consulta', 'read')")
    public AgendamentoResponseDTO findById (@Argument Long consultaId) {
        return agendamentoService.findById(consultaId);
    }

    @SchemaMapping(typeName = "Consulta", field = "paciente")
    public UsuarioDTO paciente(AgendamentoResponseDTO agendamento) {
        if (agendamento.pacienteId() == null) return null;
        UsuarioEntity usuario = usuarioService.findUsuarioById(agendamento.pacienteId());
        return new UsuarioDTO(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }

    @SchemaMapping(typeName = "Consulta", field = "medico")
    public UsuarioDTO medico(AgendamentoResponseDTO agendamento) {
        if (agendamento.medicoId() == null) return null;
        UsuarioEntity usuario = usuarioService.findUsuarioById(agendamento.medicoId());
        return new UsuarioDTO(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }
}
