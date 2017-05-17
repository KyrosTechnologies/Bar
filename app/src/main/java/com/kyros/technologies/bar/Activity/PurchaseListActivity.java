package com.kyros.technologies.bar.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.kyros.technologies.bar.Adapters.PurchaseListAdapter;
import com.kyros.technologies.bar.R;

public class PurchaseListActivity extends AppCompatActivity {
    private LinearLayout my_inventory_list;
    private RecyclerView purchase_recycler;
    private PurchaseListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_purchase_list);
        purchase_recycler=(RecyclerView)findViewById(R.id.purchase_recycler);
        adapter=new PurchaseListAdapter(PurchaseListActivity.this);
        RecyclerView.LayoutManager layoutManagersecond=new LinearLayoutManager(getApplicationContext());
        purchase_recycler.setLayoutManager(layoutManagersecond);
        purchase_recycler.setItemAnimator(new DefaultItemAnimator());
        purchase_recycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        my_inventory_list=(LinearLayout)findViewById(R.id.my_inventory_list);
        my_inventory_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PurchaseListActivity.this,InventoryActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_bar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){


            case android.R.id.home:
                PurchaseListActivity.this.finish();
                return true;
            case R.id.home_bar:
                Intent intent=new Intent(PurchaseListActivity.this,LandingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
