package com.notes.system.api.dto;

import java.sql.Timestamp;

public class NoteResponseDTO {
    private int noteId;
    private String title;
    private String note;
    private boolean pinned;
    private Timestamp createdAt;

    public NoteResponseDTO(int noteId, String title, String note, boolean pinned, Timestamp createdAt){
        this.noteId=noteId;
        this.title=title;
        this.note=note;
        this.pinned=pinned;
        this.createdAt=createdAt;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isPinned() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
