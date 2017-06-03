package com.kyros.technologies.bar.Purchase.Activity.Activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.kyros.technologies.bar.Inventory.Activity.Activity.AddKegDescription;
import com.kyros.technologies.bar.Inventory.Activity.Activity.SectionBottlesActivity;
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

public class BottlePurchaseStock extends AppCompatActivity {

    private String bottlename=null;
    private String bottlecapacity=null;
    private String bottlecategory=null;
    private String bottlesubcategory=null;
    private EditText pur_bottle_des_name,pur_bottle_des_capacity,pur_bottle_des_main_category,pur_bottle_des_sub_category,pur_bottle_des_shots,pur_bottle_des_par_level,
            pur_bottle_des_distributor_name,pur_bottle_des_price_unit,pur_bottle_des_bin_number,pur_bottle_des_product_code;
    private PreferenceManager store;
    private String UserProfileId=null;
    private String MinValue=null;
    private String MaxValue=null;
    private String Picture=null;
    private ImageView pic_id_values;
    private byte[] bytearayProfile;
    private Bitmap bitmapvariable;

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
        Toast.makeText(getApplicationContext(),"working",Toast.LENGTH_SHORT).show();

        pur_bottle_des_main_category=(EditText)findViewById(R.id.pur_bottle_des_main_category);
        pur_bottle_des_sub_category=(EditText)findViewById(R.id.pur_bottle_des_sub_category);
        pur_bottle_des_shots=(EditText)findViewById(R.id.pur_bottle_des_shots);
        pur_bottle_des_par_level=(EditText)findViewById(R.id.pur_bottle_des_par_level);
        pur_bottle_des_distributor_name=(EditText)findViewById(R.id.pur_bottle_des_distributor_name);
        pur_bottle_des_price_unit=(EditText)findViewById(R.id.pur_bottle_des_price_unit);
        pur_bottle_des_bin_number=(EditText)findViewById(R.id.pur_bottle_des_bin_number);
        pur_bottle_des_product_code=(EditText)findViewById(R.id.pur_bottle_des_product_code);
        pic_id_values=(ImageView)findViewById(R.id.pic_id_values);
        store= PreferenceManager.getInstance(getApplicationContext());
        UserProfileId=store.getUserProfileId();

        try {

            Bundle bundle=getIntent().getExtras();
            bottlename=bundle.getString("name");
            if(bottlename==null){
                bottlename="";
            }

            bottlecapacity=bundle.getString("capacity");
            if(bottlecapacity==null){
                bottlecapacity="";
            }
            bottlecategory=bundle.getString("category");
            if(bottlecategory==null){
                bottlecategory="";
            }
            bottlesubcategory=bundle.getString("subcategory");
            if(bottlesubcategory==null){
                bottlesubcategory="";
            }
            MinValue=bundle.getString("minvalue");
            if(MinValue==null){
                MinValue="";
            }
            MaxValue=bundle.getString("maxvalue");
            if(MaxValue==null){
                MaxValue="";
            }
            Picture=bundle.getString("picture");

            try {

                pur_bottle_des_name.setText(bottlename);
                pur_bottle_des_capacity.setText(bottlecapacity);
                pur_bottle_des_main_category.setText(bottlecategory);
                pur_bottle_des_sub_category.setText(bottlesubcategory);

            }catch (Exception e){
                e.printStackTrace();
            }
            if(Picture!=null){
                Picasso.with(BottlePurchaseStock.this).load(Picture).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        pic_id_values.setImageBitmap(bitmap);
                        bitmapvariable=bitmap;
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
            }



        }catch (Exception e){
            e.printStackTrace();
        }

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
                try{
                    Async is=new Async();
                    is.execute();
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
    private String uploadFile() {

        String responseString = null;
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
        HttpClient httpclient = new DefaultHttpClient();
        String url = EndURL.URL +"insertPurchaseList";
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmapvariable.compress(Bitmap.CompressFormat.JPEG, 100, stream);
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


            double minval=Double.parseDouble(MinValue);
            double maxval=Double.parseDouble(MaxValue);
            minval=minval/100;
            maxval=maxval/100;
            String fminval=String.valueOf(minval);
            String fmaxval=String.valueOf(maxval);
            entity.addPart("image", new ByteArrayBody(bytearayProfile, UserProfileId + "liq.jpg"));
            entity.addPart("userprofileid", new StringBody(UserProfileId, ContentType.TEXT_PLAIN));
            entity.addPart("liquorname", new StringBody(name, ContentType.TEXT_PLAIN));
            entity.addPart("liquorcapacity", new StringBody(capacity, ContentType.TEXT_PLAIN));
             entity.addPart("category", new StringBody(maincat, ContentType.TEXT_PLAIN));
            entity.addPart("subcategory", new StringBody(subcat, ContentType.TEXT_PLAIN));
            entity.addPart("parlevel", new StringBody(parlevel, ContentType.TEXT_PLAIN));
            entity.addPart("distributorname", new StringBody(disname, ContentType.TEXT_PLAIN));
            entity.addPart("priceunit", new StringBody(price, ContentType.TEXT_PLAIN));
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
                Intent i=new Intent(BottlePurchaseStock.this,PurchaseListActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            } else {
                responseString = "Error occurred! Http Status Code: "
                        + statusCode;

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
