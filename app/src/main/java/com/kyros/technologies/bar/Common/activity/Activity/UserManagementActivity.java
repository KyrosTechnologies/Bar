package com.kyros.technologies.bar.Common.activity.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.kyros.technologies.bar.Common.activity.Adapter.UserManagementAdapter;
import com.kyros.technologies.bar.Common.activity.model.UserManagementModel;
import com.kyros.technologies.bar.R;
import com.kyros.technologies.bar.SharedPreferences.PreferenceManager;

import java.util.ArrayList;

public class UserManagementActivity extends AppCompatActivity {
        private LinearLayout add_user;
    private RecyclerView recycler_usermangemet;
    private ArrayList<UserManagementModel>userlist=new ArrayList<UserManagementModel>();
    private UserManagementAdapter adapter;
    private PreferenceManager store;
    private String UserprofileId=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_user_management);
        store= PreferenceManager.getInstance(getApplicationContext());
        UserprofileId=store.getUserProfileId();
        recycler_usermangemet=(RecyclerView)findViewById(R.id.recycler_usermangemet);
        adapter=new UserManagementAdapter(UserManagementActivity.this,userlist);
        RecyclerView.LayoutManager layoutManagersecond=new LinearLayoutManager(getApplicationContext());
        recycler_usermangemet.setLayoutManager(layoutManagersecond);
        recycler_usermangemet.setItemAnimator(new DefaultItemAnimator());
        recycler_usermangemet.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        add_user=(LinearLayout)findViewById(R.id.add_user);
        add_user.setOnClickListener(new View.OnClickListener() {
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
