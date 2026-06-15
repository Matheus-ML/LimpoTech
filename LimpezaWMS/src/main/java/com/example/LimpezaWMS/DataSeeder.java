package com.example.LimpezaWMS;

import com.example.LimpezaWMS.models.UsuarioModel;
import com.example.LimpezaWMS.repositories.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        criarAdminSeNaoExistir();
    }

    private void criarAdminSeNaoExistir() {
        if (usuarioRepository.findByEmail("admin@limpotech.com").isPresent()) {
            return; // já existe, não faz nada
        }

        UsuarioModel admin = new UsuarioModel();
        admin.setEmail("admin@limpotech.com");
        admin.setSenha(passwordEncoder.encode("admin123"));
        admin.setRole("ADMIN");

        usuarioRepository.save(admin);

        System.out.println(">>> [DataSeeder] Usuário ADMIN criado: admin@limpotech.com / admin123");
    }
}
