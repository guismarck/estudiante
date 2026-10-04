package app.estudiante.modelo;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@SuperBuilder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true, exclude = {"roles"})
@EqualsAndHashCode(callSuper = true, exclude = {"roles"})
@Table(name = "sec_usuarios")
public class Usuario extends AuditableEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idpersona", referencedColumnName = "idpersona", nullable = false)
    private Persona idPersona;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Column(nullable = false)
    private Short estado; // 1: Activo, 0: Inactivo, 2: Bloqueado

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "sec_roles_usuario",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    @Builder.Default
    private Set<Rol> roles = new HashSet<>();
}
