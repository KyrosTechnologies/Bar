package com.kyros.technologies.bar.Common.activity.model;

import java.util.ArrayList;

/**
 * Created by Thirunavukkarasu on 19-06-2017.
 */

public class UserManagementModel {
    private String UserName;
    private String UserEmail;
    private String UserRole;
    private int Id;
    private ArrayList<BarAccess>barAccess=new ArrayList<BarAccess>();
    public UserManagementModel(){}

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public String getUserRole() {
        return UserRole;
    }

    public void setUserRole(String userRole) {
        UserRole = userRole;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public ArrayList<BarAccess> getBarAccess() {
        return barAccess;
    }

    public void setBarAccess(ArrayList<BarAccess> barAccess) {
        this.barAccess = barAccess;
    }
}
