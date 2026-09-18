package app.estudiante.modelo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "estudiante")
@PrimaryKeyJoinColumn(name = "idpersona")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Estudiante extends Persona {

    @Column(name = "cod_estudiante", nullable = false, unique = true, length = 45)
    private String codEstudiante;

    @Column(name = "codigo_MINED", unique = true, length = 50)
    private String codigoMined;

    @Column(name = "estado", nullable = false)
    private Boolean estado = true;
}