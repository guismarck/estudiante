package app.estudiante.modelo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "sec_usuarios")
@Builder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "roles")
@EqualsAndHashCode(exclude = "roles")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "estado")
    private Integer estado; // 1 = ACTIVO, 0 = INACTIVO

    @Column(name = "idpersona")
    private Long idpersona;

    @Column(name = "creado_el")
    private LocalDateTime creadoEl;

    @Column(name = "creado_por")
    private String creadoPor;

    @Column(name = "actualizado_el")
    private LocalDateTime actualizadoEl;

    @Column(name = "actualizado_por")
    private String actualizadoPor;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "sec_roles_usuario",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    @JsonIgnoreProperties("usuarios")
    private Set<Rol> roles;

    public boolean isActivo() {
        return this.estado != null && this.estado == 1;
    }
}