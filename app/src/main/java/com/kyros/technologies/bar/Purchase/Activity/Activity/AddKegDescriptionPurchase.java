package com.kyros.technologies.bar.Purchase.Activity.Activity;

/**
 * Created by Rohin on 06-06-2017.
 */

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
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
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static android.R.attr.name;
import static android.R.attr.type;

public class AddKegDescriptionPurchase extends AppCompatActivity {
    private ImageView kegg_image_purchase;
    private EditText keg_name,keg_weight,keg_emptyweight,keg_shots,keg_parlevel,
            keg_distributorname,keg_price,keg_binnumber,keg_productcode,keg_category,keg_subcategory;
    private String baseimage=null;
    private byte [] picturebyte=null;
    private String MinValue="";
    private String MaxValue="";
    private byte[] bytearayProfile;
    private Bitmap bitmapvariable;
    private String BarId=null;
    private String SectionId=null;
    private PreferenceManager store;
    private String UserProfileId=null;
    private String id=null;
    private String type=null;
    private String kegname=null;
    private String kegfullweight=null;
    private String kegemptyweight=null;
    private String kegcategory=null;
    private String kegsubcategory=null;
    private String shots=null;
    private String parlevel=null;
    private String distributorname=null;
    private String price=null;
    private String binnumber=null;
    private String productcode=null;
    private String Picture=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_add_keg_description_purchase);
        kegg_image_purchase=(ImageView)findViewById(R.id.kegg_image_purchase);
        keg_productcode=(EditText)findViewById(R.id.keg_productcode_purchase);
        keg_binnumber=(EditText)findViewById(R.id.keg_binnumber_purchase);
        keg_price=(EditText)findViewById(R.id.keg_price_purchase);
        Toast.makeText(getApplicationContext(),"working",Toast.LENGTH_SHORT).show();

        keg_distributorname=(EditText)findViewById(R.id.keg_distributorname_purchase);
        keg_parlevel=(EditText)findViewById(R.id.keg_parlevel_purchase);
        keg_shots=(EditText)findViewById(R.id.keg_shots_purchase);
        keg_emptyweight=(EditText)findViewById(R.id.keg_emptyweight_purchase);
        keg_weight=(EditText)findViewById(R.id.keg_weight_purchase);
        keg_name=(EditText)findViewById(R.id.keg_name_purchase);
        keg_category=(EditText)findViewById(R.id.keg_category_purchase);
        keg_subcategory=(EditText)findViewById(R.id.keg_subcategory_purchase);
        store= PreferenceManager.getInstance(getApplicationContext());
        BarId=store.getBarId();
        SectionId=store.getSectionId();
        UserProfileId=store.getUserProfileId();
        try {
            Bundle bundle=getIntent().getExtras();
            String image=bundle.getString("image");
            baseimage=image;
            MinValue=bundle.getString("minvalue");
            MaxValue=bundle.getString("maxvalue");
            byte[]decodedString= Base64.decode(image.getBytes(),Base64.DEFAULT);
            picturebyte=decodedString;
            Bitmap decodeByte= BitmapFactory.decodeByteArray(decodedString,0,decodedString.length);
//            kegg_image_purchase.setImageBitmap(decodeByte);
            bitmapvariable=decodeByte;
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            Picasso.with(AddKegDescriptionPurchase.this).load(baseimage).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    kegg_image_purchase.setImageBitmap(bitmap);
                    bitmapvariable=bitmap;
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

            Bundle bundle=getIntent().getExtras();
            kegname=bundle.getString("name");
            if(kegname==null){
                kegname="";
            }

            kegfullweight=bundle.getString("fullweight");
            if(kegfullweight==null){
                kegfullweight="";
            }
            kegemptyweight=bundle.getString("emptyweight");
            if(kegemptyweight==null){
                kegemptyweight="";
            }

            kegcategory=bundle.getString("category");
            if(kegcategory==null){
                kegcategory="";
            }
            kegsubcategory=bundle.getString("subcategory");
            if(kegsubcategory==null){
                kegsubcategory="";
            }
            shots=bundle.getString("shots");
            if(shots==null){
                shots="";
            }
            id=bundle.getString("id");
            if(id==null){
                id="0";
            }
            type=bundle.getString("type");
            if(type==null){
                type="";
            }
            parlevel=bundle.getString("parvalue");
            if(parlevel==null){
                parlevel="";
            }
            distributorname=bundle.getString("distributorname");
            if(distributorname==null){
                distributorname="";
            }
            price=bundle.getString("price");
            if(price==null){
                price="";
            }
            binnumber=bundle.getString("binnumber");
            if(binnumber==null){
                binnumber="";
            }
            productcode=bundle.getString("productcode");
            if(productcode==null){
                productcode="";
            }

            MinValue=bundle.getString("minvalue");
            if(MinValue==null){
                MinValue="";
            }
            MaxValue=bundle.getString("maxvalue");
            if(MaxValue==null){
                MaxValue="";
            }
            Picture=bundle.getString("image");

            try {

                keg_name.setText(kegname);
                keg_weight.setText(kegfullweight);
                keg_emptyweight.setText(kegemptyweight);
                keg_category.setText(kegcategory);
                keg_subcategory.setText(kegsubcategory);
                keg_shots.setText(shots);
                keg_parlevel.setText(parlevel);
                keg_distributorname.setText(distributorname);
                keg_price.setText(price);
                keg_binnumber.setText(binnumber);
                keg_productcode.setText(productcode);
            }catch (Exception e){
                e.printStackTrace();
            }

            if(Picture!=null){
                Picasso.with(AddKegDescriptionPurchase.this).load(Picture).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        kegg_image_purchase.setImageBitmap(bitmap);
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
        getMenuInflater().inflate(R.menu.next, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_next:
                if(type.equals("update")){

                    //Log.d("Keg Values",id+type+kegname+kegfullweight+kegemptyweight+kegcategory+kegsubcategory+shots+parlevel+distributorname+price+parlevel+binnumber+productcode+picturebyte);
                    UpdatepurchaselistKeg();
                }else {
                    try {
                        Async is = new Async();
                        is.execute();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                break;
            case android.R.id.home:
                AddKegDescriptionPurchase.this.finish();
                return true;
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
        String name=keg_name.getText().toString();
        String fullweight=keg_weight.getText().toString();
        String emptyweight=keg_emptyweight.getText().toString();
        String category=keg_category.getText().toString();
        String subcategory=keg_subcategory.getText().toString();
        String parlevel=keg_parlevel.getText().toString();
        String distributor=keg_distributorname.getText().toString();
        String price=keg_price.getText().toString();
        String binnumber=keg_binnumber.getText().toString();
        String shots=keg_shots.getText().toString();
        String productcode=keg_productcode.getText().toString();
        HttpClient httpclient = new DefaultHttpClient();
        String url = EndURL.URL + "insertCustomKegPurchase";
        Log.d("url: ",url);
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
            entity.addPart("fullweight", new StringBody(fullweight, ContentType.TEXT_PLAIN));
            entity.addPart("emptyweight", new StringBody(emptyweight, ContentType.TEXT_PLAIN));
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
            entity.addPart("type",new StringBody("keg",ContentType.TEXT_PLAIN));

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

    private void UpdatepurchaselistKeg() {
        String tag_json_obj = "json_obj_req";
        String url = EndURL.URL+"UpdatePurchaseListKeg";
        Log.d("bottleurl", url);
        String name =keg_name.getText().toString();
        String fullweight= keg_weight.getText().toString();
        String emptyweight=keg_emptyweight.getText().toString();
        String maincat=keg_category.getText().toString();
        String subcat=keg_subcategory.getText().toString();
        String shots=keg_shots.getText().toString();
        String parlevel=keg_parlevel.getText().toString();
        String disname=keg_distributorname.getText().toString();
        String price=keg_price.getText().toString();
        String bin=keg_binnumber.getText().toString();
        String product=keg_productcode.getText().toString();
        JSONObject inputLogin=new JSONObject();
        try{
            inputLogin.put("id",id);
            inputLogin.put("userprofileid",UserProfileId);
            inputLogin.put("liquorname",name);
            inputLogin.put("fullweight",fullweight);
            inputLogin.put("emptyweight",emptyweight);
            inputLogin.put("type","keg");
            inputLogin.put("shots",shots);
            inputLogin.put("category",maincat);
            inputLogin.put("subcategory",subcat);
            inputLogin.put("parlevel",parlevel);
            inputLogin.put("distributorname",disname);
            inputLogin.put("price",price);
            inputLogin.put("binnumber",bin);
            inputLogin.put("productcode",product);
            inputLogin.put("minvalue",MinValue);
            inputLogin.put("maxvalue",MaxValue);



        }catch (Exception e){
            e.printStackTrace();
        }
        Log.d("inputJsonuser",inputLogin.toString());
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.PUT, url, inputLogin, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("List Response",response.toString());
                try {

                    JSONObject obj=new JSONObject(response.toString());
                    String message=obj.getString("message");
                    boolean success=obj.getBoolean("issuccess");
                    if (success){

                        Toast.makeText(getApplicationContext(),"Item Updated successfully",Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(AddKegDescriptionPurchase.this,PurchaseListActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);

                    }else {
                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),"Something went wrong!.",Toast.LENGTH_SHORT).show();

            }
        }) {

        };
        ServiceHandler.getInstance().addToRequestQueue(objectRequest, tag_json_obj);

    }


}