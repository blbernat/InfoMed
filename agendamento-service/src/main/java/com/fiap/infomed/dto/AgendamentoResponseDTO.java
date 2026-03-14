package com.fiap.infomed.dto;

import com.fiap.infomed.entities.EStatusConsulta;

import java.time.LocalDateTime;

public record AgendamentoResponseDTO(
        Long id,
        EStatusConsulta status,
        String observacao,
        LocalDateTime dataConsulta,
        Long pacienteId,
        Long medicoId
) {}
