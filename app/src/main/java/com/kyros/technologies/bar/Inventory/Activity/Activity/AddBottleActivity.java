package com.kyros.technologies.bar.Inventory.Activity.Activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kyros.technologies.bar.Inventory.Activity.Adapters.AddBottleAdapter;
import com.kyros.technologies.bar.R;
import com.kyros.technologies.bar.ServiceHandler.ServiceHandler;
import com.kyros.technologies.bar.SharedPreferences.PreferenceManager;
import com.kyros.technologies.bar.utils.EndURL;
import com.kyros.technologies.bar.utils.Purchase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddBottleActivity extends AppCompatActivity {
    private TextView add_inventory_type;
    private RecyclerView add_bottle;
    private AddBottleAdapter adapter;
    private String UserprofileId=null;
    private ArrayList<Purchase> inventoryArrayList=new ArrayList<Purchase>();
    private PreferenceManager store;
    private SearchView add_bottle_auto_complete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_add_bottle);
        add_bottle=(RecyclerView)findViewById(R.id.add_bottle);
        add_bottle_auto_complete=(SearchView) findViewById(R.id.add_bottle_auto_complete);
        store= PreferenceManager.getInstance(getApplicationContext());
        UserprofileId=store.getUserProfileId();
        adapter=new AddBottleAdapter(AddBottleActivity.this,inventoryArrayList);
        RecyclerView.LayoutManager layoutManagersecond=new LinearLayoutManager(getApplicationContext());
        add_bottle.setLayoutManager(layoutManagersecond);
        add_bottle.setItemAnimator(new DefaultItemAnimator());
        add_bottle.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        //GetBottlesInventoryList();
        adapter.notifyDataSetChanged();
        add_inventory_type=(TextView)findViewById(R.id.add_inventory_type);
        add_inventory_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AddBottleActivity.this,InventoryActivity.class);
                startActivity(i);
            }
        });

        String s= getIntent().getStringExtra("search");
        if(s!=null){
            final ArrayList<Purchase> filterlistdd=filter(inventoryArrayList,s);
            adapter. setFilter(filterlistdd);
        }



        add_bottle_auto_complete.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if(query!=null){
                    final ArrayList<Purchase> filterlistdd=filter(inventoryArrayList,query);
                    adapter. setFilter(filterlistdd);

                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if(newText!=null){
                    final ArrayList<Purchase> filterlistdd=filter(inventoryArrayList,newText);
                    adapter. setFilter(filterlistdd);
                }

                return false;
            }
        });

    }
    private ArrayList<Purchase>filter(ArrayList<Purchase>movies,String query){
        query=query.toLowerCase();
        final ArrayList<Purchase>filterdlist=new ArrayList<>();
        for(Purchase movie:movies){
            final String text=movie.getLiquorname().toLowerCase();
            if(text.contains(query)){
                filterdlist.add(movie);
            }
        }
        return filterdlist;
    }
    private void GetBottlesInventoryList() {
        String tag_json_obj = "json_obj_req";
        final String url = EndURL.URL+"getUserPurchaseList/"+UserprofileId;
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
                            String liquorname=first.getString("liquorname");
                            String liquorcapacity=first.getString("liquorcapacity");
                            String shots=first.getString("shots");
                            String category=first.getString("category");
                            String parlevel=first.getString("parlevel");
                            String distributorname=first.getString("distributorname");
                            String priceunit=first.getString("price");
                            String binnumber=first.getString("binnumber");
                            String productcode=first.getString("productcode");
                            String createdon=first.getString("createdon");
                            String pictureurl=first.getString("pictureurl");
                            String subcategory=first.getString("subcategory");
                           String minvalue=first.getString("minvalue");
                            String maxvalue=first.getString("maxvalue");
                            String totalbottles=first.getString("totalbottles");
                            String fullweight=first.getString("fullweight");
                            String emptyweight=first.getString("emptyweight");
                            String type=first.getString("type");
                            Purchase purchase=new Purchase();
                            purchase.setId(id);
                            purchase.setLiquorname(liquorname);
                            purchase.setMinvalue(minvalue);
                            purchase.setMaxvalue(maxvalue);
                            purchase.setTotalbottles(totalbottles);
                            purchase.setFullweight(fullweight);
                            purchase.setEmptyweight(emptyweight);
                            purchase.setUserprofileid(userprofile);
                            purchase.setLiquorcapacity(liquorcapacity);
                            purchase.setShots(shots);
                            purchase.setCategory(category);
                            purchase.setSubcategory(subcategory);
                            purchase.setPictureurl(pictureurl);
                            purchase.setType(type);
                            purchase.setParlevel(parlevel);
                            purchase.setDistributorname(distributorname);
                            purchase.setPriceunit(priceunit);
                            purchase.setBinnumber(binnumber);
                            purchase.setProductcode(productcode);
                            purchase.setCreatedon(createdon);
                            inventoryArrayList.add(purchase);
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

//                texts.setText(error.toString());
            }
        }) {

        };
        ServiceHandler.getInstance().addToRequestQueue(objectRequest, tag_json_obj);

    }

    @Override
    protected void onResume() {
        super.onResume();
        GetBottlesInventoryList();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                AddBottleActivity.this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
