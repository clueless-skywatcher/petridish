package io.duskmare.petridish.exceptions;

public class InvalidRespCommandException extends RuntimeException {
    public InvalidRespCommandException(String message) {
        super(message);
    }
}
