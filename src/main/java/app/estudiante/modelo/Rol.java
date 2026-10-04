package app.estudiante.modelo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "sec_roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rol extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    @Column(nullable = false, unique = true, length = 32)
    private String codigo;

    @Column(nullable = false, length = 60)
    private String descripcion;
}