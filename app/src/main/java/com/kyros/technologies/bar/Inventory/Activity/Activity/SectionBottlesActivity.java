package com.kyros.technologies.bar.Inventory.Activity.Activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kyros.technologies.bar.Inventory.Activity.Adapters.SectionBarAdapter;
import com.kyros.technologies.bar.Inventory.Activity.Adapters.SimpleItemHelperBottles;
import com.kyros.technologies.bar.Inventory.Activity.interfacesmodel.OnBottleListChangedListener;
import com.kyros.technologies.bar.Inventory.Activity.interfacesmodel.OnStartDragListener;
import com.kyros.technologies.bar.R;
import com.kyros.technologies.bar.ServiceHandler.ServiceHandler;
import com.kyros.technologies.bar.SharedPreferences.PreferenceManager;
import com.kyros.technologies.bar.utils.EndURL;
import com.kyros.technologies.bar.utils.UtilSectionBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SectionBottlesActivity extends AppCompatActivity implements OnBottleListChangedListener,OnStartDragListener {
    private LinearLayout add_bottle_act;
    private SectionBarAdapter adapter;
    private RecyclerView section_bar_recycler;
    private PreferenceManager store;
    private String UserprofileId=null;
    private String SectionId=null;
    private ArrayList<UtilSectionBar>utilSectionBarArrayList=new ArrayList<UtilSectionBar>();
    private SearchView section_bottles_auto_complete;
    private String SectionBottlesListInString=null;
    private ItemTouchHelper mItemTouchHelper;
    private SwipeRefreshLayout bottles_swipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_section_bottles);
        store= PreferenceManager.getInstance(getApplicationContext());

        section_bar_recycler=(RecyclerView)findViewById(R.id.section_bar_recycler);
        section_bottles_auto_complete=(SearchView) findViewById(R.id.section_bottles_auto_complete);
        UserprofileId=store.getUserProfileId();
        SectionId=store.getSectionId();
        add_bottle_act=(LinearLayout)findViewById(R.id.add_bottle_act);
       // GetBottlesList();
        SectionBottlesListInString=store.getSection("SectionBottles"+SectionId);
        bottles_swipe=(SwipeRefreshLayout)findViewById(R.id.bottles_swipe);
        Toast.makeText(getApplicationContext(),"UserProfileId :"+UserprofileId,Toast.LENGTH_SHORT).show();
        add_bottle_act.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent =new Intent(SectionBottlesActivity.this,AddBottleActivity.class);
