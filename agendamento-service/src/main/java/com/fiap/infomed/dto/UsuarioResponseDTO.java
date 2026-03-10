package com.fiap.infomed.dto;

import com.fiap.infomed.entities.TipoUsuarioEntity;
import java.time.LocalDateTime;

public record UsuarioResponseDTO (String nome,
                                  String email,
                                  String login,
                                  LocalDateTime dataAtualizacao,
                                  TipoUsuarioEntity tipoUsuario){
}
