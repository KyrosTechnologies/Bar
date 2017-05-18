package com.kyros.technologies.bar.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kyros.technologies.bar.R;
import com.kyros.technologies.bar.SharedPreferences.PreferenceManager;

public class ProfileViewActivity extends AppCompatActivity {
    private LinearLayout profile_password_change,change_profile_fields;
    private AlertDialog passworddialog;
    private String UserProfileId=null;
    private String Firstname=null;
    private String Lastname=null;
    private String UserEmail=null;
    private String MobileNumber=null;
    private String Venue=null;
    private String Country=null;
    private String Inventory=null;
    private String InventoryTime=null;
    private boolean Isactive;
    private String Create=null;
    private String Modifi=null;
    private String Ids=null;
    private String Password=null;
    private PreferenceManager store;
    private TextView venue,profile_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_profile_view);
        store= PreferenceManager.getInstance(getApplicationContext());
        UserProfileId=store.getUserProfileId();
        Firstname=store.getFirstName();
        Lastname=store.getLastName();
        UserEmail=store.getUserEmail();
        MobileNumber=store.getMobileNumber();
        Venue=store.getVenue();
        Country=store.getCountry();
        Inventory=store.getInventory();
        InventoryTime=store.getInventoryTime();
        Isactive=store.getIsactive();
        Create=store.getCreate();
        Modifi=store.getModifi();
        Ids=store.getIds();
        Password=store.getPassword();
        String FullName=Firstname+" "+Lastname;
        profile_password_change=(LinearLayout)findViewById(R.id.profile_password_change);
        profile_name=(TextView)findViewById(R.id.profile_name);
        venue=(TextView)findViewById(R.id.venue);
        profile_name.setText(FullName);
        if (Venue==null){
            Venue="";

        }
        venue.setText(Venue);

        change_profile_fields=(LinearLayout)findViewById(R.id.change_profile_fields);
        profile_password_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showPopup();
                Intent intent=new Intent(ProfileViewActivity.this,ChangePasswordActivity.class);
                startActivity(intent);
            }
        });
        change_profile_fields.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProfileViewActivity.this,EditProfile.class);
                intent.putExtra("title","Edit Profile Info");
                startActivity(intent);
            }
        });
    }
private void showPopup(){
    if(passworddialog==null){
        AlertDialog.Builder builder=new AlertDialog.Builder(ProfileViewActivity.this);
        LayoutInflater inflater=getLayoutInflater();
        View view=inflater.inflate(R.layout.change_password_profile,null);
        builder.setView(view);
        final EditText email_change_password=(EditText)view.findViewById(R.id.email_change_password);
        TextView back_change=(TextView)view.findViewById(R.id.back_change);
        TextView reset_change=(TextView)view.findViewById(R.id.reset_change);
        back_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissPopup();
            }
        });
        reset_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=email_change_password.getText().toString();
                if(email!=null && !email.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Email is: "+email,Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Reset is clicked!",Toast.LENGTH_SHORT).show();

                }
            }
        });
        passworddialog=builder.create();
        passworddialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        passworddialog.setCancelable(false);
        passworddialog.setCanceledOnTouchOutside(false);


    }
    passworddialog.show();

}private void dismissPopup(){
        if(passworddialog!=null&&passworddialog.isShowing()){
            passworddialog.dismiss();
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        dismissPopup();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissPopup();
    }

    @Override
    protected void onResume() {
        super.onResume();
        store= PreferenceManager.getInstance(getApplicationContext());
        UserProfileId=store.getUserProfileId();
        Firstname=store.getFirstName();
        Lastname=store.getLastName();
        UserEmail=store.getUserEmail();
        MobileNumber=store.getMobileNumber();
        Venue=store.getVenue();
        Country=store.getCountry();
        Inventory=store.getInventory();
        InventoryTime=store.getInventoryTime();
        Isactive=store.getIsactive();
        Create=store.getCreate();
        Modifi=store.getModifi();
        Ids=store.getIds();
        Password=store.getPassword();
        String FullName=Firstname+" "+Lastname;
        profile_password_change=(LinearLayout)findViewById(R.id.profile_password_change);
        profile_name=(TextView)findViewById(R.id.profile_name);
        venue=(TextView)findViewById(R.id.venue);
        profile_name.setText(FullName);
        if (Venue==null){
            Venue="";

        }
        venue.setText(Venue);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){


            case android.R.id.home:
                ProfileViewActivity.this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
