package com.fiap.infomed.dto;

import com.fiap.infomed.entities.EStatusConsulta;

import java.time.LocalDateTime;

public record ConsultaResponseDTO(EStatusConsulta status,
                                  String observacao,
                                  LocalDateTime horaConsulta,
                                  MedicoDTO medicoDTO) {
}
