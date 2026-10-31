package app.estudiante.servicio.InterfacesServicios;

public interface INotificacionEmailServicio {
    void enviarOTPDocente(String email, String nombreDocente, String otpCode);
}
