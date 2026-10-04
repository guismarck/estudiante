package app.estudiante.servicio.Servicios;

import app.estudiante.repositorio.ModuloRepository;
import app.estudiante.servicio.InterfacesServicios.IModuloService;
import app.estudiante.utils.seguridad.ModuloUsuarioDTO;
import app.estudiante.utils.seguridad.PermisosDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ModuloServiceImpl implements IModuloService {

    private final ModuloRepository moduloRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ModuloUsuarioDTO> obtenerModulosPorRol(String rolCodigo) {
        List<Object[]> resultados = moduloRepository.findModulosYPermisosByRol(rolCodigo);

        Map<Long, ModuloUsuarioDTO> mapModulos = new LinkedHashMap<>();
        List<ModuloUsuarioDTO> modulosRaiz = new ArrayList<>();

        // 1. Mapeo plano de registros de BD a DTOs
        for (Object[] row : resultados) {
            Long id = ((Number) row[0]).longValue();
            String nombre = (String) row[1];
            String codigo = (String) row[2];
            String recurso = (String) row[3];
            String componentKey = (String) row[4];
            String pathImg = (String) row[5];
            Long moduloPadreId = row[6] != null ? ((Number) row[6]).longValue() : null;

            PermisosDTO permisos = PermisosDTO.builder()
                    .puedeBuscar(toBoolean(row[7]))
                    .puedeAgregar(toBoolean(row[8]))
                    .puedeModificar(toBoolean(row[9]))
                    .puedeInactivar(toBoolean(row[10]))
                    .puedeProcesar(toBoolean(row[11]))
                    .puedeGuardar(toBoolean(row[12]))
                    .puedeExportar(toBoolean(row[13]))
                    .build();

            ModuloUsuarioDTO dto = ModuloUsuarioDTO.builder()
                    .id(id)
                    .nombre(nombre)
                    .codigo(codigo)
                    .recurso(recurso)
                    .componentKey(componentKey)
                    .pathImg(pathImg)
                    .moduloPadreId(moduloPadreId)
                    .permisos(permisos)
                    .subModulos(new ArrayList<>())
                    .build();

            mapModulos.put(id, dto);
        }

        // 2. Construcción jerárquica en árbol (Padres e Hijos)
        for (ModuloUsuarioDTO modulo : mapModulos.values()) {
            if (modulo.getModuloPadreId() == null) {
                modulosRaiz.add(modulo);
            } else {
                ModuloUsuarioDTO padre = mapModulos.get(modulo.getModuloPadreId());
                if (padre != null) {
                    padre.getSubModulos().add(modulo);
                } else {
                    // Si el padre no está mapeado para este rol, se expone en la raíz
                    modulosRaiz.add(modulo);
                }
            }
        }

        return modulosRaiz;
    }

    private boolean toBoolean(Object val) {
        if (val == null) return false;
        if (val instanceof Number) return ((Number) val).intValue() == 1;
        if (val instanceof Boolean) return (Boolean) val;
        return false;
    }
}
