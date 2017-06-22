package com.kyros.technologies.bar.Inventory.Activity.Activity;

        import android.app.AlertDialog;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.pm.ActivityInfo;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.net.ConnectivityManager;
        import android.net.NetworkInfo;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Base64;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.widget.ImageView;
        import android.widget.SeekBar;

        import com.kyros.technologies.bar.R;
        import com.kyros.technologies.bar.seekbar.Seekbar;

/**
 * Created by Rohin on 05-05-2017.
 */

public class AddCustomBottle extends AppCompatActivity{

    private ImageView custom_capture_bottle;
    private SeekBar min_seekbar,max_seekbar;
    private String ImageString=null;
    private Seekbar seekbar;
    private String  Minvalue=null;
    private String  Maxvalue=null;
    private AlertDialog online;
    private String path=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.add_custom_bottle);
        seekbar=(Seekbar)findViewById(R.id.seek_custom_bottle);

        custom_capture_bottle=(ImageView)findViewById(R.id.custom_capture_bottle);
        try {
            Bundle bundle=getIntent().getExtras();
            String image=bundle.getString("image");
            path=bundle.getString("path");
            ImageString=image;
            byte[]decodedString= Base64.decode(image.getBytes(),Base64.DEFAULT);
            Bitmap decodeByte= BitmapFactory.decodeByteArray(decodedString,0,decodedString.length);
            custom_capture_bottle.setImageBitmap(decodeByte);
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
        online= new AlertDialog.Builder(AddCustomBottle.this).create();
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
        getMenuInflater().inflate(R.menu.next, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_next:
                Intent i=new Intent(AddCustomBottle.this,CustomBottleDetails.class);
                i.putExtra("image",ImageString);
                i.putExtra("minvalue",Minvalue);
                i.putExtra("maxvalue",Maxvalue);
                i.putExtra("path",path);
                startActivity(i);
                break;
            case android.R.id.home:
                AddCustomBottle.this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissonlineDialog();
    }

}