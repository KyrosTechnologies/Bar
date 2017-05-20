package com.kyros.technologies.bar.utils;

/**
 * Created by Rohin on 18-05-2017.
 */

public class MyBar {

    private int userprofileid;
    private String barname;
    private int id;
    private String datecreated;
    private String datemodified;

    public MyBar(){

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

    public int getid() {
        return id;
    }

    public void setid(int barid) {
        this.id = barid;
    }

    public String getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(String datecreated) {
        this.datecreated = datecreated;
    }

    public String getDatemodified() {
        return datemodified;
    }

    public void setDatemodified(String datemodified) {
        this.datemodified = datemodified;
    }
}
