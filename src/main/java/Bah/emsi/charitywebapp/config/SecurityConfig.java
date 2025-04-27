/*package Bah.emsi.charitywebapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Désactiver la protection CSRF (optionnel selon les besoins)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/utilisateurs",
                                "/utilisateurs/inscription",
                                "/utilisateurs/connexion",
                                "/utilisateurs/{id}/modifier",
                                "/organisations",
                                "/actions",
                                "/organisations/connexion",
                                "/organisations/{id}/modifier",
                                "/organisations/{id}/actions",
                                "/actions/{id}/modifier",
                                "/actionDeCharite/**",
                                "/"
                                ).permitAll() );
        return http.build();
    }
}*/
package Bah.emsi.charitywebapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Désactiver la protection CSRF si nécessaire
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()  // Permettre l'accès à toutes les requêtes sans authentification
                );
        return http.build();
    }
}


