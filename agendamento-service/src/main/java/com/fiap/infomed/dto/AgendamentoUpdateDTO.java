package com.fiap.infomed.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AgendamentoUpdateDTO(
        @NotNull(message = "ID do agendamento é obrigatório")
        Long id,
        @NotBlank(message = "Status é obrigatório")
        String status,
        String observacao,
        @NotNull(message = "Data da consulta é obrigatória")
        LocalDateTime dataConsulta,
        @NotNull(message = "ID do paciente é obrigatório")
        Long pacienteId,
        @NotNull(message = "ID do médico é obrigatório")
        Long medicoId
) {}
