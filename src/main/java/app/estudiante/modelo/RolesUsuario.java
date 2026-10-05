package app.estudiante.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "sec_roles_usuario")
public class RolesUsuario {

    @EmbeddedId
    private RolesUsuarioId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("usuarioId")
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("rolId")
    @JoinColumn(name = "rol_id")
    private Rol rol;

    @Column(name = "creado_por", length = 50)
    private String creadoPor;

    @Column(name = "creado_el", nullable = false, updatable = false)
    private LocalDateTime creadoEl;

    @Column(name = "actualizado_por", length = 50)
    private String actualizadoPor;

    @Column(name = "actualizado_el")
    private LocalDateTime actualizadoEl;

    public RolesUsuario() {}

    public RolesUsuario(Usuario usuario, Rol rol) {
        this.usuario = usuario;
        this.rol = rol;
        this.id = new RolesUsuarioId(usuario.getId(), Integer.parseInt(rol.getId().toString()));
    }

    @PrePersist
    public void prePersist() {
        this.creadoEl = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.actualizadoEl = LocalDateTime.now();
    }

}