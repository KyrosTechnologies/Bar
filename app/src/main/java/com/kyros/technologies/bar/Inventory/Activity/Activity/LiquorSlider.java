package com.kyros.technologies.bar.Inventory.Activity.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kyros.technologies.bar.Inventory.Activity.Adapters.CustomPagerAdapter;
import com.kyros.technologies.bar.R;
import com.kyros.technologies.bar.SharedPreferences.PreferenceManager;
import com.kyros.technologies.bar.utils.UtilSectionBar;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rohin on 05-05-2017.
 */

public class LiquorSlider extends AppCompatActivity {


    private String UserProfileId=null;
    private PreferenceManager store;
    private String barid;
    private String sectionid;
    private ViewPager viewPager;
    private int position =0;
    private  ArrayList<UtilSectionBar> bottleslist=new ArrayList<UtilSectionBar>();
    private AlertDialog online;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.liquor_slider);
        store= PreferenceManager.getInstance(getApplicationContext());
        UserProfileId=store.getUserProfileId();
        barid=store.getBarId();
        sectionid=store.getSectionId();
        viewPager=(ViewPager)findViewById(R.id.viewpager);

        try{
           bottleslist.clear();
            Gson gsons=new Gson();
            Type type1=new TypeToken<List<UtilSectionBar>>(){}.getType();
            String bottleslist3=store.getSectionBottles("SectionBottles"+sectionid);
            ArrayList<UtilSectionBar> bottleslist12=gsons.fromJson(bottleslist3,type1);
            bottleslist=bottleslist12;

        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            Bundle bundle=getIntent().getExtras();
            position=bundle.getInt("position");
        }catch (Exception e){
            e.printStackTrace();
        }
        CustomPagerAdapter customPagerAdapter=new CustomPagerAdapter(this,bottleslist,position);
        viewPager.setAdapter(customPagerAdapter);
        customPagerAdapter.notifyDataSetChanged();
    }

    public boolean checkOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            return true;

        }else {
            onlineDialog();

        }

        return false;
    }

    public void onlineDialog(){
        online= new AlertDialog.Builder(LiquorSlider.this).create();
        online.setTitle("No Internet Connection");
        online.setMessage("We cannot detect any internet connectivity.Please check your internet connection and try again");
        //   alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
        online.setButton("Try Again",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                checkOnline();
            }
        });
        online.show();

    }
    private void dismissonlineDialog(){
        if(online!=null && online.isShowing()){
            online.dismiss();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                LiquorSlider.this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissonlineDialog();
    }

}