package com.kyros.technologies.bar.Common.activity.Activity;

import android.annotation.TargetApi;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kyros.technologies.bar.Common.activity.Adapter.ValueOnHandAdapter;
import com.kyros.technologies.bar.Inventory.Activity.Activity.BarActivity;
import com.kyros.technologies.bar.Inventory.Activity.Adapters.BarAdapter;
import com.kyros.technologies.bar.R;
import com.kyros.technologies.bar.ServiceHandler.ServiceHandler;
import com.kyros.technologies.bar.SharedPreferences.PreferenceManager;
import com.kyros.technologies.bar.utils.EndURL;
import com.kyros.technologies.bar.utils.MyBar;
import com.kyros.technologies.bar.utils.Purchase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Rohin on 04-05-2017.
 */

public class ValueOnHand extends AppCompatActivity {

    private RecyclerView value_on_hand_recycler;
    private ValueOnHandAdapter adapter;
    private ArrayList<Purchase> purchaseArrayList=new ArrayList<Purchase>();
    private PreferenceManager store;
    private String UserProfileId=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.value_on_hand);
        value_on_hand_recycler=(RecyclerView)findViewById(R.id.value_on_hand_recycler);
        adapter=new ValueOnHandAdapter(ValueOnHand.this,purchaseArrayList);
        RecyclerView.LayoutManager layoutManagersecond=new LinearLayoutManager(getApplicationContext());
        value_on_hand_recycler.setLayoutManager(layoutManagersecond);
        value_on_hand_recycler.setItemAnimator(new DefaultItemAnimator());
        value_on_hand_recycler.setAdapter(adapter);
        store= PreferenceManager.getInstance(getApplicationContext());
        UserProfileId=store.getUserProfileId();
        adapter.notifyDataSetChanged();
        GetBarList();
        adapter.notifyDataSetChanged();
    }

    private void GetBarList() {
        String tag_json_obj = "json_obj_req";
        String url = EndURL.URL+"GetValueOnHand/"+UserProfileId;
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
                            int userprofile=first.getInt("userprofileid");
                            String barid=first.getString("barid");
                            String sectionid=first.getString("sectionid");
                            int id=first.getInt("id");
                            String liquorname=first.getString("liquorname");
                            String liquorcapacity=first.getString("liquorcapacity");
                            String shots=first.getString("shots");
                            String category=first.getString("category");
                            String subcategory=first.getString("subcategory");
                            String parlevel=first.getString("parlevel");
                            String distributorname=first.getString("distributorname");
                            String price=first.getString("price");
                            String binnumber=first.getString("binnumber");
                            String productcode=first.getString("productcode");
                            String number=null;
                            try {
                                number=first.getString("createdon");
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            String modify=null;
                            try {
                                modify=first.getString("modifiedon");
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            String minvalue=first.getString("minvalue");
                            String maxvalue=first.getString("minvalue");
                            String pictureurl=first.getString("pictureurl");
                            String totalbottles=first.getString("totalbottles");
                            String type=first.getString("type");
                            String fullweight=first.getString("fullweight");
                            String emptyweight=first.getString("emptyweight");

                            Purchase purchase=new Purchase();
                            purchase.setid(id);
                            purchase.setBarid(barid);
                            purchase.setUserprofileid(userprofile);
                            purchase.setCreatedon(number);
                            purchase.setSectionid(sectionid);
                            purchase.setLiquorname(liquorname);
                            purchase.setLiquorcapacity(liquorcapacity);
                            purchase.setShots(shots);
                            purchase.setCategory(category);
                            purchase.setSubcategory(subcategory);
                            purchase.setParlevel(parlevel);
                            purchase.setDistributorname(distributorname);
                            purchase.setPriceunit(price);
                            purchase.setBinnumber(binnumber);
                            purchase.setProductcode(productcode);
                            purchase.setModifiedon(modify);
                            purchase.setMinvalue(minvalue);
                            purchase.setMaxvalue(maxvalue);
                            purchase.setSmall_picture_url(pictureurl);
                            purchase.setTotalbottles(totalbottles);
                            purchase.setType(type);
                            purchase.setFullweight(fullweight);
                            purchase.setEmptyweight(emptyweight);
                            purchaseArrayList.add(purchase);
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

            }
        }) {

        };
        ServiceHandler.getInstance().addToRequestQueue(objectRequest, tag_json_obj);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                ValueOnHand.this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
