package com.example.LimpezaWMS.security;

import com.example.LimpezaWMS.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private UsuarioService usuarioService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // Recursos públicos
                .requestMatchers("/login", "/css/**", "/js/**", "/images/**").permitAll()

                // Apenas ADMIN pode deletar
                .requestMatchers(HttpMethod.DELETE, "/**").hasRole("ADMIN")

                // OPERADOR e ADMIN podem consultar, inserir e atualizar
                .anyRequest().hasAnyRole("ADMIN", "OPERADOR")
            )
            .formLogin(form -> form
                .loginPage("/login")           // página customizada
                .defaultSuccessUrl("/dashboard", true)
                .failureUrl("/login?erro=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?saiu=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
            );

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(
            UsuarioService usuarioService,
            PasswordEncoder passwordEncoder
    ) {
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(usuarioService);

        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }

}
