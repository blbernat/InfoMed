package com.fiap.infomed.dto;

import java.time.LocalDateTime;

public record HistoricalResponseDTO(String diagnostico,
                                    String tratamento,
                                    LocalDateTime dataAtualizacao,
                                    MedicoDTO medicoDTO) {
}
