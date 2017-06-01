package com.kyros.technologies.bar.Inventory.Activity.Activity;

import android.annotation.TargetApi;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.kyros.technologies.bar.Inventory.Activity.Adapters.LiquorApiAdapter;
import com.kyros.technologies.bar.Inventory.Activity.List.LiquorListClass;
import com.kyros.technologies.bar.R;
import com.kyros.technologies.bar.ServiceHandler.ServiceHandler;
import com.kyros.technologies.bar.utils.EndURL;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyInventoryListActivity extends AppCompatActivity {
    private RecyclerView recycler_database;
    private LiquorApiAdapter adapter;
    private ArrayList<LiquorListClass> liquorlist=new ArrayList<LiquorListClass>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_my_inventory_list);
        recycler_database=(RecyclerView)findViewById(R.id.recycler_database);
        adapter=new LiquorApiAdapter(MyInventoryListActivity.this,liquorlist);
        RecyclerView.LayoutManager layoutManagersecond=new LinearLayoutManager(getApplicationContext());
        recycler_database.setLayoutManager(layoutManagersecond);
        recycler_database.setItemAnimator(new DefaultItemAnimator());
        recycler_database.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        StateChangeWaggonapi();
    }

    private void StateChangeWaggonapi() {
        String tag_json_obj = "json_obj_req";
        String url = EndURL.URL+ "getLiquorList";
        //  String url = "http://192.168.0.109:8080/Bar/rest/getLiquorList";
        //     showProgressDialog();
        Log.d("waggonurl", url);

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, (String) null, new Response.Listener<JSONObject>() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onResponse(JSONObject response) {
                Log.d("List Response",response.toString());
                try {

                    JSONObject obj=new JSONObject(response.toString());
                    String message=obj.getString("message");
                    boolean success=obj.getBoolean("issuccess");
                    if (success){

                        JSONArray array=obj.getJSONArray("userlist");
                        for (int i=0;i<array.length();i++){
                            JSONObject first=array.getJSONObject(i);
                            String name=first.getString("name");
                            int quantity=first.getInt("capacity_mL");
                            String type=first.getString("alcohol_type");
                            String pic=first.getString("small_picture_url");
                            String subtype=first.getString("alcohol_subtype");
                            LiquorListClass liquorListClass=new LiquorListClass();
                            liquorListClass.setName(name);
                            liquorListClass.setCapacity_mL(quantity);
                            liquorListClass.setAlcohol_subtype(subtype);
                            liquorListClass.setAlcohol_type(type);
                            liquorListClass.setSmall_picture_url(pic);
                            liquorlist.add(liquorListClass);
                        }

                        MyInventoryListActivity.this.finish();

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
                MyInventoryListActivity.this.finish();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }
}
