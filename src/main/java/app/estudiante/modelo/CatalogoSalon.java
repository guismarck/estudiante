package app.estudiante.modelo;


import lombok.*;

import jakarta.persistence.*;

@Builder
@Entity
@Data //get a set
@AllArgsConstructor//lleno
@NoArgsConstructor
@ToString
@Table (name ="catalogo_salon")
@EqualsAndHashCode(callSuper=true)
public class CatalogoSalon extends AuditableEntity{
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcatalogo_salon", nullable = false, updatable = false)
    private Integer idCatalogoSalon;

    @Column(name = "nombre_salon", nullable = false, length = 50)
    private String nombreSalon;


    @Column(name = "capacidad", nullable = false)
    @Builder.Default
    private Integer capacidad = 40;
}
