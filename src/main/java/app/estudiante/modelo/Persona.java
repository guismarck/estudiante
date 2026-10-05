package app.estudiante.modelo;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Table(
        name = "persona",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_persona_cedula", columnNames = {"cedula"})
        },
        indexes = {
                @Index(name = "idx_persona_apellidos", columnList = "apellido_completo, nombre_completo")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Persona extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpersona", nullable = false, updatable = false)
    private Integer idPersona;

    @Column(name = "nombre_completo", nullable = false, length = 150)
    private String nombreCompleto;

    @Column(name = "apellido_completo", nullable = false, length = 150)
    private String apellidoCompleto;

    @Column(name = "sexo", nullable = false, length = 20)
    private String sexo;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(name = "cedula", length = 20)
    private String cedula;

    @Column(name = "partida_nacimiento", length = 30)
    private String partidaNacimiento;

    @Column(name = "direccion", nullable = false, length = 300)
    private String direccion;

    @Column(name = "correo" , nullable = false)
    private String correo;
}
