package app.estudiante.modelo;

import app.estudiante.modelo.Estudiante;
import app.estudiante.modelo.EstudianteTutorId;
import app.estudiante.modelo.Tutor;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "estudiante_tutor")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"tutor", "persona"})
public class EstudianteTutor {

    @EmbeddedId
    private EstudianteTutorId id = new EstudianteTutorId();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("idtutor")
    @JoinColumn(name = "idtutor", referencedColumnName = "idpersona", nullable = false)
    private Tutor tutor;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("idpersonaEstudiante")
    @JoinColumn(name = "idpersona_estudiante", referencedColumnName = "idpersona", nullable = false)
    private Estudiante persona;

    @Column(name = "parentesco", nullable = false, length = 30)
    private String parentesco;

    @Column(name = "es_representante_legal", nullable = false)
    private Boolean estado = false;

    @Column(name = "creado_por", length = 50)
    private String creadoPor;

    @Column(name = "actualizado_por", length = 50)
    private String actualizadoPor;
}