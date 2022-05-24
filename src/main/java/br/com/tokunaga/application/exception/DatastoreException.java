package br.com.tokunaga.application.exception;

public class DatastoreException extends RuntimeException {
    public DatastoreException(String message) {
        super(message);
    }

    public DatastoreException(String message, Throwable ex) {
        super(message, ex);
    }
}
