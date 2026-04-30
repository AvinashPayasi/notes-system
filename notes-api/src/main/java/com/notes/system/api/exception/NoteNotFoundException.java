package com.notes.system.api.exception;

public class NoteNotFoundException extends RuntimeException  {
    public NoteNotFoundException(String message) {
        super(message);
    }
}
