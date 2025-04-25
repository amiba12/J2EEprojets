package Bah.emsi.charitywebapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Désactive CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/utilisateurs",
                                "/utilisateurs/inscription",
                                "/utilisateurs/connexion",
                                "/utilisateurs/{id}/modifier",
                                "/organisations",
                                "/organisations/connexion",
                                "/organisations/{id}/modifier",
                                "/organisations/{id}/actions",
                                "/actions/{id}/modifier",
                                ("/actionDeCharite/**")
                        ).permitAll()  // Autorise ces URL sans authentification
                        .anyRequest().permitAll()  // Autorise toutes les autres URL sans authentification
                )
                .logout(withDefaults());  // Permet la déconnexion

        return http.build();
    }
}
