package app.estudiante.modelo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "estudiante")
@PrimaryKeyJoinColumn(name = "idpersona")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Estudiante extends Persona {


    @Column(name = "cod_estudiante", nullable = false, unique = true, length = 45)
    private String codEstudiante;

    @Column(name = "codigo_MINED", unique = true, length = 50)
    private String codigoMined;

    @Column(name = "estado", nullable = false)
    private Boolean estado = true;

    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("persona")
    private List<EstudianteTutor> tutores = new ArrayList<>();

}