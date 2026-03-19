package com.fiap.infomed.service;

import com.fiap.infomed.dto.UsuarioCreateDTO;
import com.fiap.infomed.dto.UsuarioResponseDTO;
import com.fiap.infomed.dto.UsuarioUpdateDTO;
import com.fiap.infomed.dto.UsuarioUpdateSenhaDTO;
import com.fiap.infomed.entities.UsuarioEntity;
import com.fiap.infomed.repository.UsuarioRepository;
import com.fiap.infomed.service.exceptions.CreateUserException;
import com.fiap.infomed.service.exceptions.InvalidPasswordException;
import com.fiap.infomed.service.exceptions.UserNotFoundException;
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
        usuarioRepository.findByLogin(usuarioDTO.login())
                .ifPresent(u -> {
                    throw new CreateUserException("Esse login já está sendo utilizado por outro usuário.");
                });

        usuarioRepository.findByEmail(usuarioDTO.email())
                .ifPresent(u -> {
                    throw new CreateUserException("Esse e-mail já está sendo utilizado por outro usuário.");
                });

        usuarioRepository.findByCpf(usuarioDTO.cpf())
                .ifPresent(u -> {
                    throw new CreateUserException("Esse CPF já está sendo utilizado por outro usuário.");
                });
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
                        new UserNotFoundException("Usuário não encontrado para a ação solicitada! O cadastro não pode ser alterado!"));

        usuarioRepository.findByEmail(usuarioDTO.email())
                .filter(u -> !usuarioDTO.login().equals(u.getLogin()))
                .ifPresent(u -> {
                    throw new CreateUserException("Esse e-mail já está sendo utilizado por outro usuário.");
                });

        usuarioRepository.findByCpf(usuarioDTO.cpf())
                .filter(u -> !usuarioDTO.login().equals(u.getLogin()))
                .ifPresent(u -> {
                    throw new CreateUserException("Esse CPF já está sendo utilizado por outro usuário.");
                });

        usuario.setNome(usuarioDTO.nome());
        usuario.setEmail(usuarioDTO.email());
        usuario.setCpf(usuarioDTO.cpf());
        usuario.setDataNascimento(usuarioDTO.dataNascimento());
        usuario.setTipoUsuario(usuarioDTO.tipoUsuario());
        usuario.setDataAtualizacao(LocalDateTime.now());

        usuarioRepository.save(usuario);
    }

    public void deleteUsuario(String login) {
        UsuarioEntity usuarioDelete = usuarioRepository.findByLogin(login)
                .orElseThrow(() ->
                        new UserNotFoundException("Usuário não encontrado!"));
        usuarioRepository.delete(usuarioDelete);
    }

    public void updateSenhaUsuario(UsuarioUpdateSenhaDTO usuarioDTO) {
        UsuarioEntity usuario = usuarioRepository.findByLogin(usuarioDTO.login())
                .orElseThrow(() ->
                        new UserNotFoundException("Usuário não encontrado para a ação solicitada! O cadastro não pode ser alterado!"));

        boolean senhaValida = passwordEncoder.matches(usuarioDTO.senhaAtual(), usuario.getSenha());
        if (!senhaValida) {
            throw new InvalidPasswordException("A senha atual está incorreta!");
        }

        usuario.setSenha(passwordEncoder.encode(usuarioDTO.senhaNova()));
        usuario.setDataAtualizacao(LocalDateTime.now());

        usuarioRepository.save(usuario);
    }

    public List<UsuarioResponseDTO> buscarUsuarios() {
        List<UsuarioEntity> listUsuarios = usuarioRepository.findAll();

        return listUsuarios.stream()
                .map(this::mapToDomainUsuario)
                .toList();
    }

    public UsuarioEntity findUsuarioById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado!"));
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
