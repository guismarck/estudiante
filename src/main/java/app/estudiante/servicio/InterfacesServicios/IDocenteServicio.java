package app.estudiante.servicio.InterfacesServicios;

import app.estudiante.modelo.Docente;

import java.util.List;

public interface IDocenteServicio {
    public List<Docente>ListarDocent();
    public Docente buscarDocentePorId(Integer idDocente);
    public void guardarDocente (Docente docente);
    public  void eliminarDocente(Docente docente);
}
