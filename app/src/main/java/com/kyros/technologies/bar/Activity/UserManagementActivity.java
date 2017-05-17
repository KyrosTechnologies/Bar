package com.kyros.technologies.bar.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.kyros.technologies.bar.R;

public class UserManagementActivity extends AppCompatActivity {
        private LinearLayout user_item,add_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_user_management);
        user_item=(LinearLayout)findViewById(R.id.user_item);
        add_user=(LinearLayout)findViewById(R.id.add_user);
        add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserManagementActivity.this,UserDetailsActivity.class);
                startActivity(intent);
            }
        });
        user_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserManagementActivity.this,UserDetailsActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){


            case android.R.id.home:
                UserManagementActivity.this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
