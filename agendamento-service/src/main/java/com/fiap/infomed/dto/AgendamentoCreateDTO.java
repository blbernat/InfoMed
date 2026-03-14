package com.fiap.infomed.dto;

import com.fiap.infomed.entities.EStatusConsulta;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AgendamentoCreateDTO(
        @NotNull(message = "Status é obrigatório")
        EStatusConsulta status,
        String observacao,
        @NotNull(message = "Data da consulta é obrigatória")
        LocalDateTime dataConsulta,
        @NotNull(message = "ID do paciente é obrigatório")
        Long pacienteId,
        @NotNull(message = "ID do médico é obrigatório")
        Long medicoId
) {}
