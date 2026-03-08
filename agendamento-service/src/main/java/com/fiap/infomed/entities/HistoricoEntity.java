package com.fiap.infomed.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="historico_consultas")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HistoricoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String diagnostico;

    private String tratamento;

    @Column(name= "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @ManyToOne
    private UsuarioEntity paciente;

    @ManyToOne
    private UsuarioEntity medico;

}
