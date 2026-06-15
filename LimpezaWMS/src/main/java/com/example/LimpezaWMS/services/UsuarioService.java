package com.example.LimpezaWMS.services;

import com.example.LimpezaWMS.dtos.UsuarioDto;
import com.example.LimpezaWMS.models.UsuarioModel;
import com.example.LimpezaWMS.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    // -------------------------------------------------------
    // Exigido pelo Spring Security — busca usuário pelo e-mail
    // -------------------------------------------------------

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UsuarioModel usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));

        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getSenha())   // já armazenada com BCrypt
                .roles(usuario.getRole())        // "ADMIN" ou "OPERADOR"
                .build();
    }

    // -------------------------------------------------------
    // CRUD
    // -------------------------------------------------------

    public List<UsuarioModel> listarTodos() {
        return usuarioRepository.findAll();
    }

    public UsuarioModel buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado: " + id));
    }

    public UsuarioModel criar(UsuarioDto dto) {
        if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Já existe um usuário com este e-mail.");
        }

        UsuarioModel usuario = new UsuarioModel();
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        usuario.setRole(dto.getRole().toUpperCase());

        return usuarioRepository.save(usuario);
    }

    public UsuarioModel editar(Long id, UsuarioDto dto) {
        UsuarioModel usuario = buscarPorId(id);

        usuario.setEmail(dto.getEmail());
        usuario.setRole(dto.getRole().toUpperCase());

        // Só atualiza a senha se uma nova foi informada
        if (dto.getSenha() != null && !dto.getSenha().isBlank()) {
            usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        }

        return usuarioRepository.save(usuario);
    }

    public void excluir(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new IllegalArgumentException("Usuário não encontrado: " + id);
        }
        usuarioRepository.deleteById(id);
    }
}