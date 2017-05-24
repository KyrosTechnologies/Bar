package com.kyros.technologies.bar.Common.activity.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.kyros.technologies.bar.R;

public class SettingActivity extends AppCompatActivity {
    private LinearLayout user_management,email_mangaement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_setting);
        user_management=(LinearLayout)findViewById(R.id.user_management);
        email_mangaement=(LinearLayout)findViewById(R.id.email_mangaement);
        email_mangaement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SettingActivity.this,EmailManagementActivity.class);
                startActivity(intent);
            }
        });
        user_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SettingActivity.this,UserManagementActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){


            case android.R.id.home:
                SettingActivity.this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
