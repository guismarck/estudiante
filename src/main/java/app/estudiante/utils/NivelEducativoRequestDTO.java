package app.estudiante.utils;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NivelEducativoRequestDTO {


    private String nombre;

    private String descripcion;

    private Boolean activo;

    private String usuario;
}
