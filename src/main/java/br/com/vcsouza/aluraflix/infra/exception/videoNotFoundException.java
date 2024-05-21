package br.com.vcsouza.aluraflix.infra.exception;

public class videoNotFoundException extends RuntimeException {
    private final String message;
    public videoNotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
