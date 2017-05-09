package com.kyros.technologies.bar;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Rohin on 04-05-2017.
 */

public class InventoryActivity extends AppCompatActivity{

    private RecyclerView inventory_type_list;
    private InventoryType adapter;
    private TextView bottle_custom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.inventory_type_list);
        inventory_type_list=(RecyclerView)findViewById(R.id.inventory_type_list);
        bottle_custom=(TextView)findViewById(R.id.bottle_custom);
        adapter=new InventoryType(InventoryActivity.this);
        inventory_type_list.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        inventory_type_list.setItemAnimator(new DefaultItemAnimator());
        inventory_type_list.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        bottle_custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(InventoryActivity.this,AddToInventory.class);
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                InventoryActivity.this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
