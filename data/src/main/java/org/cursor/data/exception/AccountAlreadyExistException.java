package org.cursor.data.exception;

public class AccountAlreadyExistException extends RuntimeException {

    public AccountAlreadyExistException(String message) {
        super(message);
    }
}
