package com.kyros.technologies.bar.Inventory.Activity.Activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.h6ah4i.android.widget.verticalseekbar.VerticalSeekBar;
import com.kyros.technologies.bar.Inventory.Activity.Adapters.CustomPagerAdapter;
import com.kyros.technologies.bar.R;
import com.kyros.technologies.bar.ServiceHandler.ServiceHandler;
import com.kyros.technologies.bar.SharedPreferences.PreferenceManager;
import com.kyros.technologies.bar.utils.EndURL;
import com.kyros.technologies.bar.utils.UtilSectionBar;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

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
    private  ArrayList<UtilSectionBar> bottleslist=new ArrayList<UtilSectionBar>();
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
            String bottleslist3=store.getSectionBottles();
            ArrayList<UtilSectionBar> bottleslist12=gsons.fromJson(bottleslist3,type1);
            bottleslist=bottleslist12;

        }catch (Exception e){
            e.printStackTrace();
        }
        CustomPagerAdapter customPagerAdapter=new CustomPagerAdapter(this,bottleslist);
        viewPager.setAdapter(customPagerAdapter);
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

}