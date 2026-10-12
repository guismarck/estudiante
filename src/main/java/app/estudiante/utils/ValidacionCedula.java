package app.estudiante.utils;


import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidacionCedula {

    private static final Pattern PATTERN_CEDULA = Pattern.compile("^(\\d{3})-?(\\d{2})(\\d{2})(\\d{2})-?(\\d{4})([A-Za-zA-Z])$");

    /**
     * Extrae de forma segura la fecha de nacimiento codificada en la Cédula Nicaragüense.
     * @param cedula Cédula en formato con o sin guiones.
     * @return LocalDate extraída o null si no corresponde a una fecha válida.
     */
    public static LocalDate extraerFechaNacimiento(String cedula) {
        if (cedula == null || cedula.trim().isEmpty()) {
            return null;
        }

        Matcher matcher = PATTERN_CEDULA.matcher(cedula.trim());
        if (!matcher.matches()) {
            return null;
        }

        try {
            int dia = Integer.parseInt(matcher.group(2));
            int mes = Integer.parseInt(matcher.group(3));
            int anioDosDigitos = Integer.parseInt(matcher.group(4));

            int anioActual = LocalDate.now().getYear();
            int dosDigitosAnioActual = anioActual % 100;
            int sigloActual = (anioActual / 100) * 100;

            int anioCompleto = (anioDosDigitos > dosDigitosAnioActual)
                    ? (sigloActual - 100) + anioDosDigitos
                    : sigloActual + anioDosDigitos;

            return LocalDate.of(anioCompleto, mes, dia);
        } catch (DateTimeParseException | IllegalArgumentException e) {
            return null;
        }
    }
}
