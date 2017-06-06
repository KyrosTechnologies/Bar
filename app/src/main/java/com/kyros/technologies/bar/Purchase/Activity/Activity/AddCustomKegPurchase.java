package com.kyros.technologies.bar.Purchase.Activity.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.kyros.technologies.bar.Inventory.Activity.Activity.AddCustomKeg;
import com.kyros.technologies.bar.Inventory.Activity.Activity.AddKegDescription;
import com.kyros.technologies.bar.R;
import com.kyros.technologies.bar.seekbar.Seekbar;

public class AddCustomKegPurchase extends AppCompatActivity {
    private ImageView custom_capture_keg;
    private Seekbar seekbar;
    private String  Minvalue=null;
    private String  Maxvalue=null;
    private String ImageString=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_add_custom_keg_purchase);
        seekbar=(Seekbar)findViewById(R.id.seek_custom_bottle_purchase);
        Toast.makeText(getApplicationContext(),"working",Toast.LENGTH_SHORT).show();

        custom_capture_keg=(ImageView)findViewById(R.id.custom_capture_bottle_purchase);

        try {
            Bundle bundle=getIntent().getExtras();
            String image=bundle.getString("image");
            ImageString=image;
            byte[]decodedString= Base64.decode(image.getBytes(),Base64.DEFAULT);
            Bitmap decodeByte= BitmapFactory.decodeByteArray(decodedString,0,decodedString.length);
            custom_capture_keg.setImageBitmap(decodeByte);
        }catch (Exception e){
            e.printStackTrace();
        }
        seekbar.setOnRangeSeekBarChangeListener(new Seekbar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(Seekbar bar, Number minValue, Number maxValue) {
                Minvalue=String.valueOf(minValue);
                Maxvalue=String.valueOf(maxValue);

            }
        });
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
                Intent i=new Intent(AddCustomKegPurchase.this,AddKegDescriptionPurchase.class);
                i.putExtra("image",ImageString);
                i.putExtra("minvalue",Minvalue);
                i.putExtra("maxvalue",Maxvalue);
                startActivity(i);

                break;
            case android.R.id.home:
                AddCustomKegPurchase.this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}