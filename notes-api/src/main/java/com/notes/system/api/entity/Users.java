package com.notes.system.api.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
public class Users {
    @Id
    @Column( name = "user_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userID;
    @Column(columnDefinition = "citext")
    private String email;
    private String password;
    @Column(insertable = false, updatable = false)
    private Instant createdAt;
    private Boolean isEnabled=true;

    protected Users(){}

    public Users(String email, String password){
        this.email=email;
        this.password=password;
    }

    public UUID getUserID(){
        return userID;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isEnabled(){
        return isEnabled;
    }
}
