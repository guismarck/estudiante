package app.estudiante.modelo;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;
import jakarta.validation.constraints.NotBlank;

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


    @NotBlank(message = "El código del estudiante es obligatorio")
    @JsonAlias("cod_estudiante")
    @Column(name = "cod_estudiante", nullable = false, unique = true, length = 45)
    private String codEstudiante;

    @Column(name = "codigo_MINED", unique = true, length = 50)
    private String codigoMined;

    @Column(name = "estado", nullable = false)
    private Boolean estado = true;
}