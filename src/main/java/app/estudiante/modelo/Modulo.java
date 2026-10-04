package app.estudiante.modelo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "sec_modulos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Modulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, unique = true, length = 60)
    private String codigo;

    @Column(nullable = false, length = 255)
    private String recurso;

    @Column(name = "path_img", length = 255)
    private String pathImg;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modulo_padre_id")
    private Modulo moduloPadre;

    @Column(nullable = false)
    private Integer orden;

    @Column(nullable = false)
    private Boolean estado;
}