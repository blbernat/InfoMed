package com.fiap.infomed.controller;

import com.fiap.infomed.dto.AgendamentoCreateDTO;
import com.fiap.infomed.dto.AgendamentoResponseDTO;
import com.fiap.infomed.dto.AgendamentoUpdateDTO;
import com.fiap.infomed.dto.ConsultaResponseDTO;
import com.fiap.infomed.service.AgendamentoService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AgendamentoController {
    private final AgendamentoService agendamentoService;

    public AgendamentoController(AgendamentoService agendamentoService) {
        this.agendamentoService = agendamentoService;
    }

    @QueryMapping
    @PreAuthorize("hasPermission(#patientId, 'Patient', 'read')")
    public List<ConsultaResponseDTO> patientAppointments(@Argument Long patientId) {
        return agendamentoService.patientAppointments(patientId);
    }

    @QueryMapping
    @PreAuthorize("hasPermission(#patientId, 'Patient', 'read')")
    public List<ConsultaResponseDTO> futureAppointments(@Argument Long patientId) {
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
}
