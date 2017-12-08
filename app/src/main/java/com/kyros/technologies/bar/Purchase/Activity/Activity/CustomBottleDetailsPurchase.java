package com.kyros.technologies.bar.Purchase.Activity.Activity;

/**
 * Created by Rohin on 06-06-2017.
 */

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
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

import com.kyros.technologies.bar.R;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CustomBottleDetailsPurchase extends AppCompatActivity {
    private ImageView image_custombottle;
    private EditText name_custombottle,capacity_custombottle,category_custombottle,subcategory_custombottle,
            shots_custombottle,parlevel_custombottle,distributor_custombottle,price_custombottle,
            binnumber_custombottle,producr_custombottle;

    private byte [] picturebyte=null;
    private String MinValue="";
    private String MaxValue="";
    private String BarId=null;
    private String SectionId=null;
    private PreferenceManager store;
    private String UserProfileId=null;
    private String path=null;
    private Bitmap bitmapvar;
    private String baseimage=null;
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
        setContentView(R.layout.activity_custom_bottle_details_purchase);
        store=PreferenceManager.getInstance(getApplicationContext());
        BarId=store.getBarId();
        SectionId=store.getSectionId();
        UserProfileId=store.getUserProfileId();
        Toast.makeText(getApplicationContext(),"working",Toast.LENGTH_SHORT).show();

        image_custombottle=(ImageView)findViewById(R.id.image_custombottle_purchase);
        name_custombottle=(EditText)findViewById(R.id.name_custombottle_purchase);
        capacity_custombottle=(EditText)findViewById(R.id.capacity_custombottle_purchase);
        category_custombottle=(EditText)findViewById(R.id.category_custombottle_purchase);
        subcategory_custombottle=(EditText)findViewById(R.id.subcategory_custombottle_purchase);
        shots_custombottle=(EditText)findViewById(R.id.shots_custombottle_purchase);
        parlevel_custombottle=(EditText)findViewById(R.id.parlevel_custombottle_purchase);
        distributor_custombottle=(EditText)findViewById(R.id.distributor_custombottle_purchase);
        price_custombottle=(EditText)findViewById(R.id.price_custombottle_purchase);
        binnumber_custombottle=(EditText)findViewById(R.id.binnumber_custombottle_purchase);
        producr_custombottle=(EditText)findViewById(R.id.producr_custombottle_purchase);
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
            bitmapvar=decodeByte;
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            Picasso.with(CustomBottleDetailsPurchase.this).load(baseimage).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    image_custombottle.setImageBitmap(bitmap);
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
                CustomBottleDetailsPurchase.this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private class Async extends AsyncTask<String,String,String > {

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
            String url = EndURL.URL + "insertCustomBottlePurchase";
            Log.d("url: ",url);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmapvar.compress(Bitmap.CompressFormat.PNG, 0, stream);
            bytearayProfile = stream.toByteArray();
            HttpPost httppost = new HttpPost(url);
            httppost.addHeader("Authorization",store.getUserProfileId()+"|"+store.getAuthorizationKey());
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


                double minval=Double.parseDouble(minValue);
                double maxval=Double.parseDouble(maxValue);
                minval=minval/100;
                maxval=maxval/100;
                String fminval=String.valueOf(minval);
                String fmaxval=String.valueOf(maxval);
                entity.addPart("image", new ByteArrayBody(bytearayProfile, UserProfileId + "liq.png"));
                entity.addPart("userprofileid", new StringBody(UserProfileId, ContentType.TEXT_PLAIN));
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
                    CustomBottleDetailsPurchase.this.finish();
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