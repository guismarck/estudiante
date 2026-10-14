package app.estudiante.servicio.InterfacesServicios;

import app.estudiante.modelo.Tutor;

import java.util.List;

public interface ITutorServicio{
    List<Tutor> ListarTutor();
    List<Tutor> busquedaGeneral(String search);
    Tutor buscarTutorPorId(Integer idTutor);
    void guardarTutor (Tutor tutor);
    void eliminarTutor(Tutor tutor);



}
