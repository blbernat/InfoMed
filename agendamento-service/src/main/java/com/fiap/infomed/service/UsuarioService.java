package com.fiap.infomed.service;

import com.fiap.infomed.dto.UsuarioCreateDTO;
import com.fiap.infomed.dto.UsuarioResponseDTO;
import com.fiap.infomed.dto.UsuarioUpdateDTO;
import com.fiap.infomed.entities.UsuarioEntity;
import com.fiap.infomed.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void saveUsuario(UsuarioCreateDTO usuarioDTO) {
        UsuarioEntity usuario = new UsuarioEntity();

        usuario.setNome(usuarioDTO.nome());
        usuario.setEmail(usuarioDTO.email());
        usuario.setLogin(usuarioDTO.login());
        usuario.setCpf(usuarioDTO.cpf());
        usuario.setDataNascimento(usuarioDTO.dataNascimento());
        usuario.setSenha(passwordEncoder.encode(usuarioDTO.senha())); // Encode the password
        usuario.setTipoUsuario(usuarioDTO.tipoUsuario());
        usuario.setDataAtualizacao(LocalDateTime.now());

        usuarioRepository.save(usuario);
    }

    public void updateUsuario(UsuarioUpdateDTO usuarioDTO) {
        UsuarioEntity usuario = usuarioRepository.findByLogin(usuarioDTO.login())
                .orElseThrow(() ->
                        new EntityNotFoundException("Usuário não encontrado para a ação solicitada! O cadastro não pode ser alterado!"));

        usuarioRepository.findByEmail(usuarioDTO.email())
                .filter(u -> !usuarioDTO.login().equals(u.getLogin()))
                .ifPresent(u -> {
                    throw new IllegalArgumentException("Esse e-mail já está sendo utilizado por outro usuário.");
                });

        usuario.setNome(usuarioDTO.nome());
        usuario.setEmail(usuarioDTO.email());
        usuario.setTipoUsuario(usuarioDTO.tipoUsuario());
        usuario.setDataAtualizacao(LocalDateTime.now());

        usuarioRepository.save(usuario);
    }

    public void deleteUsuario(String login) {
        UsuarioEntity usuarioDelete = usuarioRepository.findByLogin(login)
                .orElseThrow(() ->
                        new EntityNotFoundException("Usuário não encontrado!"));
        usuarioRepository.delete(usuarioDelete);
    }

    public List<UsuarioResponseDTO> buscarUsuarios() {
        List<UsuarioEntity> listUsuarios = usuarioRepository.findAll();

        return listUsuarios.stream()
                .map(this::mapToDomainUsuario)
                .toList();
    }

    private UsuarioResponseDTO mapToDomainUsuario(UsuarioEntity usuario){
        if (usuario == null) return null;
        return new UsuarioResponseDTO(usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getLogin(),
                usuario.getCpf(),
                usuario.getDataNascimento(),
                usuario.getDataAtualizacao(),
                usuario.getTipoUsuario());
    }
}
