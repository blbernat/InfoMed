package com.fiap.infomed.controller;

import com.fiap.infomed.dto.AgendamentoCreateDTO;
import com.fiap.infomed.dto.AgendamentoResponseDTO;
import com.fiap.infomed.dto.AgendamentoUpdateDTO;
import com.fiap.infomed.service.AgendamentoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/agendamento")
@Tag(name = "Agendamento", description = "Endpoints para agendamento de consultas")
public class AgendamentoController {

    private final AgendamentoService service;

    public AgendamentoController(AgendamentoService service) {
        this.service = service;
    }

    @PostMapping
    public AgendamentoResponseDTO create(@RequestBody @Valid AgendamentoCreateDTO agendamentoDTO) {
        return service.createAppointment(agendamentoDTO);
    }

    @PutMapping
    public AgendamentoResponseDTO updateAppointment(@RequestBody @Valid AgendamentoUpdateDTO agendamentoDTO) {
        return service.updateAppointment(agendamentoDTO);
    }

    @DeleteMapping
    public void deleteAppointment(@RequestParam Long id) {
        service.deleteAppointment(id);
    }

    @GetMapping("/consulta")
    public AgendamentoResponseDTO findById(@RequestParam Long idConsulta) {
        return service.findById(idConsulta);
    }

    @GetMapping("/paciente")
    public List<AgendamentoResponseDTO> findByPacienteId(@RequestParam Long pacienteId) {
        return service.findByPacienteId(pacienteId);
    }

    @GetMapping("/medico")
    public List<AgendamentoResponseDTO> findByMedicoId(@RequestParam Long pacienteId) {
        return service.findByMedicoId(pacienteId);
    }
}