//                startActivity(intent);
                Intent i=new Intent(SectionBottlesActivity.this,InventoryActivity.class);
                startActivity(i);
            }
        });

        String s= getIntent().getStringExtra("search");
        if(s!=null){
            final ArrayList<UtilSectionBar> filterlistdd=filter(utilSectionBarArrayList,s);
            adapter. setFilter(filterlistdd);
        }



        section_bottles_auto_complete.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if(query!=null){
                    final ArrayList<UtilSectionBar> filterlistdd=filter(utilSectionBarArrayList,query);
                    adapter. setFilter(filterlistdd);

                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if(newText!=null){
                    final ArrayList<UtilSectionBar> filterlistdd=filter(utilSectionBarArrayList,newText);
                    adapter. setFilter(filterlistdd);
                }

                return false;
            }
        });

        if(SectionBottlesListInString!=null){
            try{
                utilSectionBarArrayList.clear();
                Gson gsons=new Gson();
                Type type1=new TypeToken<List<UtilSectionBar>>(){}.getType();
                utilSectionBarArrayList=gsons.fromJson(SectionBottlesListInString,type1);

            }catch (Exception e){
                e.printStackTrace();
            }
            if(utilSectionBarArrayList!=null && utilSectionBarArrayList.size()!=0){

                section_bar_recycler=(RecyclerView)findViewById(R.id.section_bar_recycler);
                adapter=new SectionBarAdapter(SectionBottlesActivity.this,utilSectionBarArrayList,this,this);
                ItemTouchHelper.Callback callback = new SimpleItemHelperBottles(adapter);
                mItemTouchHelper = new ItemTouchHelper(callback);
                mItemTouchHelper.attachToRecyclerView(section_bar_recycler);

                RecyclerView.LayoutManager layoutManagersecond=new LinearLayoutManager(getApplicationContext());
                section_bar_recycler.setLayoutManager(layoutManagersecond);
                section_bar_recycler.setItemAnimator(new DefaultItemAnimator());
                section_bar_recycler.setHasFixedSize(true);
                section_bar_recycler.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }else{
                Toast.makeText(getApplicationContext(), "List is empty !", Toast.LENGTH_SHORT).show();
            }

        }
        else{

            section_bar_recycler=(RecyclerView)findViewById(R.id.section_bar_recycler);
            RecyclerView.LayoutManager layoutManagersecond=new LinearLayoutManager(getApplicationContext());

            adapter=new SectionBarAdapter(SectionBottlesActivity.this,utilSectionBarArrayList,this,this);
            ItemTouchHelper.Callback callback = new SimpleItemHelperBottles(adapter);
            mItemTouchHelper = new ItemTouchHelper(callback);
            mItemTouchHelper.attachToRecyclerView(section_bar_recycler);

            section_bar_recycler.setLayoutManager(layoutManagersecond);
            section_bar_recycler.setItemAnimator(new DefaultItemAnimator());
            section_bar_recycler.setHasFixedSize(true);
            section_bar_recycler.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        bottles_swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                bottles_swipe.setRefreshing(true);
                GetBottlesList();

            }
        });

    }

    private ArrayList<UtilSectionBar>filter(ArrayList<UtilSectionBar>movies,String query){
        query=query.toLowerCase();
        final ArrayList<UtilSectionBar>filterdlist=new ArrayList<>();
        for(UtilSectionBar movie:movies){
            final String text=movie.getLiquorname().toLowerCase();
            if(text.contains(query)){
                filterdlist.add(movie);
            }
        }
        return filterdlist;
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
                bottles_swipe.setRefreshing(false);
                try {

                    JSONObject obj=new JSONObject(response.toString());
                    String message=obj.getString("Message");
                    boolean success=obj.getBoolean("IsSuccess");
                    if (success){
                        utilSectionBarArrayList.clear();

                        JSONArray array=obj.getJSONArray("Model");
                        for (int i=0;i<array.length();i++){
                            JSONObject first=array.getJSONObject(i);
                            int id=first.getInt("Id");
                            int userprofile=first.getInt("UserProfileId");
                            int barid=first.getInt("BarId");
                            int sectionid=first.getInt("SectionId");
                            String minvalue=first.getString("MinValue");
                            String maxvalue=first.getString("MaxValue");
                            String liquorname=first.getString("LiquorName");
                            String liquorcapacity=null;
                            try{
                                liquorcapacity=first.getString("LiquorCapacity");

                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            String shots=first.getString("Shots");
                            String category=first.getString("Category");
                            String subcategory=first.getString("SubCategory");
                            String parlevel=first.getString("ParLevel");
                            String distributorname=first.getString("DistributorName");
                            String priceunit=first.getString("Price");
                            String binnumber=first.getString("BinNumber");
                            String productcode=first.getString("ProductCode");
                            String createdon=first.getString("CreatedOn");
                            String pictureurl=first.getString("PictureURL");
                            String totalbottles=null;
                            try {
                                totalbottles=first.getString("TotalBottles");
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            String type=null;
                            try {
                                type=first.getString("Type");
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            String fullweight=null;
                            try {
                                fullweight=first.getString("FullWeight");
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            String emptyweight=null;
                            try {
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
                            utilSectionBar.setBottleId(id);
                            utilSectionBar.setPictureurl(pictureurl);
                            try{
                                utilSectionBar.setMinvalue(Double.parseDouble(minvalue));
                                utilSectionBar.setMaxvalue(Double.parseDouble(maxvalue));
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                            utilSectionBar.setTotalbottles(totalbottles);
                            utilSectionBar.setType(type);
                            utilSectionBar.setFullweight(fullweight);
                            utilSectionBar.setEmptyweight(emptyweight);
                            utilSectionBarArrayList.add(utilSectionBar);
                        }


                    }else {
                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                        store.putSectionBottles("SectionBottles"+SectionId,null);

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
                    if(adapter!=null){
                        adapter.notifyDataSetChanged();
                    }

                try{
                    Gson gson=new Gson();
                    String bottlestring=gson.toJson(utilSectionBarArrayList);
                    store.putSectionBottles("SectionBottles"+SectionId,bottlestring);

                }catch (Exception e){
                    Log.d("exception_conve_gson",e.getMessage());
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                bottles_swipe.setRefreshing(false);

                Toast.makeText(getApplicationContext(),"Not Working",Toast.LENGTH_SHORT).show();

//                texts.setText(error.toString());
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
    protected void onResume() {
        super.onResume();
        store=PreferenceManager.getInstance(getApplicationContext());
        SectionBottlesListInString=store.getSection("SectionBottles"+SectionId);
        String putSectionBottles=store.getSectionBottles("SectionBottles"+SectionId);

        if(putSectionBottles==null){
            GetBottlesList();
        }else{
            Log.d("resumetext",putSectionBottles);

            try{
                utilSectionBarArrayList.clear();
                Gson gsons=new Gson();
                Type type1=new TypeToken<List<UtilSectionBar>>(){}.getType();
                utilSectionBarArrayList=gsons.fromJson(putSectionBottles,type1);

            }catch (Exception e){
                e.printStackTrace();
            }
            if(utilSectionBarArrayList!=null && utilSectionBarArrayList.size()!=0){

                section_bar_recycler=(RecyclerView)findViewById(R.id.section_bar_recycler);
                adapter=new SectionBarAdapter(SectionBottlesActivity.this,utilSectionBarArrayList,this,this);
                ItemTouchHelper.Callback callback = new SimpleItemHelperBottles(adapter);
                mItemTouchHelper = new ItemTouchHelper(callback);
                mItemTouchHelper.attachToRecyclerView(section_bar_recycler);

                RecyclerView.LayoutManager layoutManagersecond=new LinearLayoutManager(getApplicationContext());
                section_bar_recycler.setLayoutManager(layoutManagersecond);
                section_bar_recycler.setItemAnimator(new DefaultItemAnimator());
                section_bar_recycler.setHasFixedSize(true);
                section_bar_recycler.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }else{
                Toast.makeText(getApplicationContext(), "List is empty  resume!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                Intent i=new Intent(SectionBottlesActivity.this,AddSectionActivity.class);
                startActivity(i);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewholder) {
        mItemTouchHelper.startDrag(viewholder);

    }

    @Override
    public void onBottleListChanged(ArrayList<UtilSectionBar> utilSectionBarArrayList) {
        try{
            Gson gson=new Gson();
            String sectionbottleslist=gson.toJson(utilSectionBarArrayList);
            Log.d("Changed SectionList",sectionbottleslist);
            store.putSection("SectionBottles"+SectionId,sectionbottleslist);

        }catch (Exception e){
            Log.d("exception_conve_gson",e.getMessage());
        }
    }
}