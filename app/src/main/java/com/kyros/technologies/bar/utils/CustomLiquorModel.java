package com.kyros.technologies.bar.utils;

/**
 * Created by Thirunavukkarasu on 23-05-2017.
 */

public class CustomLiquorModel {
    private int userprofileid;
    private int barid;
    private  int sectionid;
    private String liquorname;
    private String liquorcapacity;
    private String shots;
    private String category;
    private String subcategory;
    private String parlevel;
    private String distributorname;
    private String priceunit;
    private String binnumber;
    private String productcode;
    private double MinValue;
    private double MaxValue;

    public CustomLiquorModel(){

    }

    public int getUserprofileid() {
        return userprofileid;
    }

    public void setUserprofileid(int userprofileid) {
        this.userprofileid = userprofileid;
    }

    public int getBarid() {
        return barid;
    }

    public void setBarid(int barid) {
        this.barid = barid;
    }

    public int getSectionid() {
        return sectionid;
    }

    public void setSectionid(int sectionid) {
        this.sectionid = sectionid;
    }

    public String getLiquorname() {
        return liquorname;
    }

    public void setLiquorname(String liquorname) {
        this.liquorname = liquorname;
    }

    public String getLiquorcapacity() {
        return liquorcapacity;
    }

    public void setLiquorcapacity(String liquorcapacity) {
        this.liquorcapacity = liquorcapacity;
    }

    public String getShots() {
        return shots;
    }

    public void setShots(String shots) {
        this.shots = shots;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getParlevel() {
        return parlevel;
    }

    public void setParlevel(String parlevel) {
        this.parlevel = parlevel;
    }

    public String getDistributorname() {
        return distributorname;
    }

    public void setDistributorname(String distributorname) {
        this.distributorname = distributorname;
    }

    public String getPriceunit() {
        return priceunit;
    }

    public void setPriceunit(String priceunit) {
        this.priceunit = priceunit;
    }

    public String getBinnumber() {
        return binnumber;
    }

    public void setBinnumber(String binnumber) {
        this.binnumber = binnumber;
    }

    public String getProductcode() {
        return productcode;
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode;
    }

    public double getMinValue() {
        return MinValue;
    }

    public void setMinValue(double minValue) {
        MinValue = minValue;
    }

    public double getMaxValue() {
        return MaxValue;
    }

    public void setMaxValue(double maxValue) {
        MaxValue = maxValue;
    }
}
