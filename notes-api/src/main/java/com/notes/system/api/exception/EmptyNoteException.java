package com.notes.system.api.exception;

public class EmptyNoteException extends RuntimeException {
    public EmptyNoteException(String message) {
        super(message);
    }
}
