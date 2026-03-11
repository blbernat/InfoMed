package com.fiap.infomed.dto;

import java.time.LocalDateTime;

public record ConsultaResponseDTO(String status,
                                  String observacao,
                                  LocalDateTime horaConsulta,
                                  MedicoDTO medicoDTO) {
}
