package app.estudiante.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        // Activa la configuración de CORS definida arriba
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .csrf(csrf -> csrf.disable())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
            // Libera de forma absoluta las peticiones Preflight (OPTIONS)
            .requestMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll()
            .requestMatchers("/api/auth/**").permitAll()
            .anyRequest().authenticated()
        );

    return http.build();
}

    @Bean
public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration config = new CorsConfiguration();

    // 1. Especifica los orígenes o usa allowedOriginPatterns de forma explícita
    config.setAllowedOriginPatterns(List.of(
        "https://*.app.github.dev",
        "http://localhost:3000",
        "http://localhost:5173"
    ));

    // 2. Define todos los métodos HTTP que usará la aplicación
    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "PATCH"));

    // 3. Define explícitamente las cabeceras permitidas (evita usar "*")
    config.setAllowedHeaders(List.of(
        "Authorization",
        "Cache-Control",
        "Content-Type", "Accept",
        "X-Requested-With",
        "Access-Control-Allow-Origin",
        "Access-Control-Allow-Headers",
        "Origin"
    ));

    // 4. Cabeceras expuestas para que Axios pueda leerlas si es necesario
    config.setExposedHeaders(List.of("Authorization", "Link", "X-Total-Count"));

    // 5. Permitir cookies/credenciales y definir el tiempo de caché del preflight
    config.setAllowCredentials(true);
    config.setMaxAge(3600L);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    return source;
}
}