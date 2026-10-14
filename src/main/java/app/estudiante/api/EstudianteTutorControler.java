package app.estudiante.api;

import app.estudiante.modelo.EstudianteTutor;

import app.estudiante.modelo.EstudianteTutorId;
import app.estudiante.servicio.InterfacesServicios.IEstudianteTutorServicio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("estudiante-app")
@CrossOrigin(value = "http://localhost:3000")

public class EstudianteTutorControler {
    Logger logger = LoggerFactory.getLogger(EstudianteTutorControler.class);//Infomacion de la consola


    @Autowired
    private IEstudianteTutorServicio estudianteTutorServicio;

    //http://localhost:8080/estudiante-app/estudiantes
    @GetMapping(path = "/estudianteTutores")
    public List<EstudianteTutor>obternerTutores(@RequestParam(value = "search",required = false) String search){
        if (search == null || search.trim().isEmpty())
            return  estudianteTutorServicio.listarEstudianteTutor();
        return estudianteTutorServicio.busquedaGeneral(search);
    }

    @PostMapping(path = "/estudianteTutores/create") // agregar tutores-estudiantes
    public ResponseEntity agregarEstudiantesTutores(@RequestBody EstudianteTutor estudianteTutor){
        logger.info("el estudiante al guardar " + estudianteTutor);
        try {
            estudianteTutorServicio.guardarEstudianteTutor(estudianteTutor);
            return  ResponseEntity.ok("OK");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    // Buscar una relación por la clave compuesta
// GET /estudiante-app/estudianteTutores/10/25
    @GetMapping("/estudianteTutores/{idEstudiante}/{idTutor}")
    public ResponseEntity<?> buscarPorId(
            @PathVariable Integer idEstudiante,
            @PathVariable Integer idTutor) {

        EstudianteTutorId id =
                new EstudianteTutorId(idEstudiante, idTutor);

        EstudianteTutor relacion =
                estudianteTutorServicio.buscarEstudianteTutorPorId(id);

        if (relacion == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró la relación estudiante-tutor.");
        }

        return ResponseEntity.ok(relacion);
    }


    // Actualizar una relación existente
// PUT /estudiante-app/estudianteTutores/10/25
    @PutMapping("/estudianteTutores/{idEstudiante}/{idTutor}")
    public ResponseEntity<?> actualizarEstudianteTutor(
            @PathVariable Integer idEstudiante,
            @PathVariable Integer idTutor,
            @RequestBody EstudianteTutor datos) {

        try {
            EstudianteTutorId id =
                    new EstudianteTutorId(idEstudiante, idTutor);

            EstudianteTutor existente =
                    estudianteTutorServicio.buscarEstudianteTutorPorId(id);

            if (existente == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No se encontró la relación que desea actualizar.");
            }

            // Actualizar solamente los campos permitidos
            if (datos.getParentesco() != null
                    && !datos.getParentesco().trim().isEmpty()) {
                existente.setParentesco(datos.getParentesco().trim());
            }

            if (datos.getEstado() != null) {
                existente.setEstado(datos.getEstado());
            }

            if (datos.getActualizadoPor() != null) {
                existente.setActualizadoPor(datos.getActualizadoPor());
            }

            estudianteTutorServicio.guardarEstudianteTutor(existente);

            return ResponseEntity.ok(
                    "Relación estudiante-tutor actualizada correctamente.");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        } catch (Exception e) {
            logger.error("Error al actualizar la relación estudiante-tutor", e);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("No fue posible actualizar la relación.");
        }
    }


    // Eliminar una relación por su clave compuesta
// DELETE /estudiante-app/estudianteTutores/10/25
    @DeleteMapping("/estudianteTutores/{idEstudiante}/{idTutor}")
    public ResponseEntity<?> eliminarEstudianteTutor(
            @PathVariable Integer idEstudiante,
            @PathVariable Integer idTutor) {

        try {
            EstudianteTutorId id =
                    new EstudianteTutorId(idEstudiante, idTutor);

            EstudianteTutor existente =
                    estudianteTutorServicio.buscarEstudianteTutorPorId(id);

            if (existente == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No se encontró la relación que desea eliminar.");
            }

            estudianteTutorServicio.eliminarEstudianteTutor(existente);

            return ResponseEntity.noContent().build();

        } catch (Exception e) {
            logger.error("Error al eliminar la relación estudiante-tutor", e);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("No fue posible eliminar la relación.");
        }
    }

}
