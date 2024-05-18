package br.com.vcsouza.aluraflix.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class Exceptions {

    @ExceptionHandler(videoNotFoundException.class)
    public ResponseEntity videoNotFound() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity notFoundDatabase() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(categoriaNotFoundException.class)
    public ResponseEntity categoriaNotFound() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(NullCategoriaCreatedException.class)
    public ResponseEntity categoriaCreateNull(NullCategoriaCreatedException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(videoNotSavedException.class)
    public ResponseEntity videoNotSaved(videoNotSavedException exception){
        return ResponseEntity.badRequest().body(exception);
    }
}
