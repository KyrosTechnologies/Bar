package com.kyros.technologies.bar.utils;

/**
 * Created by Rohin on 24-05-2017.
 */

public class Purchase {

    private int id;
    private int userprofileid;
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
    private String createdon;
    private String small_picture_url;

    public Purchase(){

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
    public String getSmall_picture_url() {
        return small_picture_url;
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

    public String getCreatedon() {
        return createdon;
    }

    public void setCreatedon(String createdon) {
        this.createdon = createdon;
    }
}
