package com.fiap.infomed.dto;

import jakarta.validation.constraints.NotBlank;

public record UsuarioUpdateSenhaDTO(@NotBlank(message = "É obrigatório informar o login!")
                                    String login,
                                    @NotBlank(message = "É obrigatório informar a senha atual!")
                                    String senhaAtual,
                                    @NotBlank(message = "É obrigatório informar a nova senha!")
                                    String senhaNova) {
}
