package com.kyros.technologies.bar.Inventory.Activity.Activity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.kyros.technologies.bar.R;
import com.kyros.technologies.bar.ServiceHandler.ServiceHandler;
import com.kyros.technologies.bar.SharedPreferences.PreferenceManager;
import com.kyros.technologies.bar.utils.AndroidMultiPartEntity;
import com.kyros.technologies.bar.utils.EndURL;
import com.kyros.technologies.bar.utils.UtilSectionBar;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
  //  private String type=null;
    private String BottleId=null;
    private String WhichType=null;
    private Async i=null;
    private  UpdateAsync ups=null;
    private AlertDialog online,alertDialog;
    private ProgressDialog pDialog;

    private ArrayList<UtilSectionBar> utilSectionBarArrayList=new ArrayList<UtilSectionBar>();
    private String SectionId=null;
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
        UserProfileId=store.getUserProfileId();
        SectionId=store.getSectionId();
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
            bottlename=bundle.getString("liquorname");

            bottlecapacity=bundle.getString("liquorcapacity");
            bottlecategory=bundle.getString("category");
            bottlesubcategory=bundle.getString("subcategory");
            String imgbitmap=bundle.getString("image");
            MinHeight=bundle.getString("minvalue");
            MaxHeight=bundle.getString("maxvalue");
         Log.d("MIN,Max Values: ","are : "+MinHeight+" max height : "+MaxHeight);
            parlevel=bundle.getString("parvalue");
            shots=bundle.getString("shots");
            disname=bundle.getString("distributorname");
            price=bundle.getString("price");
            binnumber=bundle.getString("binnumber");
            productcode=bundle.getString("productcode");
          //  type=bundle.getString("type");
          //  fullweight=bundle.getString("fullweight");
          // emptyweight=bundle.getString("emptyweight");
            int bottid=bundle.getInt("bottleid");
            BottleId=String.valueOf(bottid);
            Log.d("bottleId",BottleId);

            WhichType=bundle.getString("whichtype");
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

                if(bottlename!=null){
                    bottle_des_name.setText(bottlename);
                }
                if(bottlecapacity!=null){
                    bottle_des_capacity.setText(bottlecapacity);

                }
                if(bottlecategory!=null){
                    bottle_des_main_category.setText(bottlecategory);

                }
                if(bottlesubcategory!=null){
                    bottle_des_sub_category.setText(bottlesubcategory);

                }
                if(parlevel!=null){
                    bottle_des_par_level.setText(parlevel);

                }
                if(shots!=null){
                    bottle_des_shots.setText(shots);

                }
                if(disname!=null){
                    bottle_des_distributor_name.setText(disname);

                }
                if(price!=null){
                    bottle_des_price_unit.setText(price);

                }
                if(binnumber!=null){
                    bottle_des_bin_number.setText(binnumber);

                }
                if(productcode!=null){
                    bottle_des_product_code.setText(productcode);

                }

            }catch (Exception e){
                e.printStackTrace();
            }



        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public boolean checkOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            return true;

        }else {
            onlineDialog();

        }

        return false;
    }

    public void onlineDialog(){
        online= new AlertDialog.Builder(BottleDescriptionActivity.this).create();
        online.setTitle("No Internet Connection");
        online.setMessage("We cannot detect any internet connectivity.Please check your internet connection and try again");
        //   alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
        online.setButton("Try Again",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                checkOnline();
            }
        });
        online.show();

    }
    private void dismissonlineDialog(){
        if(online!=null && online.isShowing()){
            online.dismiss();
        }
    }

    private void showProgressDialog() {
        if (pDialog == null) {
            pDialog = new ProgressDialog(BottleDescriptionActivity.this);
            pDialog.setMessage("Loading. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
        }
        pDialog.show();
    }

    private void dismissProgressDialog() {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }
    private void showErrorDialog() {
        if (alertDialog == null) {
            alertDialog= new AlertDialog.Builder(BottleDescriptionActivity.this).create();
            alertDialog.setTitle("Network/Connection Error");
            alertDialog.setMessage(getString(R.string.server_error_dialog));
            //   alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
            alertDialog.setButton("Ok",new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    alertDialog.dismiss();
                }
            });
        }
        alertDialog.show();
    }

    private void dismissErrorDialog() {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
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
                showProgressDialog();

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
                        BottleDescriptionActivity.this.finish();


                    }else {
                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
                dismissProgressDialog();

                Intent i=new Intent(BottleDescriptionActivity.this,SectionBottlesActivity.class);
                startActivity(i);

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                dismissProgressDialog();
                showErrorDialog();
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
                    if(WhichType.equals("newone")){
                        Toast.makeText(getApplicationContext(),"Type is empty null new registration!",Toast.LENGTH_SHORT).show();
                        try{
                             i= new Async(BottleDescriptionActivity.this);
                            i.execute();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else if(WhichType.equals("update")){
                        Toast.makeText(getApplicationContext(),"Type is update the old data!",Toast.LENGTH_SHORT).show();

                        try{
                             ups=new UpdateAsync(BottleDescriptionActivity.this);
                            ups.execute();
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }

                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        dismissProgressDialog();
        try{
                i.cancel(true);
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            ups.cancel(true);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try{
            if(i!=null){
                i.cancel(true);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            if(ups!=null){
                ups.cancel(true);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        dismissonlineDialog();
        dismissProgressDialog();
        dismissErrorDialog();
    }

    private class Async extends AsyncTask<String,String,String > {
        Context mContext;
        public Async(Context mContext){
            this.mContext=mContext;
        }
        @Override
        protected String doInBackground(String... params) {

            uploadFile(mContext);

            return null;
        }
    }
    private String uploadFile(Context mContext){
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
        httppost.addHeader("Authorization",store.getUserProfileId()+"|"+store.getAuthorizationKey());
        try {
            AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                    new AndroidMultiPartEntity.ProgressListener() {

                        @Override
                        public void transferred(long num) {
                        }
                    });




            double minval=Double.parseDouble(MinHeight);
            double maxval=Double.parseDouble(MaxHeight);
            String fminval=String.valueOf(minval);
            String fmaxval=String.valueOf(maxval);
            entity.addPart("image", new ByteArrayBody(bytearayProfile, UserProfileId + "liq.png"));
            entity.addPart("userprofileid", new StringBody(UserProfileId, ContentType.TEXT_PLAIN));
            entity.addPart("barid", new StringBody(Barid, ContentType.TEXT_PLAIN));
            entity.addPart("sectionid", new StringBody(Sectionid, ContentType.TEXT_PLAIN));
            entity.addPart("liquorname", new StringBody(name, ContentType.TEXT_PLAIN));
            entity.addPart("liquorquantitiy", new StringBody(capacity, ContentType.TEXT_PLAIN));
            entity.addPart("category", new StringBody(maincat, ContentType.TEXT_PLAIN));
            entity.addPart("subcategory", new StringBody(subcat, ContentType.TEXT_PLAIN));
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
                Intent is=new Intent(mContext.getApplicationContext(),SectionBottlesActivity.class);
                is.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                mContext.startActivity(is);


                //   Toast.makeText(mContext.getApplicationContext(),"Uploaded successfully",Toast.LENGTH_SHORT).show();
            } else {
                responseString = "Error occurred! Http Status Code: "
                        + statusCode;
                //  Toast.makeText(mContext.getApplicationContext(),"Error occured",Toast.LENGTH_SHORT).show();

            }
            Log.d("response: ",responseString);

            if(responseString!=null){
                try {

                    JSONObject obj=new JSONObject(responseString);
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
                            String shots1=first.getString("Shots");
                            String category=first.getString("Category");
                            String subcategory=first.getString("SubCategory");
                            String parlevel1=first.getString("ParLevel");
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
                            utilSectionBar.setShots(shots1);
                            utilSectionBar.setCategory(category);
                            utilSectionBar.setSubcategory(subcategory);
                            utilSectionBar.setParlevel(parlevel1);
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
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
                try{
                    Gson gson=new Gson();
                    String bottlestring=gson.toJson(utilSectionBarArrayList);
                    store.putSectionBottles("SectionBottles"+SectionId,bottlestring);
                    Log.d("resumetextdescription",bottlestring);

                }catch (Exception e){
                    Log.d("exception_conve_gson",e.getMessage());
                }
            }


        } catch (ClientProtocolException e) {
            responseString = e.toString();
        } catch (IOException e) {
            responseString = e.toString();
        }

        return responseString;

    }
    private class UpdateAsync extends AsyncTask<String,String,String>{
        private Context mContext;
        public UpdateAsync(Context mContext){
           this.mContext=mContext;
        }
        @Override
        protected String doInBackground(String... params) {
            UpdateData(mContext);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(mContext.getApplicationContext(),"Successfully Updated!",Toast.LENGTH_SHORT).show();
        }
    }
    private String UpdateData(Context mContext){
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
        String url = EndURL.URL + "UpdateLiquorSlider";
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmapvar.compress(Bitmap.CompressFormat.PNG, 0, stream);
//        int bys=bitmapvar.getByteCount();
//        ByteBuffer buffer=ByteBuffer.allocate(bys);
//        bitmapvar.copyPixelsFromBuffer(buffer);
//        byte[] arrs=buffer.array();
        bytearayProfile = stream.toByteArray();
        HttpPut httppost = new HttpPut(url);
        httppost.addHeader("Authorization",store.getUserProfileId()+"|"+store.getAuthorizationKey());
        try {
            AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                    new AndroidMultiPartEntity.ProgressListener() {

                        @Override
                        public void transferred(long num) {
                        }
                    });




            double minval=Double.parseDouble(MinHeight);
            double maxval=Double.parseDouble(MaxHeight);
//            minval=minval/100;
//            maxval=maxval/100;
            String fminval=String.valueOf(minval);
            String fmaxval=String.valueOf(maxval);
            entity.addPart("BottleId", new StringBody(BottleId,ContentType.TEXT_PLAIN));
            entity.addPart("Image", new ByteArrayBody(bytearayProfile, UserProfileId + "liq.png"));
            entity.addPart("UserProfileId", new StringBody(UserProfileId, ContentType.TEXT_PLAIN));
            entity.addPart("BarId", new StringBody(Barid, ContentType.TEXT_PLAIN));
            entity.addPart("SectionId", new StringBody(Sectionid, ContentType.TEXT_PLAIN));
            entity.addPart("LiquorName", new StringBody(name, ContentType.TEXT_PLAIN));
            entity.addPart("LiquorCapacity", new StringBody(capacity, ContentType.TEXT_PLAIN));
            entity.addPart("Category", new StringBody(maincat, ContentType.TEXT_PLAIN));
            entity.addPart("SubCategory", new StringBody(subcat, ContentType.TEXT_PLAIN));
            entity.addPart("ParValue", new StringBody(parlevel, ContentType.TEXT_PLAIN));
            entity.addPart("DistributorName", new StringBody(disname, ContentType.TEXT_PLAIN));
            entity.addPart("Price", new StringBody(price, ContentType.TEXT_PLAIN));
            entity.addPart("BinNumber", new StringBody(bin, ContentType.TEXT_PLAIN));
            entity.addPart("ProductCode", new StringBody(product, ContentType.TEXT_PLAIN));
            entity.addPart("MinValue", new StringBody(fminval, ContentType.TEXT_PLAIN));
            entity.addPart("MaxValue", new StringBody(fmaxval, ContentType.TEXT_PLAIN));
            entity.addPart("Shots", new StringBody(shots, ContentType.TEXT_PLAIN));
            entity.addPart("Type",new StringBody("bottle",ContentType.TEXT_PLAIN));

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
                Intent is=new Intent(mContext.getApplicationContext(),SectionBottlesActivity.class);
                is.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                mContext.startActivity(is);
             //       Toast.makeText(mContext.getApplicationContext(),"Uploaded successfully",Toast.LENGTH_SHORT).show();
            } else {
                responseString = "Error occurred! Http Status Code: "
                        + statusCode;
              //    Toast.makeText(mContext.getApplicationContext(),"Error occured",Toast.LENGTH_SHORT).show();

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
