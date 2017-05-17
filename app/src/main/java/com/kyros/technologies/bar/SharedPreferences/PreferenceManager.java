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

    public void clear(){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.clear();
        editor.commit();
    }

    public String getSpinnerName(String key){
        return sp.getString(key,null);
    }
    public void putSpinnerName(String key, String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString(key, value);
        editor.commit();
    }
    public String getShopname(){
        return sp.getString("ShopName",null);
    }
    public void putShopname(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("ShopName", value);
        editor.commit();
    }
    public String getSpinnerId(String key){
        return sp.getString(key,null);
    }
    public void putSpinnerId(String key, String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString(key, value);
        editor.commit();
    }
    public String getShopAddress(){
        return sp.getString("ShopAddress",null);
    }
    public void putShopAddress(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("ShopAddress", value);
        editor.commit();
    }
    public String getShopPincode(){
        return sp.getString("PinCode",null);
    }
    public void putShopPincode(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("PinCode", value);
        editor.commit();
    }

    public String getDealerProfileId(){
        return sp.getString("DealerProfileId",null);
    }
    public void putDealerProfileId(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("DealerProfileId", value);
        editor.commit();
    }
    public String getShopPictureAttachmentId(String key){
        return sp.getString(key,null);
    }
    public void putShopPictureAttachmentId(String key, String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString(key, value);
        editor.commit();
    }public String getHomePage(){
        return sp.getString("Home",null);
    }
    public void putHomepage(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("Home", value);
        editor.commit();
    }
    public String getCarPicture(String key){
        return sp.getString(key,null);
    }
    public void putCarPicture(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("CarPicture", value);
        editor.commit();
    }
    public String getCarPictureAttachmentId(String key){
        return sp.getString(key,null);
    }
    public void putCarPictureAttachmentId(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("AttachmentIdCarPicture", value);
        editor.commit();
    }
    public String getCarName(String key){
        return sp.getString(key,null);
    }
    public void putCarName(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("CarName", value);
        editor.commit();
    }
    public String getCarMakeYear(String key){
        return sp.getString(key,null);
    }
    public void putCarMakeYear(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("CarMakeYear", value);
        editor.commit();
    }
    public String getShopPicture(){
        return sp.getString("ShopPicture",null);
    }
    public void putShopPicture(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("ShopPicture", value);
        editor.commit();
    }
    public void putCarsList(ArrayList<String> ok){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        Set<String> set= new HashSet<String>();
        set.addAll(ok);
        editor.putStringSet("CarsList",set);
        editor.commit();


    }
    public Set<String> getCarsList(){
        return sp.getStringSet("CarsList",null);
    }
    public void putCarsDetailsIdList(ArrayList<String> ok){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        Set<String> set= new HashSet<String>();
        set.addAll(ok);
        editor.putStringSet("CarsListId",set);
        editor.commit();
    }
    public Set<String> getCarsDetailsIdList(){
        return sp.getStringSet("CarsListId",null);
    }
    public void putCarMakeYears(ArrayList<String> ok){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        Set<String> set= new HashSet<String>();
        set.addAll(ok);
        editor.putStringSet("CarMkyear",set);
        editor.commit();
    }
    public Set<String> getCarMakeYears(){
        return sp.getStringSet("CarMkyear",null);
    }
    public void putCarNames(ArrayList<String> ok){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        Set<String> set= new HashSet<String>();
        set.addAll(ok);
        editor.putStringSet("CarNamesss",set);
        editor.commit();
    }
    public Set<String> getCarNames(){
        return sp.getStringSet("CarNamesss",null);
    }
    public void putCarPictures(ArrayList<String> ok){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        Set<String> set= new HashSet<String>();
        set.addAll(ok);
        editor.putStringSet("Carspiks",set);
        editor.commit();
    }
    public Set<String> getCarPictures(){
        return sp.getStringSet("Carspiks",null);
    }
    public void putCarPicturesattachmentid(ArrayList<String> ok){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        Set<String> set= new HashSet<String>();
        set.addAll(ok);
        editor.putStringSet("attahidcarpic",set);
        editor.commit();
    }
    public Set<String> getCarPicturesattachmentid(){
        return sp.getStringSet("attahidcarpic",null);
    }
    public void putCarNumberplate(ArrayList<String> ok){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        Set<String> set= new HashSet<String>();
        set.addAll(ok);
        editor.putStringSet("Numberplarecar",set);
        editor.commit();
    }
    public Set<String> getCarNumberplate(){
        return sp.getStringSet("Numberplarecar",null);
    }
    public String getProfilePictureUrl(){
        return sp.getString("FileSourceProfile",null);
    }
    public void putProfilePictureUrl(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("FileSourceProfile", value);
        editor.commit();
    }public String getProfilePictureFileName(String key){
        return sp.getString(key,null);
    }
    public void putProfilePictureFileName(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("FileNameProfile", value);
        editor.commit();

    }
    public String getCarPictureFileName(String key){
        return sp.getString(key,null);
    }
    public void putCarPictureFileName(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("FileNameCar", value);
        editor.commit();
    } public String getCarPictureUrl(String key){
        return sp.getString(key,null);
    }
    public void putCarPictureUrl(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("FileSourceCar", value);
        editor.commit();
    }
    public String getShopPictureUrl(){
        return sp.getString("FileSourceShop",null);
    }
    public void putShopPictureUrl(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("FileSourceShop", value);
        editor.commit();
    }
    public String getShopProfilePictureUrl(){
        return sp.getString("FileSourceProfileShop",null);
    }
    public void putShopProfilePictureUrl(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("FileSourceProfileShop", value);
        editor.commit();
    }public String getBusinessNatureTypes(){
        return sp.getString("BusinessNatureType",null);
    }
    public void putBusinessNatureTypes(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("BusinessNatureType", value);
        editor.commit();
    }public String getBusinessTypes(){
        return sp.getString("BusinessType",null);
    }
    public void putBusinessTypes(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("BusinessType", value);
        editor.commit();
    }public String getWorkinProgress(){
        return sp.getString("WorkInProgress",null);
    }
    public void putWorkinProgress(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("WorkInProgress", value);
        editor.commit();
    }public String getVechiled_Delivered(){
        return sp.getString("Vechiled_Delivered",null);
    }
    public void putVechiled_Delivered(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("Vechiled_Delivered", value);
        editor.commit();
    }public String getSaw(){
        return sp.getString("Saw",null);
    }
    public void putSaw(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("Saw", value);
        editor.commit();
    }public String getSaw1(){
        return sp.getString("Saw1",null);
    }
    public void putSaw1(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("Saw1", value);
        editor.commit();
    }public String getSaw2(){
        return sp.getString("Saw2",null);
    }
    public void putSaw2(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("Saw2", value);
        editor.commit();
    }public String getSaw3(){
        return sp.getString("Saw3",null);
    }
    public void putSaw3(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("Saw3", value);
        editor.commit();
    }public String getAuthorizedDealershipName(){
        return sp.getString("Saw3",null);
    }
    public void putAuthorizedDealershipName(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("Saw3", value);
        editor.commit();
    }public String getMapListGoogle(){
        return sp.getString("MapListGoogle",null);
    }
    public void putMapListGoogle(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("MapListGoogle", value);
        editor.commit();
    }public String getMapListWaggon(){
        return sp.getString("MapListWaggon",null);
    }
    public void putMapListWaggon(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("MapListWaggon", value);
        editor.commit();
    }public String getFCMToken(){
        return sp.getString("FCMToken",null);
    }
    public void putFCMToken(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("FCMToken", value);
        editor.commit();
    }public String getFileSourceList(){
        return sp.getString("FileSourceList",null);
    }
    public void putFileSourceList(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("FileSourceList", value);
        editor.commit();
    }
    public String getTempOneTimePass(){
        return sp.getString("OneTimePass",null);
    }
    public void putTempOneTimePass(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("OneTimePass", value);
        editor.commit();
    }
    public String getLatitudeSaved(){
        return sp.getString("LatitudeSaved",null);
    }
    public void putLatitudeSaved(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("LatitudeSaved",value);
        editor.commit();
    }
    public String getLongitudeSaved(){
        return sp.getString("LongitudeSaved",null);
    }
    public void putLongitudeSaved(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("LongitudeSaved",value);
        editor.commit();
    }
    public String getUserPassword(){
        return sp.getString("UserPassword",null);
    }
    public void putUserPassword(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("UserPassword",value);
        editor.commit();
    }
}