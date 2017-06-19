package com.kyros.technologies.bar.Common.activity.model;

/**
 * Created by Thirunavukkarasu on 19-06-2017.
 */

public class BarAccess {
    private int BarId;
    private String BarName;
    private String CreatedOn;
    private String ModifiedOn;
    public BarAccess(){

    }

    public int getBarId() {
        return BarId;
    }

    public void setBarId(int barId) {
        BarId = barId;
    }

    public String getBarName() {
        return BarName;
    }

    public void setBarName(String barName) {
        BarName = barName;
    }

    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
    }

    public String getModifiedOn() {
        return ModifiedOn;
    }

    public void setModifiedOn(String modifiedOn) {
        ModifiedOn = modifiedOn;
    }
}
