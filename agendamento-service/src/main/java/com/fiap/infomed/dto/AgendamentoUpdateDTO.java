package com.fiap.infomed.dto;

import com.fiap.infomed.entities.EStatusConsulta;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AgendamentoUpdateDTO(
        @NotNull(message = "ID do agendamento é obrigatório")
        Long id,
        @NotNull(message = "Status é obrigatório")
        EStatusConsulta status,
        String observacao,
        @NotNull(message = "Data da consulta é obrigatória")
        LocalDateTime dataConsulta
) {}
