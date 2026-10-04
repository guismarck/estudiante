package app.estudiante.servicio.InterfacesServicios;

import app.estudiante.utils.seguridad.ModuloUsuarioDTO;

import java.util.List;

public interface IModuloService {

    List<ModuloUsuarioDTO> obtenerModulosPorRol(String rolCodigo);
}
