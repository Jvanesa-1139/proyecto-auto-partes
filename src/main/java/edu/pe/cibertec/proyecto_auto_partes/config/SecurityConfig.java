package edu.pe.cibertec.proyecto_auto_partes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/maintenance/login").permitAll()
                        .requestMatchers("/maintenance/start").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/maintenance/create").hasAnyRole("ADMIN")
                        .requestMatchers("/maintenance/add").hasAnyRole("ADMIN")
                        .requestMatchers("/maintenance-producto/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/maintenance/login")
                        .defaultSuccessUrl("/maintenance/start", false)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/maintenance/logout")
                        .logoutSuccessUrl("/maintenance/login?logout")
                        .permitAll()
                )
                .anonymous(anonymous -> anonymous
                        .disable()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
