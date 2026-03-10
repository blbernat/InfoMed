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

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private UsuarioEntity paciente;

    @ManyToOne
    @JoinColumn(name = "medico_id")
    private UsuarioEntity medico;

}
