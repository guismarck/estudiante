package app.estudiante.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class recurosNoEncontradoException extends RuntimeException {
   public recurosNoEncontradoException(String mensaje){
       super(mensaje);
   }
}
