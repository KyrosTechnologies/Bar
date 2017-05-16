package com.kyros.technologies.bar;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AddBottleActivity extends AppCompatActivity {
    private LinearLayout my_inventory_list;
    private TextView add_inventory_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_add_bottle);
        my_inventory_list=(LinearLayout)findViewById(R.id.my_inventory_list);
        add_inventory_type=(TextView)findViewById(R.id.add_inventory_type);
        my_inventory_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddBottleActivity.this,MyInventoryListActivity.class);
                startActivity(intent);
            }
        });

        add_inventory_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AddBottleActivity.this,InventoryActivity.class);
                startActivity(i);
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                AddBottleActivity.this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
