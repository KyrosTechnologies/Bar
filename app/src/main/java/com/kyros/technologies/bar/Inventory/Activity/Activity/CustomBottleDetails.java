package com.kyros.technologies.bar.Inventory.Activity.Activity;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kyros.technologies.bar.R;
import com.kyros.technologies.bar.ServiceHandler.ServiceHandler;
import com.kyros.technologies.bar.SharedPreferences.PreferenceManager;
import com.kyros.technologies.bar.multipart.VolleyMultiPartRequest;
import com.kyros.technologies.bar.multipart.VolleySingleton;
import com.kyros.technologies.bar.utils.AndroidMultiPartEntity;
import com.kyros.technologies.bar.utils.CustomLiquorModel;
import com.kyros.technologies.bar.utils.EndURL;
import com.kyros.technologies.bar.utils.TestImage;

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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rohin on 05-05-2017.
 */

public class CustomBottleDetails extends AppCompatActivity {
    private ImageView image_custombottle;
    private EditText name_custombottle,capacity_custombottle,category_custombottle,subcategory_custombottle,
            shots_custombottle,parlevel_custombottle,distributor_custombottle,price_custombottle,
            binnumber_custombottle,producr_custombottle;
    private byte[] multipartBody;

    private byte [] picturebyte=null;
    private String MinValue="";
    private String MaxValue="";
    private String BarId=null;
    private String SectionId=null;
    private PreferenceManager store;
    private String UserProfileId=null;
    private String path=null;
    private Bitmap bitmap;
    private String baseimage=null;
    private final String boundary = "apiclient-" + System.currentTimeMillis();
    private final String mimeType = "multipart/form-data;boundary=" + boundary;
    private byte[] bytearayProfile;
    private String name;
    private String  capacity;
    private String  category;
    private String  subcategory;
    private String parlevel;
    private String  shots;
    private String  distributor;
    private String price;
    private String binnumber;
    private String  productcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.custom_bottle);
        store=PreferenceManager.getInstance(getApplicationContext());
        BarId=store.getBarId();
        SectionId=store.getSectionId();
        UserProfileId=store.getUserProfileId();
        image_custombottle=(ImageView)findViewById(R.id.image_custombottle);
        name_custombottle=(EditText)findViewById(R.id.name_custombottle);
        capacity_custombottle=(EditText)findViewById(R.id.capacity_custombottle);
        category_custombottle=(EditText)findViewById(R.id.category_custombottle);
        subcategory_custombottle=(EditText)findViewById(R.id.subcategory_custombottle);
        shots_custombottle=(EditText)findViewById(R.id.shots_custombottle);
        parlevel_custombottle=(EditText)findViewById(R.id.parlevel_custombottle);
        distributor_custombottle=(EditText)findViewById(R.id.distributor_custombottle);
        price_custombottle=(EditText)findViewById(R.id.price_custombottle);
        binnumber_custombottle=(EditText)findViewById(R.id.binnumber_custombottle);
        producr_custombottle=(EditText)findViewById(R.id.producr_custombottle);
        try {
            Bundle bundle=getIntent().getExtras();
            String image=bundle.getString("image");
            baseimage=image;
            MinValue=bundle.getString("minvalue");
            MaxValue=bundle.getString("maxvalue");
            // path=bundle.getString("path");

            byte[]decodedString= Base64.decode(image.getBytes(),Base64.DEFAULT);
            picturebyte=decodedString;
            Bitmap decodeByte= BitmapFactory.decodeByteArray(decodedString,0,decodedString.length);
            image_custombottle.setImageBitmap(decodeByte);
            bitmap=decodeByte;
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_done, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_done:
                name=name_custombottle.getText().toString();
                capacity=capacity_custombottle.getText().toString();
                category=category_custombottle.getText().toString();
                subcategory=subcategory_custombottle.getText().toString();
                shots=shots_custombottle.getText().toString();
                distributor=distributor_custombottle.getText().toString();
                price=price_custombottle.getText().toString();
                binnumber=binnumber_custombottle.getText().toString();
                productcode=producr_custombottle.getText().toString();
                parlevel=parlevel_custombottle.getText().toString();
                try{
                    Async is=new Async();
                    is.execute();
                }catch (Exception e){
                    e.printStackTrace();
                }
//                uploadFile(name,capacity,category,subcategory,shots,distributor,price,binnumber,productcode,MinValue,MaxValue,picturebyte,parlevel);

                // Intent i=new Intent(CustomBottleDetails.this,SectionBottlesActivity.class);
                //startActivity(i);

                break;
            case android.R.id.home:
                CustomBottleDetails.this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void saveProfileAccount(final String name, final String capacity, final String category, final String subcategory, final String shots, final String distributor, final String price, final String binnumber, final String productcode, String minValue, String maxValue, final byte[] picturebyte, final String parlevel) {
        {
            // loading or check internet connection or something...
            // ... then
            String url = EndURL.URL+"insertCustomBottle";
            VolleyMultiPartRequest multipartRequest = new VolleyMultiPartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
                @Override
                public void onResponse(NetworkResponse response) {
                    String resultResponse = new String(response.data);
//                try {
//                    JSONObject result = new JSONObject(resultResponse);
//                    String status = result.getString("status");
//                    String message = result.getString("message");
//
////                    if (status.equals(Constant.REQUEST_SUCCESS)) {
////                        // tell everybody you have succed upload image and post strings
////                        Log.i("Messsage", message);
////                    } else {
////                        Log.i("Unexpected", message);
////                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    NetworkResponse networkResponse = error.networkResponse;
                    String errorMessage = "Unknown error";
                    if (networkResponse == null) {
//                    if (error.getClass().equals(TimeoutError.class)) {
//                        errorMessage = "Request timeout";
//                    } else if (error.getClass().equals(NoConnectionError.class)) {
//                        errorMessage = "Failed to connect server";
//                    }
                    } else {
                        String result = new String(networkResponse.data);
                        try {
                            JSONObject response = new JSONObject(result);
                            String status = response.getString("status");
                            String message = response.getString("message");

                            Log.e("Error Status", status);
                            Log.e("Error Message", message);

                            if (networkResponse.statusCode == 404) {
                                errorMessage = "Resource not found";
                            } else if (networkResponse.statusCode == 401) {
                                errorMessage = message+" Please login again";
                            } else if (networkResponse.statusCode == 400) {
                                errorMessage = message+ " Check your inputs";
                            } else if (networkResponse.statusCode == 500) {
                                errorMessage = message+" Something is getting wrong";
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    Log.i("Error", errorMessage);
                    error.printStackTrace();
                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("userprofileid", UserProfileId);
                    params.put("barid", BarId);
                    params.put("sectionid",SectionId);
                    params.put("liquorname", name);
                    params.put("liquorcapacity", capacity);
                    params.put("shots", shots);
                    params.put("category",category);
                    params.put("subcategory",subcategory);
                    params.put("parlevel", parlevel);
                    params.put("distributorname",distributor);
                    params.put("priceunit", price);
                    params.put("binnumber", binnumber);
                    params.put("productcode", productcode);
                    params.put("MinValue", MinValue);
                    params.put("MaxValue", MaxValue);
                    return params;
                }


                @Override
                protected Map<String, DataPart> getByteData() {
                    Map<String, DataPart> params = new HashMap<>();
                    // file name could found file base or direct access from real path
                    // for now just get bitmap data from ImageView
                    params.put("picture", new DataPart("file_avatar.jpg", picturebyte, "image/jpeg"));
//                params.put("cover", new DataPart("file_cover.jpg", AppHelper.getFileDataFromDrawable(getBaseContext(), mCoverImage.getDrawable()), "image/jpeg"));

                    return params;
                }
            };

            VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(multipartRequest);
        }}
    private void Uploaddata(String name, String capacity, String category, String subcategory, String shots, String distributor, String price, String binnumber, String productcode, String minValue, String maxValue, byte[] picturebyte, String parlevel) {

        String tag_json_obj = "json_obj_req";
        String url = EndURL.URL+"insertCustomBottle";
        Log.d("inserturl", url);
        JSONObject inputLogin=new JSONObject();



        byte[] message = baseimage.getBytes(StandardCharsets.UTF_8);

        ByteArrayInputStream bs = new ByteArrayInputStream(picturebyte);
        TestImage testImage=new TestImage();
        try{
            inputLogin.put("userprofileid",Integer.parseInt(UserProfileId));
            inputLogin.put("barid",Integer.parseInt(BarId));
            inputLogin.put("sectionid",Integer.parseInt(SectionId));
            inputLogin.put("liquorname",name);
            inputLogin.put("liquorcapacity",capacity);
            inputLogin.put("shots",shots);
            inputLogin.put("category",category);
            inputLogin.put("subcategory",subcategory);
            inputLogin.put("parlevel",parlevel);
            inputLogin.put("distributorname",distributor);
            inputLogin.put("priceunit",price);
            inputLogin.put("binnumber",binnumber);
            inputLogin.put("productcode",productcode);
            inputLogin.put("MinValue",Double.parseDouble(minValue));
            inputLogin.put("MaxValue",Double.parseDouble(maxValue));
            testImage.setImage(picturebyte);
            inputLogin.put("picture",testImage);
            inputLogin.put("type","bottle");


        }catch (Exception e){
            e.printStackTrace();
        }
        Log.d("inputJsonuser",inputLogin.toString());
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, url, inputLogin, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("List Response",response.toString());
                CustomBottleDetails.this.finish();


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),"not uploaded",Toast.LENGTH_SHORT).show();


            }
        }) {

        };
        ServiceHandler.getInstance().addToRequestQueue(objectRequest, tag_json_obj);
    }
    private class Async extends AsyncTask<String,String,String >{

        @Override
        protected String doInBackground(String... params) {
            uploadFile(name,capacity,category,subcategory,shots,distributor,price,binnumber,productcode,MinValue,MaxValue,picturebyte,parlevel);

            return null;
        }
    }
    private String uploadFile(String name, String capacity, String category, String subcategory, String shots, String distributor, String price, String binnumber, String productcode, String minValue, String maxValue, byte[] picturebyte, String parlevel) {
        {
            String responseString = null;

            HttpClient httpclient = new DefaultHttpClient();
            String url = EndURL.URL + "insertCustomBottle";
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            bytearayProfile = stream.toByteArray();
            HttpPost httppost = new HttpPost(url);

            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                        new AndroidMultiPartEntity.ProgressListener() {

                            @Override
                            public void transferred(long num) {
                                //publishProgress((int) ((num / (float) totalSize) * 100));
                            }
                        });

//            File sourceFile = new File(filePath);

                // Adding file data to http body

                CustomLiquorModel modl = new CustomLiquorModel();
                modl.setUserprofileid(Integer.parseInt(UserProfileId));
                modl.setBarid(Integer.parseInt(BarId));
                modl.setSectionid(Integer.parseInt(SectionId));
                modl.setLiquorname(name);
                modl.setLiquorcapacity(capacity);
                modl.setShots(shots);
                modl.setCategory(category);
                modl.setSubcategory(subcategory);
                modl.setParlevel(parlevel);
                modl.setDistributorname(distributor);
                modl.setPriceunit(price);
                modl.setBinnumber(binnumber);
                modl.setProductcode(productcode);
                modl.setMinValue(Double.parseDouble(minValue));
                modl.setMaxValue(Double.parseDouble(maxValue));
                double minval=Double.parseDouble(minValue);
                double maxval=Double.parseDouble(maxValue);
//                minval=minval/100;
//                maxval=maxval/100;
                String fminval=String.valueOf(minval);
                String fmaxval=String.valueOf(maxval);
                entity.addPart("image", new ByteArrayBody(bytearayProfile, UserProfileId + "liq.jpg"));

                entity.addPart("userprofileid", new StringBody(UserProfileId, ContentType.TEXT_PLAIN));
                entity.addPart("barid", new StringBody(BarId, ContentType.TEXT_PLAIN));
                entity.addPart("sectionid", new StringBody(SectionId, ContentType.TEXT_PLAIN));
                entity.addPart("liquorname", new StringBody(name, ContentType.TEXT_PLAIN));
                entity.addPart("liquorquantitiy", new StringBody(capacity, ContentType.TEXT_PLAIN));
                entity.addPart("category", new StringBody(category, ContentType.TEXT_PLAIN));
                entity.addPart("subcategory", new StringBody(subcategory, ContentType.TEXT_PLAIN));
                entity.addPart("parvalue", new StringBody(parlevel, ContentType.TEXT_PLAIN));
                entity.addPart("distributorname", new StringBody(distributor, ContentType.TEXT_PLAIN));
                entity.addPart("price", new StringBody(price, ContentType.TEXT_PLAIN));
                entity.addPart("binnumber", new StringBody(binnumber, ContentType.TEXT_PLAIN));
                entity.addPart("productcode", new StringBody(productcode, ContentType.TEXT_PLAIN));
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
                    CustomBottleDetails.this.finish();
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

}