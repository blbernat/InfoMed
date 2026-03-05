package com.fiap.infomed.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="consulta")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConsultaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;

    private String observacao;

    @Column(name= "data_consulta")
    private LocalDateTime dataConsulta;

    @Column(name= "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name= "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @ManyToOne
    @JoinColumn(
            name= "usuario_id",
            nullable = false)
    private UsuarioEntity usuario;

}
