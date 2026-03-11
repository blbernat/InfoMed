package com.fiap.infomed.service;

import com.fiap.infomed.dto.UsuarioCreateDTO;
import com.fiap.infomed.dto.UsuarioResponseDTO;
import com.fiap.infomed.dto.UsuarioUpdateDTO;
import com.fiap.infomed.entities.UsuarioEntity;
import com.fiap.infomed.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void saveUsuario(UsuarioCreateDTO usuarioDTO) {
        UsuarioEntity usuario = new UsuarioEntity();

        usuario.setNome(usuarioDTO.nome());
        usuario.setEmail(usuarioDTO.email());
        usuario.setLogin(usuarioDTO.login());
        usuario.setSenha(usuarioDTO.senha());
        usuario.setTipoUsuario(usuarioDTO.tipoUsuario());
        usuario.setDataAtualizacao(LocalDateTime.now());

        usuarioRepository.save(usuario);
    }

    public void updateUsuario(UsuarioUpdateDTO usuarioDTO) {
        try {
            UsuarioEntity usuario = usuarioRepository.findByLogin(usuarioDTO.login());

            if (usuario == null) {
                throw new EntityNotFoundException("Usuário não encontrado para a ação solicitada! O cadastro não pode ser alterado!");
            }

            UsuarioEntity usuarioByEmail = usuarioRepository.findByEmail(usuarioDTO.email());

            if (!usuarioByEmail.getLogin().equals(usuarioDTO.login())) {
                throw new RuntimeException("Esse e-mail já está sendo utilizado por outro usuário.");
            }

            usuario.setNome(usuarioDTO.nome());
            usuario.setEmail(usuarioDTO.email());
            usuario.setTipoUsuario(usuarioDTO.tipoUsuario());
            usuario.setDataAtualizacao(LocalDateTime.now());

            usuarioRepository.save(usuario);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Usuário não encontrado!");
        }
    }

    public void deleteUsuario(String login) {
        try {
            UsuarioEntity usuarioDelete = usuarioRepository.findByLogin(login);
            usuarioRepository.delete(usuarioDelete);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Usuário não encontrado!", e);
        }
    }

    public List<UsuarioResponseDTO> buscarUsuarios() {
        List<UsuarioEntity> listUsuarios = usuarioRepository.findAll();

        return listUsuarios.stream()
                .map(this::mapToDomainUsuario)
                .toList();
    }

    private UsuarioResponseDTO mapToDomainUsuario(UsuarioEntity usuario){
        if (usuario == null) return null;
        return new UsuarioResponseDTO(usuario.getNome(),
                usuario.getEmail(),
                usuario.getLogin(),
                usuario.getDataAtualizacao(),
                usuario.getTipoUsuario());
    }
}
