package com.kyros.technologies.bar.utils;

/**
 * Created by Rohin on 06-06-2017.
 */

public class UserDetail {

    private int id;
    private int userprofileid;
    private String barname;
    private String createdon;
    private String modifiedon;

    public UserDetail(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserprofileid() {
        return userprofileid;
    }

    public void setUserprofileid(int userprofileid) {
        this.userprofileid = userprofileid;
    }

    public String getBarname() {
        return barname;
    }

    public void setBarname(String barname) {
        this.barname = barname;
    }

    public String getCreatedon() {
        return createdon;
    }

    public void setCreatedon(String createdon) {
        this.createdon = createdon;
    }

    public String getModifiedon() {
        return modifiedon;
    }

    public void setModifiedon(String modifiedon) {
        this.modifiedon = modifiedon;
    }

    private static final UserDetail holder=new UserDetail();
    public static UserDetail getHolder(){
        return holder;
    }
}
