package app.estudiante.servicio.InterfacesServicios;

import app.estudiante.utils.AsignarRolesDTO;
import app.estudiante.utils.UsuarioActualizarRequestDTO;
import app.estudiante.utils.UsuarioCrearRequestDTO;
import app.estudiante.utils.UsuarioResponseDTO;
import java.util.List;

public interface IUsuarioServicio {
    UsuarioResponseDTO crearUsuario(UsuarioCrearRequestDTO dto, String usuarioCreador);
    UsuarioResponseDTO actualizarUsuario(Long id, UsuarioActualizarRequestDTO dto);
    UsuarioResponseDTO obtenerPorId(Long id);
    List<UsuarioResponseDTO> obtenerTodos();
    UsuarioResponseDTO asignarRoles(AsignarRolesDTO dto, String usuarioCreador);
    void cambiarEstado(Long id, Integer nuevoEstado);

}
