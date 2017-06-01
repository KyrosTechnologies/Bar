package com.kyros.technologies.bar.Purchase.Activity.Activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kyros.technologies.bar.R;
import com.kyros.technologies.bar.ServiceHandler.ServiceHandler;
import com.kyros.technologies.bar.SharedPreferences.PreferenceManager;
import com.kyros.technologies.bar.utils.EndURL;

import org.json.JSONArray;
import org.json.JSONObject;

public class BottlePurchaseStock extends AppCompatActivity {

    private String bottlename=null;
    private String bottlecapacity=null;
    private String bottlecategory=null;
    private String bottlesubcategory=null;
    private EditText pur_bottle_des_name,pur_bottle_des_capacity,pur_bottle_des_main_category,pur_bottle_des_sub_category,pur_bottle_des_shots,pur_bottle_des_par_level,
            pur_bottle_des_distributor_name,pur_bottle_des_price_unit,pur_bottle_des_bin_number,pur_bottle_des_product_code;
    private int  userprofile;
    private int barid;
    private int id;
    private int sectionid;
    private String liquorname;
    private String liquorcapacity;
    private String shots;
    private String category;
    private String subcategory;
    private String parlevel;
    private String distributor;
    private String price;
    private String binnumber;
    private String productcode;
    private PreferenceManager store;
    private String UserProfileId=null;
    private String Barid=null;
    private String Sectionid=null;
    private String LiquorName=null;
    private String LiquorCapacity=null;
    private String Shots=null;
    private String Category=null;
    private String SubCategory=null;
    private String ParLevel=null;
    private String DistributorName=null;
    private String PriceUnit=null;
    private String BinNumber=null;
    private String ProductCode=null;
    private String CreatedOn=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_purchase_bottle_description);
        pur_bottle_des_name=(EditText)findViewById(R.id.pur_bottle_des_name);
        pur_bottle_des_capacity=(EditText)findViewById(R.id.pur_bottle_des_capacity);
        pur_bottle_des_main_category=(EditText)findViewById(R.id.pur_bottle_des_main_category);
        pur_bottle_des_sub_category=(EditText)findViewById(R.id.pur_bottle_des_sub_category);
        pur_bottle_des_shots=(EditText)findViewById(R.id.pur_bottle_des_shots);
        pur_bottle_des_par_level=(EditText)findViewById(R.id.pur_bottle_des_par_level);
        pur_bottle_des_distributor_name=(EditText)findViewById(R.id.pur_bottle_des_distributor_name);
        pur_bottle_des_price_unit=(EditText)findViewById(R.id.pur_bottle_des_price_unit);
        pur_bottle_des_bin_number=(EditText)findViewById(R.id.pur_bottle_des_bin_number);
        pur_bottle_des_product_code=(EditText)findViewById(R.id.pur_bottle_des_product_code);
        store= PreferenceManager.getInstance(getApplicationContext());
        UserProfileId=store.getUserProfileId();
        Barid=store.getBarId();
        Sectionid=store.getSectionId();
        LiquorName=store.getLiquorName();
        LiquorCapacity=store.getLiquorCapacity();
        Shots=store.getShots();
        Category=store.getCategory();
        SubCategory=store.getSubCategory();
        ParLevel=store.getParLevel();
        DistributorName=store.getDistributorName();
        PriceUnit=store.getPriceUnit();
        BinNumber=store.getBinNumber();
        ProductCode=store.getProductCode();
        CreatedOn=store.getBarDateCreated();

        try {

            Bundle bundle=getIntent().getExtras();
            bottlename=bundle.getString("name");
            bottlecapacity=bundle.getString("capacity");
            bottlecategory=bundle.getString("category");
            bottlesubcategory=bundle.getString("subcategory");

            try {

                pur_bottle_des_name.setText(bottlename);
                pur_bottle_des_capacity.setText(bottlecapacity);
                pur_bottle_des_main_category.setText(bottlecategory);
                pur_bottle_des_sub_category.setText(bottlesubcategory);

            }catch (Exception e){
                e.printStackTrace();
            }



        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void PurchaseBottleDescriptionApi(final int userprofile , String liquorname,String liquorcapacity,String category,String subcategory,String shots,
                                      String parlevel,String distributor,String price,String binnumber,String productcode) {
        String tag_json_obj = "json_obj_req";
        String url = EndURL.URL+"insertPurchaseList";
        //  String url = "http://192.168.0.109:8080/Bar/rest/getLiquorList";
        Log.d("waggonurl", url);
        final JSONObject inputLogin=new JSONObject();
        try{
            inputLogin.put("userprofileid",userprofile);
            inputLogin.put("liquorname",liquorname);
            inputLogin.put("liquorcapacity",liquorcapacity);
            inputLogin.put("shots",shots);
            inputLogin.put("category",category);
            inputLogin.put("subcategory",subcategory);
            inputLogin.put("parlevel",parlevel);
            inputLogin.put("distributorname",distributor);
            inputLogin.put("priceunit",price);
            inputLogin.put("binnumber",binnumber);
            inputLogin.put("productcode",productcode);


        }catch (Exception e){
            e.printStackTrace();
        }
        Log.d("inputJsonuser",inputLogin.toString());
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, url, inputLogin, new Response.Listener<JSONObject>() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onResponse(JSONObject response) {
                Log.d("List Response",response.toString());
                try {

                    JSONObject obj=new JSONObject(response.toString());
                    String message=obj.getString("Message");
                    boolean success=obj.getBoolean("IsSuccess");
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
                            String subcategory=first.getString("subcategory");
                            String parlevel=first.getString("parlevel");
                            String distributorname=first.getString("distributorname");
                            String priceunit=first.getString("priceunit");
                            String binnumber=first.getString("binnumber");
                            String productcode=first.getString("productcode");
                            String createdon=first.getString("createdon");

                        }

                        Toast.makeText(getApplicationContext(),"Sucessfully Updated Bottle",Toast.LENGTH_SHORT).show();
                        BottlePurchaseStock.this.finish();

                    }else {
                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

                Intent i=new Intent(BottlePurchaseStock.this,PurchaseListActivity.class);
                startActivity(i);
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
        getMenuInflater().inflate(R.menu.menu_done,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){


            case android.R.id.home:
                BottlePurchaseStock.this.finish();
                return true;
            case R.id.action_done:
                Toast.makeText(getApplicationContext(),"Saved successfully!",Toast.LENGTH_SHORT).show();
                Barid=store.getBarId();
                Sectionid=store.getSectionId();
                String name =pur_bottle_des_name.getText().toString();
                String capacity= pur_bottle_des_capacity.getText().toString();
                String maincat=pur_bottle_des_main_category.getText().toString();
                String subcat=pur_bottle_des_sub_category.getText().toString();
                String shots=pur_bottle_des_shots.getText().toString();
                String parlevel=pur_bottle_des_par_level.getText().toString();
                String disname=pur_bottle_des_distributor_name.getText().toString();
                String price=pur_bottle_des_price_unit.getText().toString();
                String bin=pur_bottle_des_bin_number.getText().toString();
                String product=pur_bottle_des_product_code.getText().toString();
                Log.d("Result",name+capacity+maincat+subcat+shots+parlevel+disname+price+bin+product);
                PurchaseBottleDescriptionApi(Integer.parseInt(UserProfileId),name,capacity,maincat,subcat,shots,parlevel,disname,price,bin,product);

                break;

        }

        return super.onOptionsItemSelected(item);

    }

}
