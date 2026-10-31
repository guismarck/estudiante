package app.estudiante.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DashboardResumenDTO {

    private long estudiantes;
    private long docentes;
    private long tutores;
    private long grados;
    private long salones;
    private long asignaturas;
    private long matriculados;

    public DashboardResumenDTO(
            long estudiantes,
            long docentes,
            long grados,
            long salones,
            long tutores,
            long asignaturas,
            long matriculados) {

        this.estudiantes = estudiantes;
        this.docentes = docentes;
        this.grados = grados;
        this.salones = salones;
        this.tutores = tutores;
        this.asignaturas = asignaturas;
        this.matriculados = matriculados;
    }
}