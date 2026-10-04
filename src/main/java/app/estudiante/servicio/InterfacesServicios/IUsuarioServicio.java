package app.estudiante.servicio.InterfacesServicios;


import app.estudiante.utils.UsuarioRequestDTO;
import app.estudiante.utils.UsuarioResponseDTO;
import app.estudiante.utils.seguridad.AuthRequestDTO;
import app.estudiante.utils.seguridad.AuthResponseDTO;

import java.util.List;

public interface IUsuarioServicio {
    UsuarioResponseDTO crear(UsuarioRequestDTO request);
    UsuarioResponseDTO obtenerUsuarioPorId(Long id);
    List<UsuarioResponseDTO> listarTodos();
    UsuarioResponseDTO actualizar(Long id, UsuarioRequestDTO request);
    AuthResponseDTO autenticar(AuthRequestDTO request);

}
