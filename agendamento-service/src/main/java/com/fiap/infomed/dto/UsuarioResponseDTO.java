package com.fiap.infomed.dto;

import com.fiap.infomed.entities.ETipoUsuario;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UsuarioResponseDTO (Long id,
                                  String nome,
                                  String email,
                                  String login,
                                  String cpf,
                                  LocalDate dataNascimento,
                                  LocalDateTime dataAtualizacao,
                                  ETipoUsuario tipoUsuario){
}
