package app.estudiante.utils;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;


public final  class ValidacionEdad {

    public static final int EDAD_MINIMA_ESTUDIANTE = 5;
    public static final int EDAD_MINIMA_TUTOR = 18;

    private ValidacionEdad() {
    }

    /** Años cumplidos a la fecha de hoy. */
    public static int calcularEdad(Date fechaNacimiento) {
        LocalDate nacimiento = fechaNacimiento.toLocalDate();
        LocalDate hoy = LocalDate.now();

        if (nacimiento.isAfter(hoy)) {
            throw new IllegalArgumentException(
                    "La fecha de nacimiento no puede ser futura.");
        }

        return Period.between(nacimiento, hoy).getYears();
    }

    public static void validarEdadEstudiante(Date fechaNacimiento) {
        validar(fechaNacimiento, EDAD_MINIMA_ESTUDIANTE, "estudiante");
    }

    public static void validarEdadTutor(Date fechaNacimiento) {
        validar(fechaNacimiento, EDAD_MINIMA_TUTOR, "tutor");
    }

    private static void validar(Date fechaNacimiento, int edadMinima, String persona) {
        if (fechaNacimiento == null) {
            throw new IllegalArgumentException(
                    "La fecha de nacimiento del " + persona
                            + " es obligatoria para verificar su edad.");
        }

        int edad = calcularEdad(fechaNacimiento);

        if (edad < edadMinima) {
            throw new IllegalArgumentException(
                    "El " + persona + " debe tener al menos " + edadMinima
                            + " años cumplidos (edad actual: " + edad + ").");
        }
    }


}
