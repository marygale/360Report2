package com.marygalejabagat.it350.model;


import java.util.Date;

public class Dimension {
    private int id;
    private String name;
    private String description;
    private String icon;
    private int is_deleted;
    private Date created;
    private Date modified;

    public Dimension(){

    }

    public Dimension(int id, String name, String desc, String icon, int deleted, Date created, Date modified){
        this.id = id;
        this.name = name;
        this.description = desc;
        this.icon = icon;
        this.is_deleted = deleted;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(int is_deleted) {
        this.is_deleted = is_deleted;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

}
