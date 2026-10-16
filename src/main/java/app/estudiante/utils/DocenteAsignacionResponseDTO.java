package app.estudiante.utils;

public record DocenteAsignacionResponseDTO(
        Long idDetallePlanDeEstudio,
        Integer idPlanDeEstudio,
        Integer idAsignatura,
        String asignatura,
        Integer idGrado,
        String grado,
        Integer anioLectivo,
        Integer idSalon,
        String seccion,
        String turno
) {
}
