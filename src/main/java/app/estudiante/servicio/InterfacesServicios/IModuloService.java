package app.estudiante.servicio.InterfacesServicios;

import app.estudiante.utils.seguridad.ModuloMenuDTO;
import app.estudiante.utils.seguridad.ModuloUsuarioDTO;

import java.util.List;

public interface IModuloService {

    List<ModuloMenuDTO> obtenerMenuPorUsuario(String username);
    List<ModuloMenuDTO> obtenerRutasPlanasPorUsuario(String username);
}
