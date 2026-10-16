package app.estudiante.utils;

import app.estudiante.modelo.Tutor;


public record EstudianteTutorDTO(
        Integer id,
        Tutor tutor,
        String parentesco,
        Boolean activo
) {
}
