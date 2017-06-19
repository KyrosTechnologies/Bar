package com.kyros.technologies.bar.Common.activity.model;

/**
 * Created by Thirunavukkarasu on 19-06-2017.
 */

public class BarAccess {
    private int BarId;
    private String BarName;
    private String CreatedOn;
    private String ModifiedOn;
    private int UserManagementId;
    private String UserProfileId;
    private String Id;
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

    public int getUserManagementId() {
        return UserManagementId;
    }

    public void setUserManagementId(int userManagementId) {
        UserManagementId = userManagementId;
    }

    public String getUserProfileId() {
        return UserProfileId;
    }

    public void setUserProfileId(String userProfileId) {
        UserProfileId = userProfileId;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public void setModifiedOn(String modifiedOn) {
        ModifiedOn = modifiedOn;
    }
}
