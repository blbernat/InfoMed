package com.fiap.infomed.dto;

import com.fiap.infomed.entities.TipoUsuarioEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioCreateDTO(@NotBlank(message = "É obrigatório informar o nome!")
                               String nome,
                               @Email(message = "O formato do email é inválido!")
                               @NotBlank(message = "É obrigatório informar o email!")
                               String email,
                               @NotBlank(message = "É obrigatório informar o login!")
                               String login,
                               @NotBlank(message = "É obrigatório informar a senha!")
                               String senha,
                               @NotNull(message = "É obrigatório informar o tipo de usuário")
                               TipoUsuarioEntity tipoUsuario) {
}
