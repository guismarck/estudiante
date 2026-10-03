package app.estudiante.servicio.InterfacesServicios;

import app.estudiante.modelo.Matricula;
import app.estudiante.utils.MatriculaRequestDTO;
import app.estudiante.utils.MatriculaResponseDTO;

import java.util.List;

public interface IMatriculaServicio {
   public  List<Matricula> ListarMatricula();
    public List<Matricula> busquedaGeneral(String search);
    public Matricula buscarMatriculaPorId(Integer idmatricula);
    public  void guardarMatricula(Matricula matricula);
    public void eliminarMatricula(Matricula matricula);
    public MatriculaResponseDTO registrarMatriculaSinPago(MatriculaRequestDTO dto);
}
