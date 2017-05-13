package com.kyros.technologies.bar;

import android.app.AlertDialog;
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

public class UserDetailsActivity extends AppCompatActivity {

    private LinearLayout role;
    private AlertDialog forget_dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_user_details);
        role=(LinearLayout)findViewById(R.id.role);
        role.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openpopup();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        closepopup();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closepopup();
    }

    private void closepopup(){
        if(forget_dialog!=null&& forget_dialog.isShowing()){
            forget_dialog.dismiss();
        }
    }


    private void openpopup() {
        if(forget_dialog==null){
            AlertDialog.Builder builder=new AlertDialog.Builder(UserDetailsActivity.this);
            LayoutInflater inflater=getLayoutInflater();
            View view=inflater.inflate(R.layout.user_details_role,null);
            builder.setView(view);
            TextView back_forget=(TextView)view.findViewById(R.id.back_forget);
            back_forget.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    closepopup();
                }
            });
            forget_dialog=builder.create();
            forget_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            forget_dialog.setCancelable(false);
            forget_dialog.setCanceledOnTouchOutside(false);

        }
        forget_dialog.show();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){


            case android.R.id.home:
                UserDetailsActivity.this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
