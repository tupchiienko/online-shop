package org.cursor.accountservice.exception;

public class JwtTokenMissingException extends RuntimeException {

    public JwtTokenMissingException(String message) {
        super(message);
    }
}
