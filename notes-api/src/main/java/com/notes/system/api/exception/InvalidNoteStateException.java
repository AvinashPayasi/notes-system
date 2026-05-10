package com.notes.system.api.exception;

public class InvalidNoteStateException extends RuntimeException {
    public InvalidNoteStateException(String message) {
        super(message);
    }
}
