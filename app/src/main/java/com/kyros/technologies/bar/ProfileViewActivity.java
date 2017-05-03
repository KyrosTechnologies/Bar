package com.kyros.technologies.bar;

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

public class ProfileViewActivity extends AppCompatActivity {
    private LinearLayout profile_password_change,change_profile_fields;
    private AlertDialog passworddialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_profile_view);
        profile_password_change=(LinearLayout)findViewById(R.id.profile_password_change);
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
                Intent intent=new Intent(ProfileViewActivity.this,SignUpActivity.class);
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
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){


            case android.R.id.home:
                ProfileViewActivity.this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
