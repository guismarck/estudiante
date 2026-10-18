package app.estudiante.servicio.InterfacesServicios;

import app.estudiante.utils.LoginRequestDTO;
import app.estudiante.utils.LoginResponseDTO;

public interface IAutenticacionServicio {
    LoginResponseDTO verificarCredenciales(LoginRequestDTO request);
}