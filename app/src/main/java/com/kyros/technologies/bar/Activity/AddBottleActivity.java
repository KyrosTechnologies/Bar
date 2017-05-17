package com.kyros.technologies.bar.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.kyros.technologies.bar.R;
import com.kyros.technologies.bar.Adapters.AddBottleAdapter;

public class AddBottleActivity extends AppCompatActivity {
    private TextView add_inventory_type;
    private RecyclerView add_bottle;
    private AddBottleAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_add_bottle);
        add_bottle=(RecyclerView)findViewById(R.id.add_bottle);
        adapter=new AddBottleAdapter(AddBottleActivity.this);
        RecyclerView.LayoutManager layoutManagersecond=new LinearLayoutManager(getApplicationContext());
        add_bottle.setLayoutManager(layoutManagersecond);
        add_bottle.setItemAnimator(new DefaultItemAnimator());
        add_bottle.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        add_inventory_type=(TextView)findViewById(R.id.add_inventory_type);
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
