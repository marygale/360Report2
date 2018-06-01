package com.marygalejabagat.it350.model;

import java.util.Date;

public class Surveys {
    private int id, status, user_id, email_verification_on, open;
    private String name, description, password, is_deleted;
    private String created, modified;

    public Surveys(){}

    public Surveys(int id, String name, String description, String password, int status, int verification, int user_id,
                   int open, String created, String modified){
        this.id = id;
        this.name = name;
        this.description = description;
        this.password = password;
        this.status = status;
        this.email_verification_on = verification;
        this.user_id = user_id;
        this.open = open;
        this.created = created;
        this.modified = modified;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getEmail_verification_on() {
        return email_verification_on;
    }

    public void setEmail_verification_on(int email_verification_on) {
        this.email_verification_on = email_verification_on;
    }

    public int getOpen() {
        return open;
    }

    public void setOpen(int open) {
        this.open = open;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(String is_deleted) {
        this.is_deleted = is_deleted;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }
}
