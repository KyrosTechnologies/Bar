package com.kyros.technologies.bar.Inventory.Activity.Activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kyros.technologies.bar.R;
import com.kyros.technologies.bar.Inventory.Activity.Adapters.SectionBarAdapter;
import com.kyros.technologies.bar.ServiceHandler.ServiceHandler;
import com.kyros.technologies.bar.SharedPreferences.PreferenceManager;
import com.kyros.technologies.bar.utils.EndURL;
import com.kyros.technologies.bar.utils.UtilSectionBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SectionBottlesActivity extends AppCompatActivity {
    private LinearLayout add_bottle_act;
    private SectionBarAdapter adapter;
    private RecyclerView section_bar_recycler;
    private PreferenceManager store;
    private String UserprofileId=null;
    private String SectionId=null;
    private ArrayList<UtilSectionBar>utilSectionBarArrayList=new ArrayList<UtilSectionBar>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_section_bottles);
        section_bar_recycler=(RecyclerView)findViewById(R.id.section_bar_recycler);
        adapter=new SectionBarAdapter(SectionBottlesActivity.this,utilSectionBarArrayList);
        store= PreferenceManager.getInstance(getApplicationContext());
        UserprofileId=store.getUserProfileId();
        SectionId=store.getSectionId();
        RecyclerView.LayoutManager layoutManagersecond=new LinearLayoutManager(getApplicationContext());
        section_bar_recycler.setLayoutManager(layoutManagersecond);
        section_bar_recycler.setItemAnimator(new DefaultItemAnimator());
        section_bar_recycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        add_bottle_act=(LinearLayout)findViewById(R.id.add_bottle_act);
        GetBottlesList();
        adapter.notifyDataSetChanged();
        Toast.makeText(getApplicationContext(),"UserProfileId :"+UserprofileId,Toast.LENGTH_SHORT).show();
        add_bottle_act.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(SectionBottlesActivity.this,AddBottleActivity.class);
                startActivity(intent);
            }
        });
    }

    private void GetBottlesList() {
        String tag_json_obj = "json_obj_req";
        final String url = EndURL.URL+"getUserliquorlist/"+UserprofileId+"/"+SectionId;
        //  String url = "http://192.168.0.109:8080/Bar/rest/getLiquorList";
        Log.d("waggonurl", url);

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, (String)null, new Response.Listener<JSONObject>() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onResponse(JSONObject response) {
                Log.d("List Response",response.toString());
                try {

                    JSONObject obj=new JSONObject(response.toString());
                    String message=obj.getString("message");
                    boolean success=obj.getBoolean("issuccess");
                    if (success){

                        JSONArray array=obj.getJSONArray("model");
                        for (int i=0;i<array.length();i++){
                            JSONObject first=array.getJSONObject(i);
                            int id=first.getInt("id");
                            int userprofile=first.getInt("userprofileid");
                            int barid=first.getInt("barid");
                            int sectionid=first.getInt("sectionid");
                            double minvalue=first.getDouble("minvalue");
                            double maxvalue=first.getDouble("maxvalue");
                            String liquorname=first.getString("liquorname");
                            String liquorcapacity=null;
                            try{
                                liquorcapacity=first.getString("liquorcapacity");

                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            String shots=first.getString("shots");
                            String category=first.getString("category");
                            String subcategory=first.getString("subcategory");
                            String parlevel=first.getString("parlevel");
                            String distributorname=first.getString("distributorname");
                            String priceunit=first.getString("price");
                            String binnumber=first.getString("binnumber");
                            String productcode=first.getString("productcode");
                            String createdon=first.getString("createdon");
                            String pictureurl=first.getString("pictureurl");
                            String totalbottles=null;
                            try {
                                totalbottles=first.getString("totalbottles");
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            UtilSectionBar utilSectionBar=new UtilSectionBar();
                            utilSectionBar.setSectionid(sectionid);
                            utilSectionBar.setBarid(barid);
                            utilSectionBar.setLiquorname(liquorname);
                            utilSectionBar.setUserprofileid(userprofile);
                            utilSectionBar.setLiquorcapacity(liquorcapacity);
                            utilSectionBar.setShots(shots);
                            utilSectionBar.setCategory(category);
                            utilSectionBar.setSubcategory(subcategory);
                            utilSectionBar.setParlevel(parlevel);
                            utilSectionBar.setDistributorname(distributorname);
                            utilSectionBar.setPriceunit(priceunit);
                            utilSectionBar.setBinnumber(binnumber);
                            utilSectionBar.setProductcode(productcode);
                            utilSectionBar.setCreatedon(createdon);
                            utilSectionBar.setPictureurl(pictureurl);
                            utilSectionBar.setMinvalue(minvalue);
                            utilSectionBar.setMaxvalue(maxvalue);
                            utilSectionBar.setTotalbottles(totalbottles);
                            utilSectionBarArrayList.add(utilSectionBar);
                        }

                        Toast.makeText(getApplicationContext(),"Sucessfully Created",Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

                adapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),"Not Working",Toast.LENGTH_SHORT).show();

//                texts.setText(error.toString());
            }
        }) {

        };
        ServiceHandler.getInstance().addToRequestQueue(objectRequest, tag_json_obj);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                SectionBottlesActivity.this.finish();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }
}
