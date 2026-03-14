package com.fiap.infomed.controller;

import com.fiap.infomed.dto.AgendamentoCreateDTO;
import com.fiap.infomed.dto.AgendamentoResponseDTO;
import com.fiap.infomed.dto.AgendamentoUpdateDTO;
import com.fiap.infomed.service.AgendamentoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

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
}
