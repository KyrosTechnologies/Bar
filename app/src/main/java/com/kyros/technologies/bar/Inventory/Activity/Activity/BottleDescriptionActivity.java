package com.kyros.technologies.bar.Inventory.Activity.Activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kyros.technologies.bar.R;
import com.kyros.technologies.bar.ServiceHandler.ServiceHandler;
import com.kyros.technologies.bar.SharedPreferences.PreferenceManager;
import com.kyros.technologies.bar.utils.AndroidMultiPartEntity;
import com.kyros.technologies.bar.utils.EndURL;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class BottleDescriptionActivity extends AppCompatActivity {

    private String bottlename=null;
    private String bottlecapacity=null;
    private String bottlecategory=null;
    private String bottlesubcategory=null;
    private EditText bottle_des_name,bottle_des_capacity,bottle_des_main_category,bottle_des_sub_category,bottle_des_shots,bottle_des_par_level,
            bottle_des_distributor_name,bottle_des_price_unit,bottle_des_bin_number,bottle_des_product_code;
    private ImageView bott_image;
    private PreferenceManager store;
    private String UserProfileId=null;
    private String Barid=null;
    private String Sectionid=null;
    private Bitmap bitmapvar;
    private byte[] bytearayProfile;
    private String MinHeight=null;
    private String MaxHeight=null;
    private String parlevel=null;
    private String shots=null;
    private String disname=null;
    private String price=null;
    private String binnumber=null;
    private String productcode=null;
    private String fullweight=null;
    private String emptyweight=null;
    private String type=null;
    private String id=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_bottle_description);
        bottle_des_name=(EditText)findViewById(R.id.bottle_des_name);
        bottle_des_capacity=(EditText)findViewById(R.id.bottle_des_capacity);
        bottle_des_main_category=(EditText)findViewById(R.id.bottle_des_main_category);
        bottle_des_sub_category=(EditText)findViewById(R.id.bottle_des_sub_category);
        bottle_des_shots=(EditText)findViewById(R.id.bottle_des_shots);
        bottle_des_par_level=(EditText)findViewById(R.id.bottle_des_par_level);
        bottle_des_distributor_name=(EditText)findViewById(R.id.bottle_des_distributor_name);
        bottle_des_price_unit=(EditText)findViewById(R.id.bottle_des_price_unit);
        bottle_des_bin_number=(EditText)findViewById(R.id.bottle_des_bin_number);
        bottle_des_product_code=(EditText)findViewById(R.id.bottle_des_product_code);
        bott_image=(ImageView)findViewById(R.id.bott_image);
        store= PreferenceManager.getInstance(getApplicationContext());
        Toast.makeText(getApplicationContext(),"picture",Toast.LENGTH_SHORT).show();
        UserProfileId=store.getUserProfileId();
        Barid=store.getBarId();
        Sectionid=store.getSectionId();
