package com.kyros.technologies.bar.utils;

/**
 * Created by Rohin on 18-05-2017.
 */

public class MySection {

    private int userprofile;
    private String sectionname;
    private int sectionid;
    private int barid;
    private String sectioncreated;
    private String sectionmodified;

    public MySection(){

    }

    public int getUserprofile() {
        return userprofile;
    }

    public int getBarid() {
        return barid;
    }

    public void setBarid(int barid) {
        this.barid = barid;
    }

    public void setUserprofile(int userprofile) {
        this.userprofile = userprofile;
    }

    public String getSectionname() {
        return sectionname;
    }

    public void setSectionname(String sectionname) {
        this.sectionname = sectionname;
    }

    public int getSectionid() {
        return sectionid;
    }

    public void setSectionid(int sectionid) {
        this.sectionid = sectionid;
    }

    public String getSectioncreated() {
        return sectioncreated;
    }

    public void setSectioncreated(String sectioncreated) {
        this.sectioncreated = sectioncreated;
    }

    public String getSectionmodified() {
        return sectionmodified;
    }

    public void setSectionmodified(String sectionmodified) {
        this.sectionmodified = sectionmodified;
    }
}
