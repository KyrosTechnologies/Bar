package com.kyros.technologies.bar.Purchase.Activity.Activity;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kyros.technologies.bar.Purchase.Activity.Adapters.PurchaseListAdapter;
import com.kyros.technologies.bar.R;
import com.kyros.technologies.bar.ServiceHandler.ServiceHandler;
import com.kyros.technologies.bar.SharedPreferences.PreferenceManager;
import com.kyros.technologies.bar.utils.EndURL;
import com.kyros.technologies.bar.utils.Purchase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PurchaseListActivity extends AppCompatActivity {
    private LinearLayout my_inventory_list;
    private RecyclerView purchase_recycler;
    private PurchaseListAdapter adapter;
    private String UserprofileId=null;
    private ArrayList<Purchase>purchaseArrayList=new ArrayList<Purchase>();
    private PreferenceManager store;
    private SearchView purchase_stock_auto_complete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_purchase_list);
        store= PreferenceManager.getInstance(getApplicationContext());
        UserprofileId=store.getUserProfileId();
        purchase_recycler=(RecyclerView)findViewById(R.id.purchase_recycler);
        purchase_stock_auto_complete=(SearchView) findViewById(R.id.purchase_stock_auto_complete);
        adapter=new PurchaseListAdapter(PurchaseListActivity.this,purchaseArrayList);
        RecyclerView.LayoutManager layoutManagersecond=new LinearLayoutManager(getApplicationContext());
        purchase_recycler.setLayoutManager(layoutManagersecond);
        purchase_recycler.setItemAnimator(new DefaultItemAnimator());
        purchase_recycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        my_inventory_list=(LinearLayout)findViewById(R.id.my_inventory_list);
        adapter.notifyDataSetChanged();
        my_inventory_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PurchaseListActivity.this,InventoryTypePurchase.class);
                startActivity(intent);
            }
        });

        String s= getIntent().getStringExtra("search");
        if(s!=null){
            final ArrayList<Purchase> filterlistdd=filter(purchaseArrayList,s);
            adapter. setFilter(filterlistdd);
        }



        purchase_stock_auto_complete.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if(query!=null){
                    final ArrayList<Purchase> filterlistdd=filter(purchaseArrayList,query);
                    adapter. setFilter(filterlistdd);

                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if(newText!=null){
                    final ArrayList<Purchase> filterlistdd=filter(purchaseArrayList,newText);
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

    @Override
    protected void onResume() {
        super.onResume();
        GetBottlesList();

    }

    private void GetBottlesList() {
        String tag_json_obj = "json_obj_req";
        final String url = EndURL.URL+"getUserPurchaseList/"+UserprofileId;
        //  String url = "http://192.168.0.109:8080/Bar/rest/getLiquorList";
        Log.d("waggonurl", url);

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, (String)null, new Response.Listener<JSONObject>() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onResponse(JSONObject response) {
                Log.d("List Response",response.toString());
                purchaseArrayList.clear();
                try {

                    JSONObject obj=new JSONObject(response.toString());
                    String message=obj.getString("Message");
                    boolean success=obj.getBoolean("IsSuccess");
                    if (success){

                        purchaseArrayList.clear();

                        JSONArray array=obj.getJSONArray("Model");
                        for (int i=0;i<array.length();i++){
                            JSONObject first=array.getJSONObject(i);
                            String id=first.getString("Id");
                            String userprofile=first.getString("UserProfileId");
                            String liquorname=first.getString("LiquorName");
                            String liquorcapacity=first.getString("LiquorCapacity");
                            String shots=first.getString("Shots");
                            String category=first.getString("Category");
                            String subcategory=null;
                            try {
                                subcategory=first.getString("SubCategory");

                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            String parlevel=first.getString("ParLevel");
                            String distributorname=first.getString("DistributorName");
                            String priceunit=first.getString("Price");
                            String binnumber=first.getString("BinNumber");
                            String productcode=first.getString("ProductCode");
                            String createdon=first.getString("CreatedOn");
                            String minvalue=first.getString("MinValue");
                            String maxvalue=first.getString("MaxValue");
                            String pictureurl=first.getString("PictureURL");
                            String type=first.getString("Type");
                            String fullweight=first.getString("FullWeight");
                            String emptyweight=first.getString("EmptyWeight");
                            String totalbottles=first.getString("TotalBottles");
                            Purchase purchase=new Purchase();
                            purchase.setLiquorname(liquorname);
                            purchase.setTotalbottles(totalbottles);
                            purchase.setId(Integer.parseInt(id));
                            purchase.setMinvalue(minvalue);
                            purchase.setMaxvalue(maxvalue);
                            purchase.setPictureurl(pictureurl);
                            purchase.setType(type);
                            purchase.setFullweight(fullweight);
                            purchase.setEmptyweight(emptyweight);
                            purchase.setUserprofileid(Integer.parseInt(userprofile));
                            purchase.setLiquorcapacity(liquorcapacity);
                            purchase.setShots(shots);
                            purchase.setCategory(category);
                            purchase.setSubcategory(subcategory);
                            purchase.setParlevel(parlevel);
                            purchase.setDistributorname(distributorname);
                            purchase.setPriceunit(priceunit);
                            purchase.setBinnumber(binnumber);
                            purchase.setProductcode(productcode);
                            purchase.setCreatedon(createdon);
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
            @Override
            public Map<String, String> getHeaders()throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("Authorization", store.getUserProfileId()+"|"+store.getAuthorizationKey());
                return params;
            }
        };
        ServiceHandler.getInstance().addToRequestQueue(objectRequest, tag_json_obj);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_bar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){


            case android.R.id.home:
                PurchaseListActivity.this.finish();
                return true;
            case R.id.home_bar:
                PurchaseListActivity.this.finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}