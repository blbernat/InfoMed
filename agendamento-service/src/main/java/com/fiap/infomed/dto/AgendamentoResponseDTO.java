package com.fiap.infomed.dto;

import java.time.LocalDateTime;

public record AgendamentoResponseDTO(
        Long id,
        String status,
        String observacao,
        LocalDateTime dataConsulta,
        Long pacienteId,
        Long medicoId
) {}
