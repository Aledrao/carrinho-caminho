package br.com.asas.carrinhoCaminho.exception;

public class DepartamentoException extends Exception {

    public DepartamentoException(String message) {
        super(message);
    }

    public DepartamentoException(String message, Throwable cause) {
        super(message, cause);
    }
}
