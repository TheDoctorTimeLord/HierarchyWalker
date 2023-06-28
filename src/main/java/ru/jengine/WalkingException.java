package ru.jengine;

public class WalkingException extends RuntimeException {
    public WalkingException(String message) {
        super(message);
    }

    public WalkingException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
