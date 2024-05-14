package br.com.vcsouza.aluraflix.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class Exceptions {

    @ExceptionHandler(videoNotFoundExceptio.class)
    public ResponseEntity videoNotFound(){
        return ResponseEntity.notFound().build();
    }
}
