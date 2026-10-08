package app.estudiante.servicio.InterfacesServicios;

import app.estudiante.modelo.Docente;


import java.util.List;

public interface IDocenteServicio {
      List<Docente>ListarDocente();
    List<Docente> busquedaGeneral(String search);
     Docente buscarDocentePorId(Integer idDocente);
     void guardarDocente (Docente docente);
      void eliminarDocente(Docente docente);
}
