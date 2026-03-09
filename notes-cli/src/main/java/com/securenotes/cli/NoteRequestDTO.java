package com.securenotes.cli;

public class NoteRequestDTO {

    private String title;
    private String notes;

    public NoteRequestDTO(String title, String notes) {
        this.title = title;
        this.notes = notes;
    }

    public String getTitle() {
        return title;
    }

    public String getNotes() {
        return notes;
    }
}
