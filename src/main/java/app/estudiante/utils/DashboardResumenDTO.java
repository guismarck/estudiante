
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

    // Estados de los estudiantes
    private long activos;
    private long inactivos;

    // Matrículas del año lectivo
    private long matriculados;

    public DashboardResumenDTO(
            long estudiantes,
            long docentes,
            long grados,
            long salones,
            long tutores,
            long asignaturas,
            long activos,
            long inactivos,
            long matriculados) {

        this.estudiantes = estudiantes;
        this.docentes = docentes;
        this.salones = salones;
        this.tutores = tutores;
        this.grados = grados;
        this.asignaturas = asignaturas;
        this.activos = activos;
        this.inactivos = inactivos;
        this.matriculados = matriculados;
    }
}
