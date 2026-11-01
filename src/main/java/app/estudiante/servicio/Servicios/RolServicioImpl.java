package app.estudiante.servicio.Servicios;
import app.estudiante.exception.recursosNoEncontradoException;
import app.estudiante.utils.*;
import app.estudiante.modelo.RolEntity;
import app.estudiante.repositorio.IRolRepositorio;
import app.estudiante.servicio.InterfacesServicios.IRolServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RolServicioImpl implements IRolServicio {

    private final IRolRepositorio rolRepositorio;

    @Override
    @Transactional(readOnly = true)
    public List<RolResponseDTO> obtenerTodos() {
        return rolRepositorio.findAll().stream()
                .map(this::mapearADto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public RolResponseDTO obtenerPorId(Short id) {
        RolEntity rol = rolRepositorio.findById(id)
                .orElseThrow(() -> new recursosNoEncontradoException("Rol no encontrado con ID: " + id));
        return mapearADto(rol);
    }

    @Override
    @Transactional
    public RolResponseDTO crear(RolRequestDTO dto, String usuarioAuditoria) {
        String codigoFormateado = dto.codigo().toUpperCase().trim();
        if (rolRepositorio.existsByCodigo(codigoFormateado)) {
            throw new recursosNoEncontradoException("Ya existe un rol registrado con el código: " + codigoFormateado);
        }

        RolEntity rol = RolEntity.builder()
                .codigo(codigoFormateado)
                .descripcion(dto.descripcion())
                .build();

        return mapearADto(rolRepositorio.save(rol));
    }

    @Override
    @Transactional
    public RolResponseDTO actualizar(Short id, RolRequestDTO dto, String usuarioAuditoria) {
        RolEntity rol = rolRepositorio.findById(id)
                .orElseThrow(() -> new recursosNoEncontradoException("Rol no encontrado con ID: " + id));

        String codigoFormateado = dto.codigo().toUpperCase().trim();
        if (!rol.getCodigo().equalsIgnoreCase(codigoFormateado) && rolRepositorio.existsByCodigo(codigoFormateado)) {
            throw new recursosNoEncontradoException("Ya existe otro rol registrado con el código: " + codigoFormateado);
        }

        rol.setCodigo(codigoFormateado);
        rol.setDescripcion(dto.descripcion());

        return mapearADto(rolRepositorio.save(rol));
    }

    @Override
    @Transactional
    public void eliminar(Short id) {
        if (!rolRepositorio.existsById(id)) {
            throw new recursosNoEncontradoException("Rol no encontrado con ID: " + id);
        }
        rolRepositorio.deleteById(id);
    }

    private RolResponseDTO mapearADto(RolEntity entidad) {
        return new RolResponseDTO(
                entidad.getId(),
                entidad.getCodigo(),
                entidad.getDescripcion()
        );
    }
}
