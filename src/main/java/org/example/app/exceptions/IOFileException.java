package org.example.app.exceptions;

public class IOFileException extends Exception {
    private String message;

    public IOFileException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
