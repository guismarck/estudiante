package app.estudiante.utils.seguridad;

import java.util.Arrays;

public class SecurityUtils {

    private SecurityUtils() {}

    public static void wipe(char[] array) {
        if (array != null) {
            Arrays.fill(array, '\0');
        }
    }

    public static int extractBcryptCost(String storedHash) {
        if (storedHash != null && storedHash.startsWith("$2")) {
            String[] parts = storedHash.split("\\$");
            if (parts.length >= 3) {
                try {
                    return Integer.parseInt(parts[2]);
                } catch (NumberFormatException ignored) {
                    // Fallback a costo por defecto
                }
            }
        }
        return 10;
    }
}
