package com.fiap.infomed.entities;

import com.fiap.infomed.enums.EStatusConsulta;
import com.fiap.infomed.enums.EStatusNotificacao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "notificacao")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificacaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_consulta", unique = true)
    private Long idConsulta;

    @Enumerated(EnumType.STRING)
    private EStatusConsulta status;

    private String observacao;

    @Column(name = "data_consulta")
    private LocalDateTime dataConsulta;

    @Column(name = "nome_paciente")
    private String nomePaciente;

    @Column(name = "nome_medico")
    private String nomeMedico;

    @Enumerated(EnumType.STRING)
    private EStatusNotificacao statusNotificacao;
}
