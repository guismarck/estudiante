package app.estudiante.utils;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NivelEducativoResponseDTO {

    private Integer idnivel;
    private String nombre;
    private String descripcion;
    private Boolean activo;
    private String creadoPor;
    private LocalDateTime creadoEl;
    private String actualizadoPor;
    private LocalDateTime actualizadoEl;
}