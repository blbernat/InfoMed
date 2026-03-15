package com.fiap.infomed.dto;

import com.fiap.infomed.entities.EStatusConsulta;

import java.time.LocalDateTime;

public record ConsultaResponseDTO(Long id,
                                  EStatusConsulta status,
                                  String observacao,
                                  LocalDateTime dataHora,
                                  MedicoDTO medicoDTO,
                                  UsuarioDTO pacienteDTO) {
}
