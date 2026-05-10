package com.notes.system.api.entity;

import com.notes.system.api.entity.enums.NotesState;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.type.PostgreSQLEnumJdbcType;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name="notes")
public class Notes {
    @Column(name="user_id")
    private UUID userId;
    @Id
    @Column(name = "note_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int noteId;
    private String title;
    private String note;
    @Column(name="created_at", updatable = false, insertable = false)
    private Timestamp createdAt;
    private boolean pinned;
    @Enumerated(EnumType.STRING)
    @JdbcType(value = PostgreSQLEnumJdbcType.class)
    private NotesState state=NotesState.ACTIVE;

    protected Notes(){}

    public Notes(UUID userId, String title, String note){
        this.userId=userId;
        this.title=title;
        this.note=note;
    }

    public int getNotesId(){
        return noteId;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title=title;
    }

    public String getNote(){
        return note;
    }

    public boolean isPinned(){
        return pinned;
    }

    public void setPinned(boolean pinned){
        this.pinned=pinned;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public NotesState getState(){
        return state;
    }

    public void setState(NotesState state){
        this.state=state;
    }

}