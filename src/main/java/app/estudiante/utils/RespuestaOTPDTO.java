package app.estudiante.utils;

public record RespuestaOTPDTO(
    String mensaje,
    Long docenteId,
    String tokenRecuperacion,
    String emailEnmascarado
) {}