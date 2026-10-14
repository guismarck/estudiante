package app.estudiante.modelo;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class EstudianteTutorId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "idpersona_estudiante")
    private Integer idpersonaEstudiante;

    @Column(name = "idtutor")
    private Integer idtutor;

}
