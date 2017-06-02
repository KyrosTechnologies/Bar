package com.kyros.technologies.bar.Inventory.Activity.Activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
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
import com.kyros.technologies.bar.Common.activity.Activity.EditProfile;
import com.kyros.technologies.bar.R;
import com.kyros.technologies.bar.ServiceHandler.ServiceHandler;
import com.kyros.technologies.bar.SharedPreferences.PreferenceManager;
import com.kyros.technologies.bar.utils.EndURL;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Rohin on 05-05-2017.
 */

public class LiquorSlider extends AppCompatActivity {

    private TextView edit_bottle;
    private String UserProfileId=null;
    private PreferenceManager store;
    private EditText bottle_quan;
    private ImageView plus,minus,liquor_bottle_image;
    private TextView shots_count;
    private SeekBar mySeekBar;
    private String userprofileid;
    private double totalcount=0;
    private String barid;
    private String sectionid;
    private String liquorname;
    private String shots;
    private String capacity;
    private String pictureurl;
    private double minval;
    private double maxval;
    private String totalbottles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.liquor_slider);
        store= PreferenceManager.getInstance(getApplicationContext());
        userprofileid=store.getUserProfileId();
        barid=store.getBarId();
        sectionid=store.getSectionId();
        try {
            Bundle bundle=getIntent().getExtras();
            pictureurl=bundle.getString("picture");
            minval=bundle.getDouble("minvalue");
            maxval=bundle.getDouble("maxvalue");
            shots=bundle.getString("shots");
            capacity=bundle.getString("capacity");
            liquorname=bundle.getString("name");
            totalbottles=bundle.getString("totalbottles");
            totalcount=Integer.parseInt(totalbottles);
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            actionBar.setTitle(liquorname);

        }catch (Exception e){
            e.printStackTrace();
        }
        liquor_bottle_image=(ImageView)findViewById(R.id.liquor_bottle_image);

        try {
            Picasso.with(LiquorSlider.this)
                    .load(pictureurl)
                    .resize(279, 320)
                    .into(liquor_bottle_image);
        }catch (Exception eq){
            eq.printStackTrace();
        }
        bottle_quan=(EditText)findViewById(R.id.bottle_quan);
        try {
            Log.d("bottles",totalbottles);
            String value=totalbottles;
            bottle_quan.setText(value);
        }catch (Exception e){
            e.printStackTrace();
        }
        edit_bottle=(TextView)findViewById(R.id.edit_bottle);
        minus=(ImageView)findViewById(R.id.minus);
        plus=(ImageView)findViewById(R.id.plus);
        mySeekBar=(SeekBar)findViewById(R.id.mySeekBar);
        shots_count=(TextView)findViewById(R.id.shots_count);
        try {
            shots_count.setText(shots);

        }catch (Exception e){
            e.printStackTrace();
        }
        edit_bottle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LiquorSlider.this,BottleDescriptionActivity.class);
                startActivity(i);
            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalcount++;
                bottle_quan.setText(String.valueOf(totalcount));
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalcount!=0){
                    totalcount--;
                    bottle_quan.setText(String.valueOf(totalcount));
                }
            }
        });

        mySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
               progress= progress/100;
                totalcount=totalcount+progress;
                bottle_quan.setText(String.valueOf(totalcount));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private void LiquorSliderapi(final String userprofileid, double totalbottle, String barid, String sectionid,  String liquorname) {
        String tag_json_obj = "json_obj_req";
        String url = EndURL.URL+"InsertUserTotalBottle";
        //  String url = "http://192.168.0.109:8080/Bar/rest/getLiquorList";
        Log.d("waggonurl", url);
        JSONObject inputLogin=new JSONObject();
        try{
            inputLogin.put("userprofileid",userprofileid);
            inputLogin.put("totalbottles",String.valueOf(totalbottle));
            inputLogin.put("barid",barid);
            inputLogin.put("sectionid",sectionid);
            inputLogin.put("liquorname",liquorname);


        }catch (Exception e){
            e.printStackTrace();
        }
        Log.d("inputJsonuser",inputLogin.toString());
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.PUT, url, inputLogin, new Response.Listener<JSONObject>() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onResponse(JSONObject response) {
                Log.d("List Response",response.toString());
                try {

                    JSONObject obj=new JSONObject(response.toString());
                    String message=obj.getString("message");
                    boolean success=obj.getBoolean("issuccess");
                    if (success){

                        Toast.makeText(getApplicationContext(),"Successfully Updated",Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(LiquorSlider.this,SectionBottlesActivity.class);
                        startActivity(i);

                    }else {
                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),"Not Working",Toast.LENGTH_SHORT).show();
                if(error!=null){
                    Toast.makeText(getApplicationContext(),"Error: "+error.toString(),Toast.LENGTH_SHORT).show();
                }
//                texts.setText(error.toString());
            }
        }) {

        };
        objectRequest.setRetryPolicy(new DefaultRetryPolicy(
                20*1000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ServiceHandler.getInstance().addToRequestQueue(objectRequest, tag_json_obj);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_done, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_done:
                LiquorSliderapi(userprofileid,totalcount,barid,sectionid,liquorname);
                break;
            case android.R.id.home:
                LiquorSlider.this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}