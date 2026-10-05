package app.estudiante.servicio.Servicios;

import app.estudiante.modelo.Modulo;
import app.estudiante.modelo.PermisoRol;
import app.estudiante.modelo.Usuario;
import app.estudiante.repositorio.ModuloRepository;
import app.estudiante.repositorio.PermisoRolRepository;
import app.estudiante.repositorio.UsuarioRepositorio;
import app.estudiante.servicio.InterfacesServicios.IModuloService;
import app.estudiante.utils.RecursoNoEncontradoException;
import app.estudiante.utils.seguridad.ModuloMenuDTO;
import app.estudiante.utils.seguridad.PermisoModuloDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ModuloServiceImpl implements IModuloService {

    private final ModuloRepository moduloRepository;
    private final UsuarioRepositorio usuarioRepository;
    private final PermisoRolRepository permisoRolRepository;

    public ModuloServiceImpl(ModuloRepository moduloRepository,
                              UsuarioRepositorio usuarioRepository,
                              PermisoRolRepository permisoRolRepository) {
        this.moduloRepository = moduloRepository;
        this.usuarioRepository = usuarioRepository;
        this.permisoRolRepository = permisoRolRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ModuloMenuDTO> obtenerMenuPorUsuario(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado: " + username));

        List<Modulo> modulosAutorizados = moduloRepository.findModulosAutorizadosPorUsuario(usuario.getId());
        List<PermisoRol> permisos = permisoRolRepository.findPermisosByUsuarioId(usuario.getId());

        Map<Long, PermisoModuloDTO> mapaPermisos = consolidarPermisosPorModulo(permisos);

        // Construcción de Nodos del Árbol de Menús
        Map<Long, ModuloMenuDTO> dtoMap = new HashMap<>();
        for (Modulo m : modulosAutorizados) {
            ModuloMenuDTO dto = ModuloMenuDTO.builder()
                    .id(m.getId())
                    .nombre(m.getNombre())
                    .codigo(m.getCodigo())
                    .recurso(m.getRecurso())
                    .componentKey(m.getComponentKey())
                    .pathImg(m.getPathImg())
                    .orden(m.getOrden())
                    .permisos(mapaPermisos.getOrDefault(m.getId(), new PermisoModuloDTO()))
                    .submodulos(new ArrayList<>())
                    .build();
            dtoMap.put(m.getId(), dto);
        }

        List<ModuloMenuDTO> arbolMenu = new ArrayList<>();
        for (Modulo m : modulosAutorizados) {
            ModuloMenuDTO dtoActual = dtoMap.get(m.getId());
            if (m.getModuloPadre() == null) {
                arbolMenu.add(dtoActual);
            } else if (dtoMap.containsKey(m.getModuloPadre().getId())) {
                dtoMap.get(m.getModuloPadre().getId()).getSubmodulos().add(dtoActual);
            }
        }

        arbolMenu.sort(Comparator.comparingInt(ModuloMenuDTO::getOrden));
        return arbolMenu;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ModuloMenuDTO> obtenerRutasPlanasPorUsuario(String username) {
        List<ModuloMenuDTO> arbol = obtenerMenuPorUsuario(username);
        List<ModuloMenuDTO> listaPlana = new ArrayList<>();
        aplanarArbol(arbol, listaPlana);
        return listaPlana;
    }

    private void aplanarArbol(List<ModuloMenuDTO> nodos, List<ModuloMenuDTO> destino) {
        for (ModuloMenuDTO nodo : nodos) {
            if (nodo.getRecurso() != null && nodo.getComponentKey() != null) {
                destino.add(nodo);
            }
            if (nodo.getSubmodulos() != null && !nodo.getSubmodulos().isEmpty()) {
                aplanarArbol(nodo.getSubmodulos(), destino);
            }
        }
    }

    private Map<Long, PermisoModuloDTO> consolidarPermisosPorModulo(List<PermisoRol> permisos) {
        Map<Long, PermisoModuloDTO> mapa = new HashMap<>();
        for (PermisoRol p : permisos) {
            Long moduloId = p.getModulo().getId();
            PermisoModuloDTO dto = mapa.computeIfAbsent(moduloId, k -> PermisoModuloDTO.builder()
                    .moduloCodigo(p.getModulo().getCodigo())
                    .recurso(p.getModulo().getRecurso())
                    .componentKey(p.getModulo().getComponentKey())
                    .buscar(false).agregar(false).modificar(false)
                    .inactivar(false).procesar(false).guardar(false).exportar(false)
                    .build());

            // Merge lógico OR si el usuario posee múltiples roles
            dto.setBuscar(dto.getBuscar() || Boolean.TRUE.equals(p.getPuedeBuscar()));
            dto.setAgregar(dto.getAgregar() || Boolean.TRUE.equals(p.getPuedeAgregar()));
            dto.setModificar(dto.getModificar() || Boolean.TRUE.equals(p.getPuedeModificar()));
            dto.setInactivar(dto.getInactivar() || Boolean.TRUE.equals(p.getPuedeInactivar()));
            dto.setProcesar(dto.getProcesar() || Boolean.TRUE.equals(p.getPuedeProcesar()));
            dto.setGuardar(dto.getGuardar() || Boolean.TRUE.equals(p.getPuedeGuardar()));
            dto.setExportar(dto.getExportar() || Boolean.TRUE.equals(p.getPuedeExportar()));
        }
        return mapa;
    }
}
