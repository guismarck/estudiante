package app.estudiante.utils.seguridad;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Envoltorio utilitario para el manejo seguro de hashes de contraseñas.
 * Se registra explícitamente como un @Component gestionado por el contenedor IoC de Spring.
 */
@Component
public class PasswordHasher {

    private final PasswordEncoder passwordEncoder;

    public PasswordHasher(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Genera un hash BCrypt seguro a partir de una contraseña en texto plano.
     *
     * @param rawPassword Contraseña sin encriptar.
     * @return Hash codificado con sal integrada.
     */
    public String hash(String rawPassword) {
        if (rawPassword == null || rawPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía.");
        }
        return passwordEncoder.encode(rawPassword);
    }

    /**
     * Verifica si una contraseña en texto plano coincide con el hash almacenado.
     *
     * @param rawPassword Contraseña enviada durante la autenticación.
     * @param encodedHash Hash almacenado en la base de datos.
     * @return true si la contraseña coincide; false en caso contrario.
     */
    public boolean verify(String rawPassword, String encodedHash) {
        if (rawPassword == null || encodedHash == null) {
            return false;
        }
        return passwordEncoder.matches(rawPassword, encodedHash);
    }
}