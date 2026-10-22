package app.estudiante.utils;

import app.estudiante.modelo.Estudiante;
import app.estudiante.modelo.EstudianteTutor;
import app.estudiante.modelo.Tutor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class EstudianteRequestDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Estudiante estudiante;
    private List<EstudianteTutorDTO> tutores;
}
