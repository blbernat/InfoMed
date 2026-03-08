package com.fiap.infomed.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="usuario")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true)
    private String login;

    private String senha;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String cpf;

    @Column(name= "data_nascimento")
    private LocalDateTime dataNascimento;

    @Column(name= "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @Enumerated(EnumType.STRING)
    private TipoUsuarioEntity tipoUsuario;

}
