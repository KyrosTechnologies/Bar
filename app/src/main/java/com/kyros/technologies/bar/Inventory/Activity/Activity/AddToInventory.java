package com.kyros.technologies.bar.Inventory.Activity.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kyros.technologies.bar.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Rohin on 05-05-2017.
 */

public class AddToInventory extends AppCompatActivity {

    private LinearLayout add_custom_bottle;
    private AlertDialog custom_bottle_dialog;
    private final CharSequence[] items = {"Take Photo","Cancel"};
    private final int REQUEST_CAMERA = 0;
    private final int SELECT_FILE = 1;
    private String whichone=null;
    private Uri selectedImage=null;
    private AlertDialog online;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.add_to_inventory);
        add_custom_bottle=(LinearLayout)findViewById(R.id.add_custom_bottle);
        add_custom_bottle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openpopup();
            }
        });

    }

    private void closepopup(){
        if(custom_bottle_dialog!=null&& custom_bottle_dialog.isShowing()){
            custom_bottle_dialog.dismiss();
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
        online= new AlertDialog.Builder(AddToInventory.this).create();
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


    private void openpopup() {
        if(custom_bottle_dialog==null){
            AlertDialog.Builder builder=new AlertDialog.Builder(AddToInventory.this);
            LayoutInflater inflater=getLayoutInflater();
            View view=inflater.inflate(R.layout.custom_bottle_popup,null);
            builder.setView(view);
            TextView bottle=(TextView)view.findViewById(R.id.bottle);
            TextView keg=(TextView)view.findViewById(R.id.keg);
            TextView cancel=(TextView)view.findViewById(R.id.cancel);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    closepopup();
                }
            });
            bottle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertdialog();
                    whichone="bottle";
                }
            });

            keg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertdialog();
                    whichone="keg";

                }
            });
            custom_bottle_dialog=builder.create();
            custom_bottle_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            custom_bottle_dialog.setCancelable(false);
            custom_bottle_dialog.setCanceledOnTouchOutside(false);

        }
        custom_bottle_dialog.show();

    }

    private void alertdialog() {
        if ((ContextCompat.checkSelfPermission(AddToInventory.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED)&&(ContextCompat.checkSelfPermission(AddToInventory.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED)) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(AddToInventory.this,
                    Manifest.permission.CAMERA)&&ActivityCompat.shouldShowRequestPermissionRationale(AddToInventory.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(AddToInventory.this, "Camera permission needed to take Picture", Toast.LENGTH_SHORT).show();

            } else {
                ActivityCompat.requestPermissions(AddToInventory.this,
                        new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CAMERA);

            }


        }else{
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_CAMERA);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissonlineDialog();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {

            case SELECT_FILE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);

                } else {
                    Toast.makeText(getApplicationContext(),"You have denied the permission",Toast.LENGTH_SHORT).show();

                }
                return;
            }
            case REQUEST_CAMERA:{
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);

                } else {
                    Toast.makeText(getApplicationContext(),"You have denied the permission",Toast.LENGTH_SHORT).show();

                }
                return;
            }

        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==SELECT_FILE){
            if(resultCode==RESULT_OK){
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }else if(requestCode==REQUEST_CAMERA){
            if(resultCode==RESULT_OK){
                  //  selectedImage = data.getData();

                onCaptureImageResult(data);

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }


    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        String wagon = "WaggonStation";
        String state = Environment.getExternalStorageState();

        if (!Environment.MEDIA_MOUNTED.equals(state)) {
            Toast.makeText(this, "SD Card not mounted!", Toast.LENGTH_LONG).show();
        }

        File imagestoragedir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), wagon);
        if (!imagestoragedir.exists() && !imagestoragedir.mkdirs()) {
            Toast.makeText(this, "Failed to create directory in SD Card", Toast.LENGTH_LONG).show();
        }

        File destination = new File(imagestoragedir.getPath(),
                 "_profilepicture" + ".jpg");

        FileOutputStream fo;
        try {

            try {
                String imgString = Base64.encodeToString(getBytesFromBitmap(thumbnail), Base64.DEFAULT);
                if (imgString!=null){
                    if(whichone!=null){
                        switch (whichone){
                            case "bottle":
                                Intent i=new Intent(AddToInventory.this,AddCustomBottle.class);
                                i.putExtra("image",imgString);
                                i.putExtra("path","");
                                startActivity(i);
                                break;
                            case "keg":
                                Intent k=new Intent(AddToInventory.this,AddCustomKeg.class);
                                k.putExtra("image",imgString);
                                k.putExtra("path","");
                                startActivity(k);
                                break;
                        }
                    }


                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
        return stream.toByteArray();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                AddToInventory.this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}