package app.estudiante.utils.seguridad;

import org.springframework.stereotype.Component;

@Component
public interface PasswordHasher {
    /**
     * Calcula el hash para una contraseña en claro.
     *
     * @param password contraseña en claro (no nula)
     * @return representación del hash lista para persistencia
     */
    String hash(char[] password);

    /**
     * Verifica si la contraseña en claro coincide con el hash persistido.
     *
     * @param password contraseña en claro (no nula)
     * @param storedHash hash previamente generado (no nulo)
     * @return true si coincide; false en caso contrario
     */
    boolean verify(char[] password, String storedHash);

    boolean needsRehash(String storedHash);

}
