package app.estudiante.servicio.InterfacesServicios;

import app.estudiante.utils.LoginRequestDTO;
import app.estudiante.utils.LoginResponseDTO;
import app.estudiante.utils.RespuestaOTPDTO;
import app.estudiante.utils.SolicitudOTPDTO;
import app.estudiante.utils.VerificarOTPDTO;

public interface IAutenticacionServicio {
    LoginResponseDTO verificarCredenciales(LoginRequestDTO request);
    RespuestaOTPDTO solicitarCodigoOTP(SolicitudOTPDTO solicitud);
    void verificarOTPYCambiarPassword(VerificarOTPDTO verificarDto);
}