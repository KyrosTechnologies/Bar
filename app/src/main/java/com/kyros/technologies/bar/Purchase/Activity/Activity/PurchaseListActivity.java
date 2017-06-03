package com.kyros.technologies.bar.Purchase.Activity.Activity;

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
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.LinearLayout;
        import android.widget.Toast;

        import com.android.volley.Request;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.JsonObjectRequest;
        import com.kyros.technologies.bar.Purchase.Activity.Adapters.PurchaseListAdapter;
        import com.kyros.technologies.bar.Common.activity.Activity.LandingActivity;
        import com.kyros.technologies.bar.Inventory.Activity.Activity.InventoryActivity;
        import com.kyros.technologies.bar.R;
        import com.kyros.technologies.bar.ServiceHandler.ServiceHandler;
        import com.kyros.technologies.bar.SharedPreferences.PreferenceManager;
        import com.kyros.technologies.bar.utils.EndURL;
        import com.kyros.technologies.bar.utils.Purchase;

        import org.json.JSONArray;
        import org.json.JSONObject;

        import java.util.ArrayList;

public class PurchaseListActivity extends AppCompatActivity {
    private LinearLayout my_inventory_list;
    private RecyclerView purchase_recycler;
    private PurchaseListAdapter adapter;
    private String UserprofileId=null;
    private ArrayList<Purchase>purchaseArrayList=new ArrayList<Purchase>();
    private PreferenceManager store;

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
        adapter=new PurchaseListAdapter(PurchaseListActivity.this,purchaseArrayList);
        RecyclerView.LayoutManager layoutManagersecond=new LinearLayoutManager(getApplicationContext());
        purchase_recycler.setLayoutManager(layoutManagersecond);
        purchase_recycler.setItemAnimator(new DefaultItemAnimator());
        purchase_recycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        Toast.makeText(getApplicationContext(),"working",Toast.LENGTH_SHORT).show();

        my_inventory_list=(LinearLayout)findViewById(R.id.my_inventory_list);
        adapter.notifyDataSetChanged();
        my_inventory_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PurchaseListActivity.this,InventoryTypePurchase.class);
                startActivity(intent);
            }
        });
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
                            String subcategory=null;
                            try {
                                subcategory=first.getString("subcategory");

                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            String parlevel=first.getString("parlevel");
                            String distributorname=first.getString("distributorname");
                            String priceunit=first.getString("price");
                            String binnumber=first.getString("binnumber");
                            String productcode=first.getString("productcode");
                            String createdon=first.getString("createdon");
                            String minvalue=first.getString("minvalue");
                            String maxvalue=first.getString("maxvalue");
                            String pictureurl=first.getString("pictureurl");
                            String type=first.getString("type");
                            String fullweight=first.getString("fullweight");
                            String emptyweight=first.getString("emptyweight");
                            Purchase purchase=new Purchase();
                            purchase.setLiquorname(liquorname);
                            purchase.setMinvalue(minvalue);
                            purchase.setMaxvalue(maxvalue);
                            purchase.setPictureurl(pictureurl);
                            purchase.setType(type);
                            purchase.setFullweight(fullweight);
                            purchase.setEmptyweight(emptyweight);
                            purchase.setUserprofileid(userprofile);
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