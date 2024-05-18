package br.com.vcsouza.aluraflix.exception;


public class NullCategoriaCreatedException extends RuntimeException {
    private final String message;
    public NullCategoriaCreatedException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
