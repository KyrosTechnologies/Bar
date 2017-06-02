package com.kyros.technologies.bar.Inventory.Activity.Activity;

        import android.content.Intent;
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

        import com.kyros.technologies.bar.R;
        import com.kyros.technologies.bar.SharedPreferences.PreferenceManager;
        import com.kyros.technologies.bar.utils.AndroidMultiPartEntity;
        import com.kyros.technologies.bar.utils.CustomLiquorModel;
        import com.kyros.technologies.bar.utils.EndURL;

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
 * Created by kyros on 02-06-2017.
 */

public class AddKegDescription  extends AppCompatActivity {
    private ImageView kegg_image;
    private EditText keg_name,keg_weight,keg_emptyweight,keg_shots,keg_parlevel,
            keg_distributorname,keg_price,keg_binnumber,keg_productcode,keg_category,keg_subcategory;

    private Bitmap bitmap;
    private String baseimage=null;
    private byte [] picturebyte=null;
    private String MinValue="";
    private String MaxValue="";
    private byte[] bytearayProfile;
    private String BarId=null;
    private String SectionId=null;
    private PreferenceManager store;
    private String UserProfileId=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.custom_keg);
        kegg_image=(ImageView)findViewById(R.id.kegg_image);
        keg_productcode=(EditText)findViewById(R.id.keg_productcode);
        keg_binnumber=(EditText)findViewById(R.id.keg_binnumber);
        keg_price=(EditText)findViewById(R.id.keg_price);
        keg_distributorname=(EditText)findViewById(R.id.keg_distributorname);
        keg_parlevel=(EditText)findViewById(R.id.keg_parlevel);
        keg_shots=(EditText)findViewById(R.id.keg_shots);
        keg_emptyweight=(EditText)findViewById(R.id.keg_emptyweight);
        keg_weight=(EditText)findViewById(R.id.keg_weight);
        keg_name=(EditText)findViewById(R.id.keg_name);
        keg_category=(EditText)findViewById(R.id.keg_category);
        keg_subcategory=(EditText)findViewById(R.id.keg_subcategory);
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
            kegg_image.setImageBitmap(decodeByte);
            bitmap=decodeByte;
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
                try{
                    Async is=new Async();
                    is.execute();
                }catch (Exception e){
                    e.printStackTrace();
                }

                break;
            case android.R.id.home:
                AddKegDescription.this.finish();
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
        String url = EndURL.URL + "insertCustomKeg";
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


            double minval=Double.parseDouble(MinValue);
            double maxval=Double.parseDouble(MaxValue);
            minval=minval/100;
            maxval=maxval/100;
            String fminval=String.valueOf(minval);
            String fmaxval=String.valueOf(maxval);
            entity.addPart("image", new ByteArrayBody(bytearayProfile, UserProfileId + "liq.jpg"));

            entity.addPart("userprofileid", new StringBody(UserProfileId, ContentType.TEXT_PLAIN));
            entity.addPart("barid", new StringBody(BarId, ContentType.TEXT_PLAIN));
            entity.addPart("sectionid", new StringBody(SectionId, ContentType.TEXT_PLAIN));
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
                Intent i=new Intent(AddKegDescription.this,SectionBottlesActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);                    //    Toast.makeText(getApplicationContext(),"Uploaded successfully",Toast.LENGTH_SHORT).show();
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