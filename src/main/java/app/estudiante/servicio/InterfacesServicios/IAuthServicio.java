package app.estudiante.servicio.InterfacesServicios;


import app.estudiante.utils.seguridad.LoginRequest;
import app.estudiante.utils.seguridad.LoginResponse;

public interface IAuthServicio {
    LoginResponse autenticar(LoginRequest request);
}