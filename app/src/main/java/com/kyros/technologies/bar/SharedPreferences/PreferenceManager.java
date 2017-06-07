package com.kyros.technologies.bar.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Thirunavukkarasu on 28-07-2016.
 */
public class PreferenceManager {
    private static PreferenceManager store;
    private final SharedPreferences sp;

    private PreferenceManager(Context context){
        String filename = "login_credentials";
        sp=context.getApplicationContext().getSharedPreferences(filename,0);
    }
    public static PreferenceManager getInstance(Context context){
        if(store==null){
            Log.v("PreferenceManager","NEW STORE");
            store=new PreferenceManager(context);
        }
        return store;
    }
    public String getUserProfileId(){
        return sp.getString("UserProfileId",null);
    }
    public void putUserProfileId(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("UserProfileId", value);
        editor.commit();
    }
    public String getFirstName(){
        return sp.getString("FirstName",null);
    }
    public void putFirstName(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("FirstName", value);
        editor.commit();
    }
    public String getLastName(){
        return sp.getString("LastName",null);
    }
    public void putLastName(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("LastName", value);
        editor.commit();
    }
    public String getUserEmail(){
        return sp.getString("UserEmail",null);
    }
    public void putUserEmail(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("UserEmail", value);
        editor.commit();
    }
    public String getMobileNumber(){
        return sp.getString("MobileNumber",null);
    }
    public void putMobileNumber(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("MobileNumber", value);
        editor.commit();
    }
    public String getVenue(){
        return sp.getString("Venue",null);
    }
    public void putVenue(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("Venue", value);
        editor.commit();
    }
    public String getCountry(){
        return sp.getString("Country",null);
    }
    public void putCountry(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("Country", value);
        editor.commit();
    }
    public String getInventory(){
        return sp.getString("Inventory",null);
    }
    public void putInventory(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("Inventory", value);
        editor.commit();
    }
    public String getInventoryTime(){
        return sp.getString("InventoryTime",null);
    }
    public void putinventoryTime(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("InventoryTime", value);
        editor.commit();
    }
    public boolean getIsactive(){
        return sp.getBoolean("Isactive",false);
    }
    public void putIsactive(boolean value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putBoolean("Isactive", value);
        editor.commit();
    }
    public String getCreate(){
        return sp.getString("Create",null);
    }
    public void putCreate(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("Create", value);
        editor.commit();
    }
    public String getModifi(){
        return sp.getString("Modify",null);
    }
    public void putModifi(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("Modify", value);
        editor.commit();
    }

    public String getIds(){
        return sp.getString("Id",null);
    }
    public void putIds(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("Id", value);
        editor.commit();
    }

    public String getPassword(){
        return sp.getString("Password",null);
    }
    public void putPassword(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("Password", value);
        editor.commit();
    }

    public String getBarName(){
        return sp.getString("BarName",null);
    }
    public void putBarName(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("BarName", value);
        editor.commit();
    }

    public String getBarId(){
        return sp.getString("BarId",null);
    }
    public void putBarId(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("BarId", value);
        editor.commit();
    }

    public String getSectionId(){
        return sp.getString("SectionId",null);
    }
    public void putSectionId(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("SectionId", value);
        editor.commit();
    }

    public String getSectionName(){
        return sp.getString("SectionName",null);
    }
    public void putSectionName(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("SectionName", value);
        editor.commit();
    }


    public String getBarDateCreated(){
        return sp.getString("BarCreated",null);
    }
    public void putBarDateCreated(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("BarCreated", value);
        editor.commit();
    }

    public void clear(){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.clear();
        editor.commit();
    }

    public String getLiquorName(){
        return sp.getString("LiquorName",null);
    }
    public void putLiquorName(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("LiquorName", value);
        editor.commit();
    }

    public String getLiquorCapacity(){
        return sp.getString("LiquorCapacity",null);
    }
    public void putLiquorCapacity(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("LiquorCapacity", value);
        editor.commit();
    }

    public String getShots(){
        return sp.getString("Shots",null);
    }
    public void putShots(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("Shots", value);
        editor.commit();
    }

    public String getCategory(){
        return sp.getString("Category",null);
    }
    public void putCategory(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("Category", value);
        editor.commit();
    }

    public String getSubCategory(){
        return sp.getString("SubCategory",null);
    }
    public void putSubCategory(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("SubCategory", value);
        editor.commit();
    }
    public String getParLevel(){
        return sp.getString("ParLevel",null);
    }
    public void putParLevel(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("ParLevel", value);
        editor.commit();
    }

    public String getDistributorName(){
        return sp.getString("DistributorName",null);
    }
    public void putDistributorName(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("DistributorName", value);
        editor.commit();
    }
    public String getPriceUnit(){
        return sp.getString("PriceUnit",null);
    }
    public void putPriceUnit(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("PriceUnit", value);
        editor.commit();
    }

    public String getBinNumber(){
        return sp.getString("PriceUnit",null);
    }
    public void putBinNumber(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("PriceUnit", value);
        editor.commit();
    }

    public String getProductCode(){
        return sp.getString("ProductCode",null);
    }
    public void putProductCode(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("ProductCode", value);
        editor.commit();
    }
    public String getSectionBottles(){
        return sp.getString("SectionBottles",null);
    }
    public void putSectionBottles(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("SectionBottles", value);
        editor.commit();
    }

}