package app.estudiante.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RolesUsuarioId implements Serializable {

    @Column(name = "usuario_id")
    private Long usuarioId;

    @Column(name = "rol_id")
    private Integer rolId;

    public RolesUsuarioId() {}

    public RolesUsuarioId(Long usuarioId, Integer rolId) {
        this.usuarioId = usuarioId;
        this.rolId = rolId;
    }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public Integer getRolId() { return rolId; }
    public void setRolId(Integer rolId) { this.rolId = rolId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RolesUsuarioId that = (RolesUsuarioId) o;
        return Objects.equals(usuarioId, that.usuarioId) && Objects.equals(rolId, that.rolId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuarioId, rolId);
    }
}