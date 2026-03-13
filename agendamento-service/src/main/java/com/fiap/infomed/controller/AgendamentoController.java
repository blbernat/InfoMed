package com.fiap.infomed.controller;

import com.fiap.infomed.entities.ConsultaEntity;
import com.fiap.infomed.service.AgendamentoService;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    public ConsultaEntity create(@RequestBody ConsultaEntity agendamento) {
        return service.createAppointment(agendamento);
    }

    @PutMapping
    public ConsultaEntity updateAppointment(@RequestBody ConsultaEntity agendamento) {
        return service.updateAppointment(agendamento);
    }
}
