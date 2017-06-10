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
import com.kyros.technologies.bar.Common.activity.Adapter.DistributorListAdapter;
import com.kyros.technologies.bar.R;
import com.kyros.technologies.bar.ServiceHandler.ServiceHandler;
import com.kyros.technologies.bar.SharedPreferences.PreferenceManager;
import com.kyros.technologies.bar.utils.EndURL;
import com.kyros.technologies.bar.utils.Purchase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Rohin on 05-05-2017.
 */

public class DistributorList extends AppCompatActivity {

    private RecyclerView distributor_list_recycler;
    private DistributorListAdapter adapter;
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
        setContentView(R.layout.distributor_list);
        distributor_list_recycler=(RecyclerView)findViewById(R.id.distributor_list_recycler);
        adapter=new DistributorListAdapter(DistributorList.this,purchaseArrayList);
        RecyclerView.LayoutManager layoutManagersecond=new LinearLayoutManager(getApplicationContext());
        distributor_list_recycler.setLayoutManager(layoutManagersecond);
        distributor_list_recycler.setItemAnimator(new DefaultItemAnimator());
        distributor_list_recycler.setAdapter(adapter);
        store= PreferenceManager.getInstance(getApplicationContext());
        UserProfileId=store.getUserProfileId();
        adapter.notifyDataSetChanged();
//        GetDistributorList();
        adapter.notifyDataSetChanged();

    }

    private void GetDistributorList() {
        String tag_json_obj = "json_obj_req";
        String url = EndURL.URL+"GetDistributorList/"+UserProfileId;
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

                        JSONArray array=obj.getJSONArray("distributorslist");
                        for (int i=0;i<array.length();i++){
                            JSONObject first=array.getJSONObject(i);
                            int userprofile=first.getInt("userprofileid");
                            int id=first.getInt("id");
                            String distributorname=first.getString("distributorname");

                            Purchase purchase=new Purchase();
                            purchase.setid(id);
                            purchase.setDistributorname(distributorname);
                            purchase.setUserprofileid(userprofile);
                            purchaseArrayList.add(purchase);
                        }


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
    protected void onResume() {
        super.onResume();
        GetDistributorList();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                DistributorList.this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}