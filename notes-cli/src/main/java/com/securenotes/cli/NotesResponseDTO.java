package com.securenotes.cli;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NotesResponseDTO {
    @JsonProperty("isPinned")
    private boolean pinned;
    private String notes;
    private int notesId;
    private String title;


    public int getNotesId() {
        return notesId;
    }

    public void setNotesId(int notesId) {
        this.notesId = notesId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isPinned() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }
}
