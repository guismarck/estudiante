/*package app.estudiante.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityBeansConfig {
*/
    /**
     * Define el algoritmo de codificación de contraseñas estándar para la aplicación.
     * Se utiliza BCrypt con una fuerza de hashing (log rounds) de 12 para mitigar ataques de fuerza bruta.
     */
    /*
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}*/