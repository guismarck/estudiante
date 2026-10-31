package app.estudiante.servicio.InterfacesServicios;

public interface ITokenStatelessServicio {
    String generarTokenRecuperacion(Long docenteId, String otpCode, long expiracionEpochMilli);
    boolean validarTokenYOTP(String tokenRecuperacion, Long docenteId, String otpIngresado);
}