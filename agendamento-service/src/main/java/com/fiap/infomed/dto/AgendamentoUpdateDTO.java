package com.fiap.infomed.dto;

import com.fiap.infomed.entities.EStatusConsulta;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AgendamentoUpdateDTO(
        @NotNull(message = "ID do agendamento é obrigatório")
        Long id,
        EStatusConsulta status,
        String observacao,
        LocalDateTime dataConsulta
) {}
