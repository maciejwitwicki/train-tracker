package io.mwi.traintracker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebFluxSecurity
public class AppConfiguration {

    private static final String[] NOT_SECURED_ENDPOINTS = {
            "/login", "/register", "/actuator/**", "/swagger-ui.html", "/webjars/swagger-ui/**", "/v3/api-docs/**"
    };

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange()
                .pathMatchers(NOT_SECURED_ENDPOINTS).permitAll()
                .anyExchange().authenticated()
                .and()
                .csrf().disable()
                .httpBasic().disable()
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))//.and()
                .oauth2ResourceServer().jwt();

        return http.build();
    }

    CorsConfigurationSource corsConfigurationSource() {
        var configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}
