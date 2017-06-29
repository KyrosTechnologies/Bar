package com.kyros.technologies.bar.Inventory.Activity.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

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

/**
 * Created by Rohin on 30-05-2017.
 */

public class AddBottleDescription extends AppCompatActivity {

    private String bottlename=null;
    private String bottlecapacity=null;
    private String bottlecategory=null;
    private String bottlesubcategory=null;
    private EditText bottle_des_name,bottle_des_capacity,bottle_des_main_category,bottle_des_sub_category,bottle_des_shots,bottle_des_par_level,
            bottle_des_distributor_name,bottle_des_price_unit,bottle_des_bin_number,bottle_des_product_code;
    private PreferenceManager store;
    private String UserProfileId=null;
    private String Barid=null;
    private String Sectionid=null;
    private String Update=null;
    private ImageView bott_image;
    private Bitmap bitmapvariable=null;
    private String MinValue=null;
    private String MaxValue=null;
    private byte[] bytearayProfile;
    private AlertDialog online;


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
        bott_image=(ImageView)findViewById(R.id.bott_image);
        bottle_des_sub_category=(EditText)findViewById(R.id.bottle_des_sub_category);
        bottle_des_shots=(EditText)findViewById(R.id.bottle_des_shots);
        bottle_des_par_level=(EditText)findViewById(R.id.bottle_des_par_level);
        bottle_des_distributor_name=(EditText)findViewById(R.id.bottle_des_distributor_name);
        bottle_des_price_unit=(EditText)findViewById(R.id.bottle_des_price_unit);
        bottle_des_bin_number=(EditText)findViewById(R.id.bottle_des_bin_number);
        bottle_des_product_code=(EditText)findViewById(R.id.bottle_des_product_code);
        store= PreferenceManager.getInstance(getApplicationContext());
        UserProfileId=store.getUserProfileId();
        Barid=store.getBarId();
        Sectionid=store.getSectionId();
        try {

            Bundle bundle=getIntent().getExtras();
            bottlename=bundle.getString("name");
            bottlecapacity=bundle.getString("capacity");
            bottlecategory=bundle.getString("category");
            bottlesubcategory=bundle.getString("subcategory");

            try {

                bottle_des_name.setText(bottlename);
                bottle_des_capacity.setText(bottlecapacity);
                bottle_des_main_category.setText(bottlecategory);
                bottle_des_sub_category.setText(bottlesubcategory);

            }catch (Exception e){
                e.printStackTrace();
            }



        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            Bundle bundle=getIntent().getExtras();
            String image=bundle.getString("image");
            String liquorname=bundle.getString("liquorname");
            if(liquorname!=null){
                bottle_des_name.setText(liquorname);
            }
            String liquorcapacity=bundle.getString("liquorcapacity");
            if(liquorcapacity!=null){
                bottle_des_capacity.setText(liquorcapacity);
            }
            String shots=bundle.getString("shots");
            if(shots!=null){
                bottle_des_shots.setText(shots);
            }
            String minvalue=bundle.getString("minvalue");
            MinValue=minvalue;
            String maxvalue=bundle.getString("maxvalue");
            MaxValue=maxvalue;
            String createdon=bundle.getString("createdon");
            String productcode=bundle.getString("productcode");
            if(productcode!=null){
                bottle_des_product_code.setText(productcode);
            }
            String binnumber=bundle.getString("binnumber");
            if(binnumber!=null){
                bottle_des_bin_number.setText(binnumber);
            }
            String price=bundle.getString("price");
            if(price!=null){
                bottle_des_price_unit.setText(price);
            }
            String category=bundle.getString("category");
            if(category!=null){
                bottle_des_main_category.setText(category);
            }
            String subcategory=bundle.getString("subcategory");
            if(subcategory!=null){
                bottle_des_sub_category.setText(subcategory);
            }
            String parvalue=bundle.getString("parvalue");
            if(parvalue!=null){
                bottle_des_par_level.setText(parvalue);
            }
            String distributorname=bundle.getString("distributorname");
            if(distributorname!=null){
                bottle_des_distributor_name.setText(distributorname);
            }
            int id=bundle.getInt("id");
             Update=bundle.getString("update");

            if(image!=null){
                try{
                    Picasso.with(AddBottleDescription.this).load(image).into(bott_image);
                    Picasso.with(AddBottleDescription.this).load(image).into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            bitmapvariable=bitmap;
                            bott_image.setImageBitmap(bitmap);
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
        online= new AlertDialog.Builder(AddBottleDescription.this).create();
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_done,menu);
        return  true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissonlineDialog();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){


            case android.R.id.home:
                AddBottleDescription.this.finish();
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
        bitmapvariable.compress(Bitmap.CompressFormat.PNG, 0, stream);
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




            double minval=Double.parseDouble(MinValue);
            double maxval=Double.parseDouble(MaxValue);
//            minval=minval/100;
//            maxval=maxval/100;
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
                AddBottleDescription.this.finish();
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
