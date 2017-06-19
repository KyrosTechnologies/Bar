package com.kyros.technologies.bar.Common.activity.Activity;

import android.annotation.TargetApi;
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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kyros.technologies.bar.Common.activity.Adapter.ParListAdapter;
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

public class ParList extends AppCompatActivity {

    private ParListAdapter adapter;
    private ArrayList<Purchase> purchaseArrayList=new ArrayList<Purchase>();
    private PreferenceManager store;
    private String UserProfileId=null;
    private RecyclerView par_recycler;
    private SearchView par_list_auto_complete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.par_list);
        par_recycler=(RecyclerView)findViewById(R.id.par_recycler);
        par_list_auto_complete=(SearchView) findViewById(R.id.par_list_auto_complete);
        adapter=new ParListAdapter(ParList.this,purchaseArrayList);
//        par_list_auto_complete.setThreshold(1);
//        par_list_auto_complete.setAdapter(adapter);
//        par_list_auto_complete.setTextColor(Color.RED);
        RecyclerView.LayoutManager layoutManagersecond=new LinearLayoutManager(getApplicationContext());
        par_recycler.setLayoutManager(layoutManagersecond);
        par_recycler.setItemAnimator(new DefaultItemAnimator());
        par_recycler.setAdapter(adapter);
        store= PreferenceManager.getInstance(getApplicationContext());
        UserProfileId=store.getUserProfileId();
        adapter.notifyDataSetChanged();
        //GetParList();
        adapter.notifyDataSetChanged();
        String s= getIntent().getStringExtra("search");
        if(s!=null){
            final ArrayList<Purchase> filterlistdd=filter(purchaseArrayList,s);
            adapter. setFilter(filterlistdd);
        }



        par_list_auto_complete.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

    private void GetParList() {
        String tag_json_obj = "json_obj_req";
        String url = EndURL.URL+"GetParValue/"+UserProfileId;
        //  String url = "http://192.168.0.109:8080/Bar/rest/getLiquorList";
        Log.d("waggonurl", url);

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, (String)null, new Response.Listener<JSONObject>() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onResponse(JSONObject response) {
                Log.d("List Response",response.toString());
                try {

                    JSONObject obj=new JSONObject(response.toString());
                    String message=obj.getString("Message");
                    boolean success=obj.getBoolean("IsSuccess");
                    if (success){

                        purchaseArrayList.clear();

                        JSONArray array=obj.getJSONArray("Model");
                        for (int i=0;i<array.length();i++){
                            JSONObject first=array.getJSONObject(i);
                            int userprofile=first.getInt("UserProfileId");
                            String barid=first.getString("BarId");
                            String sectionid=first.getString("SectionId");
                            int id=first.getInt("Id");
                            String liquorname=first.getString("LiquorName");
                            String liquorcapacity=first.getString("LiquorCapacity");
                            String shots=first.getString("Shots");
                            String category=first.getString("Category");
                            String subcategory=first.getString("SubCategory");
                            String parlevel=first.getString("ParLevel");
                            String distributorname=first.getString("DistributorName");
                            String price=first.getString("Price");
                            String binnumber=first.getString("BinNumber");
                            String productcode=first.getString("ProductCode");
                            String number=null;
                            try {
                                number=first.getString("CreatedOn");
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            String modify=null;
                            try {
                                modify=first.getString("ModifiedOn");
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            String minvalue=first.getString("MinValue");
                            String maxvalue=first.getString("MaxValue");
                            String pictureurl=first.getString("PictureURL");
                            String totalbottles=first.getString("TotalBottles");
                            String type=first.getString("Type");
                            String fullweight=first.getString("FullWeight");
                            String emptyweight=first.getString("EmptyWeight");

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
        GetParList();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                ParList.this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}