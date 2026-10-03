package app.estudiante.modelo;


import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import jakarta.persistence.*;

@Builder
@Entity
@Data //get a set
@AllArgsConstructor//lleno
@NoArgsConstructor
@ToString
@Table (name ="catalogo_salon")
public class CatalogoSalon extends AuditableEntity{
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcatalogo_salon", nullable = false, updatable = false)
    private Long idCatalogoSalon;

    @NotBlank(message = "El nombre del salón es obligatorio")
    @Column(name = "nombre_salon", nullable = false, length = 50)
    private String nombreSalon;


    @Column(name = "capacidad", nullable = false)
    @Builder.Default
    private Integer capacidad = 40;
}
