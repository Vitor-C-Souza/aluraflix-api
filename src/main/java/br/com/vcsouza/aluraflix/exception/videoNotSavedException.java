package br.com.vcsouza.aluraflix.exception;

public class videoNotSavedException extends RuntimeException {
    private final String message;

    public videoNotSavedException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
