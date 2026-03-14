package com.fiap.infomed.dto;

import com.fiap.infomed.entities.ETipoUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record UsuarioUpdateDTO(@NotBlank(message = "É obrigatório informar o nome!")
                                String nome,
                               @Email(message = "O formato do email é inválido!")
                                @NotBlank(message = "É obrigatório informar o email!")
                                String email,
                               @NotBlank(message = "É obrigatório informar o login!")
                                String login,
                               @CPF(message = "O formato do CPF é inválido!")
                               @NotBlank(message = "É obrigatório informar o CPF!")
                               String cpf,
                               @NotNull(message = "É obrigatório informar a data de nascimento!")
                               LocalDate dataNascimento,
                               @NotNull(message = "É obrigatório informar o tipo de usuário")
                                ETipoUsuario tipoUsuario) {
}
