package com.fiap.infomed.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AgendamentoMessageDTO {
    private Long id;
    private String status;
    private String observacao;
    private LocalDateTime dataConsulta;
    private UsuarioDTO paciente;
    private UsuarioDTO medico;
}
