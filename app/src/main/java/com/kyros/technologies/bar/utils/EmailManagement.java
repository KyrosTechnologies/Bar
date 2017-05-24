package com.kyros.technologies.bar.utils;

/**
 * Created by Rohin on 20-05-2017.
 */

public class EmailManagement {

    private int id;
    private int userprofile;
    private String useremail;
    private String createdon;

    public EmailManagement(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserprofile() {
        return userprofile;
    }

    public void setUserprofile(int userprofile) {
        this.userprofile = userprofile;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getCreatedon() {
        return createdon;
    }

    public void setCreatedon(String createdon) {
        this.createdon = createdon;
    }
}