//        LiquorName=store.getLiquorName();
//        LiquorCapacity=store.getLiquorCapacity();
//        Shots=store.getShots();
//        Category=store.getCategory();
//        SubCategory=store.getSubCategory();
//        ParLevel=store.getParLevel();
//        DistributorName=store.getDistributorName();
//        PriceUnit=store.getPriceUnit();
//        BinNumber=store.getBinNumber();
//        ProductCode=store.getProductCode();
//        CreatedOn=store.getBarDateCreated();

        try {

            Bundle bundle=getIntent().getExtras();
            bottlename=bundle.getString("name");
            bottlecapacity=bundle.getString("capacity");
            bottlecategory=bundle.getString("category");
            bottlesubcategory=bundle.getString("subcategory");
            String imgbitmap=bundle.getString("image");
            MinHeight=bundle.getString("minheight");
            MaxHeight=bundle.getString("maxheight");
            parlevel=bundle.getString("parlevel");
            shots=bundle.getString("shots");
            disname=bundle.getString("disname");
            price=bundle.getString("price");
            binnumber=bundle.getString("binnumber");
            productcode=bundle.getString("productcode");
            type=bundle.getString("type");
            fullweight=bundle.getString("fullweight");
            emptyweight=bundle.getString("emptyweight");
            id=bundle.getString("id");
//            Log.d("Pictureurl",imgbitmap);
            try{
                Picasso.with(BottleDescriptionActivity.this).load(imgbitmap).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        bott_image.setImageBitmap(bitmap);
                        bitmapvar=bitmap;
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }

            try {

                bottle_des_name.setText(bottlename);
                bottle_des_capacity.setText(bottlecapacity);
                bottle_des_main_category.setText(bottlecategory);
                bottle_des_sub_category.setText(bottlesubcategory);
                bottle_des_par_level.setText(parlevel);
                bottle_des_shots.setText(shots);
                bottle_des_distributor_name.setText(disname);
                bottle_des_price_unit.setText(price);
                bottle_des_bin_number.setText(binnumber);
                bottle_des_product_code.setText(productcode);

            }catch (Exception e){
                e.printStackTrace();
            }



        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void BottleDescriptionApi(final int userprofile , int barid, int sectionid,String liquorname,String liquorcapacity,String category,String subcategory,String shots,
                                      String parlevel,String distributor,String price,String binnumber,String productcode) {
        String tag_json_obj = "json_obj_req";
        String url = EndURL.URL+"insertUserLiquorlist";
        //  String url = "http://192.168.0.109:8080/Bar/rest/getLiquorList";
        Log.d("waggonurl", url);
        final JSONObject inputLogin=new JSONObject();
        try{
            inputLogin.put("userprofileid",userprofile);
            inputLogin.put("barid",barid);
            inputLogin.put("sectionid",sectionid);
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
                            String liquorname=first.getString("liquorname");
                            String liquorcapacity=first.getString("liquorcapacity");
                            String shots=first.getString("shots");
                            String category=first.getString("category");
                            String subcategory=first.getString("subcategory");
                            String parlevel=first.getString("parlevel");
                            String distributorname=first.getString("distributorname");
                            int priceunit=first.getInt("priceunit");
                            String binnumber=first.getString("binnumber");
                            String productcode=first.getString("productcode");
                            String createdon=first.getString("createdon");

                        }

                        Toast.makeText(getApplicationContext(),"Sucessfully Logged In",Toast.LENGTH_SHORT).show();
                        BottleDescriptionActivity.this.finish();


                    }else {
                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

                Intent i=new Intent(BottleDescriptionActivity.this,SectionBottlesActivity.class);
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
                BottleDescriptionActivity.this.finish();
                return true;
            case R.id.action_done:
                Toast.makeText(getApplicationContext(),"Saved successfully!",Toast.LENGTH_SHORT).show();
                Barid=store.getBarId();
                Sectionid=store.getSectionId();
                String name =bottle_des_name.getText().toString();
                String capacity= bottle_des_capacity.getText().toString();
                String maincat=bottle_des_main_category.getText().toString();
                String subcat=bottle_des_sub_category.getText().toString();
                String shots=bottle_des_shots.getText().toString();
                String parlevel=bottle_des_par_level.getText().toString();
                String disname=bottle_des_distributor_name.getText().toString();
                String price=bottle_des_price_unit.getText().toString();
                String bin=bottle_des_bin_number.getText().toString();
                String product=bottle_des_product_code.getText().toString();
                Log.d("Result",name+capacity+maincat+subcat+shots+parlevel+disname+price+bin+product);
//                BottleDescriptionApi(Integer.parseInt(UserProfileId),Integer.parseInt(Barid),Integer.parseInt(Sectionid),name,capacity,maincat,subcat,shots,parlevel,disname,price,bin,product);

                try{
                    Async i= new Async();
                    i.execute();
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;

        }

        return super.onOptionsItemSelected(item);
    }
    private class Async extends AsyncTask<String,String,String > {

        @Override
        protected String doInBackground(String... params) {

            uploadFile();

            return null;
        }
    }
    private String uploadFile(){
        Barid=store.getBarId();
        Sectionid=store.getSectionId();
        String name =bottle_des_name.getText().toString();
        String capacity= bottle_des_capacity.getText().toString();
        String maincat=bottle_des_main_category.getText().toString();
        String subcat=bottle_des_sub_category.getText().toString();
        String shots=bottle_des_shots.getText().toString();
        String parlevel=bottle_des_par_level.getText().toString();
        String disname=bottle_des_distributor_name.getText().toString();
        String price=bottle_des_price_unit.getText().toString();
        String bin=bottle_des_bin_number.getText().toString();
        String product=bottle_des_product_code.getText().toString();
        Log.d("Result",name+capacity+maincat+subcat+shots+parlevel+disname+price+bin+product);
        String responseString = null;

        HttpClient httpclient = new DefaultHttpClient();
        String url = EndURL.URL + "insertUserLiquorlistM";
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmapvar.compress(Bitmap.CompressFormat.PNG, 0, stream);
//        int bys=bitmapvar.getByteCount();
//        ByteBuffer buffer=ByteBuffer.allocate(bys);
//        bitmapvar.copyPixelsFromBuffer(buffer);
//        byte[] arrs=buffer.array();
        bytearayProfile = stream.toByteArray();
        HttpPost httppost = new HttpPost(url);

        try {
            AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                    new AndroidMultiPartEntity.ProgressListener() {

                        @Override
                        public void transferred(long num) {
                        }
                    });




            double minval=Double.parseDouble(MinHeight);
            double maxval=Double.parseDouble(MaxHeight);
            minval=minval/100;
            maxval=maxval/100;
            String fminval=String.valueOf(minval);
            String fmaxval=String.valueOf(maxval);
            entity.addPart("image", new ByteArrayBody(bytearayProfile, UserProfileId + "liq.png"));
            entity.addPart("userprofileid", new StringBody(UserProfileId, ContentType.TEXT_PLAIN));
            entity.addPart("barid", new StringBody(Barid, ContentType.TEXT_PLAIN));
            entity.addPart("sectionid", new StringBody(Sectionid, ContentType.TEXT_PLAIN));
            entity.addPart("liquorname", new StringBody(name, ContentType.TEXT_PLAIN));
            entity.addPart("liquorquantitiy", new StringBody(capacity, ContentType.TEXT_PLAIN));
            entity.addPart("category", new StringBody(bottlecategory, ContentType.TEXT_PLAIN));
            entity.addPart("subcategory", new StringBody(bottlesubcategory, ContentType.TEXT_PLAIN));
            entity.addPart("parvalue", new StringBody(parlevel, ContentType.TEXT_PLAIN));
            entity.addPart("distributorname", new StringBody(disname, ContentType.TEXT_PLAIN));
            entity.addPart("price", new StringBody(price, ContentType.TEXT_PLAIN));
            entity.addPart("binnumber", new StringBody(bin, ContentType.TEXT_PLAIN));
            entity.addPart("productcode", new StringBody(product, ContentType.TEXT_PLAIN));
            entity.addPart("minvalue", new StringBody(fminval, ContentType.TEXT_PLAIN));
            entity.addPart("maxvalue", new StringBody(fmaxval, ContentType.TEXT_PLAIN));
            entity.addPart("shots", new StringBody(shots, ContentType.TEXT_PLAIN));
            entity.addPart("type",new StringBody("bottle",ContentType.TEXT_PLAIN));

            long totalSize = entity.getContentLength();
            httppost.setEntity(entity);

            // Making server call
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity r_entity = response.getEntity();
            try{
                Log.d("outputentity",entity.toString());
            }catch(Exception e){
                e.printStackTrace();
            }

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                // Server response
                responseString = EntityUtils.toString(r_entity);
                BottleDescriptionActivity.this.finish();
                //    Toast.makeText(getApplicationContext(),"Uploaded successfully",Toast.LENGTH_SHORT).show();
            } else {
                responseString = "Error occurred! Http Status Code: "
                        + statusCode;
                //  Toast.makeText(getApplicationContext(),"Error occured",Toast.LENGTH_SHORT).show();

            }
            Log.d("response: ",responseString);


        } catch (ClientProtocolException e) {
            responseString = e.toString();
        } catch (IOException e) {
            responseString = e.toString();
        }

        return responseString;

    }


}
