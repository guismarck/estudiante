package app.estudiante.modelo;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "estudiante_tutor")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"tutor", "persona"})

public class EstudianteTutor {

    @EmbeddedId
    private   EstudianteTutorId id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idtutor")
    @JoinColumn(
            name = "idtutor",
            referencedColumnName = "idpersona",
            nullable = false
    )
    private Tutor tutor;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idperson_estudiante")
    @JoinColumn(
            name = "idpersona_estudiante",
            referencedColumnName = "idpersona",
            nullable = false
    )
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
