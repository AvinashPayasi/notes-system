package com.notes.system.api.dto;

import com.notes.system.api.entity.Notes;

import java.sql.Timestamp;

public class NoteResponse {
    private int noteId;
    private String title;
    private String note;
    private boolean pinned;
    private Timestamp createdAt;

    public NoteResponse(Notes notes){
        this.noteId=notes.getNotesId();
        this.title=notes.getTitle();
        this.note=notes.getNote();
        this.pinned=notes.isPinned();
        this.createdAt=notes.getCreatedAt();
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
