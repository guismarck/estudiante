package app.estudiante.modelo;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Entity
@Data //get a set
@AllArgsConstructor//lleno
@ToString
@Table (name ="catalogo_salon")
public class CatalogoSalon extends AuditableEntity{
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcatalogo_salon", nullable = false, updatable = false)
    private Long idCatalogoSalon;

    @NotBlank(message = "El nombre del salón es obligatorio")
    @Size(max = 50, message = "El nombre del salón no debe exceder los 50 caracteres")
    @Column(name = "nombre_salon", nullable = false, length = 50)
    private String nombreSalon;


    @Min(value = 1, message = "La capacidad debe ser mayor a 0")
    @Column(name = "capacidad", nullable = false)
    @Builder.Default
    private Integer capacidad = 40;
}
