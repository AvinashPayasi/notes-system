package com.notes.system.api;

import com.notes.system.api.entity.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UsersDetails implements UserDetails {

    private final Users users;

    public UsersDetails(Users users){
        this.users=users;
    }

    public String getUserId(){
        return String.valueOf(users.getUserID());
    }

    @Override
    public String getUsername(){
        return users.getEmail();
    }

    @Override
    public String getPassword(){
        return users.getPassword();
    }

    @Override
    public boolean isEnabled(){
        return users.isEnabled();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return List.of();
    }


}
