package com.fiap.infomed.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="notificacao")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificacaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;

    private String mensagem;

    @Column(name= "data_envio")
    private LocalDateTime dataEnvio;

    @ManyToOne
    @JoinColumn(
            name= "usuario_id",
            nullable = false)
    private UsuarioEntity usuario;

    @ManyToOne
    @JoinColumn(
            name= "consulta_id",
            nullable = false)
    private ConsultaEntity consulta;

}
