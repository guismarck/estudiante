package app.estudiante.servicio.InterfacesServicios;
import app.estudiante.utils.*;

import java.util.List;

public interface IRolServicio {
    List<RolResponseDTO> obtenerTodos();
    RolResponseDTO obtenerPorId(Short id);
    RolResponseDTO crear(RolRequestDTO dto, String usuarioAuditoria);
    RolResponseDTO actualizar(Short id, RolRequestDTO dto, String usuarioAuditoria);
    void eliminar(Short id);
}