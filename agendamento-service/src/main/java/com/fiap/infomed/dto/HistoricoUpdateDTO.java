package com.fiap.infomed.dto;

import java.time.LocalDateTime;

public record HistoricoUpdateDTO(
    Long id,
    String diagnostico,
    String tratamento,
    String observacoes,
    LocalDateTime dataAtualizacao
) {}
