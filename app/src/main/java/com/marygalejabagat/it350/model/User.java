package com.marygalejabagat.it350.model;

public class User {
    private int userID, roleID;
    private String firstName, lastName, email;
    private String roleName;

    public User(){}
    public User(int userID, int roleID, String fname, String lname, String email, String roleName){
        this.userID = userID;
        this.roleID = roleID;
        this.firstName = fname;
        this.lastName = lname;
        this.email = email;
        this.roleName = roleName;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
