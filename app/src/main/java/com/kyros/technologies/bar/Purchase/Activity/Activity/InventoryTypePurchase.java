package com.kyros.technologies.bar.Purchase.Activity.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.kyros.technologies.bar.Inventory.Activity.Activity.AddToInventory;
import com.kyros.technologies.bar.R;
import com.kyros.technologies.bar.Inventory.Activity.Adapters.InventoryType;
import com.kyros.technologies.bar.Purchase.Activity.Adapters.InventoryPurchaseAdapter;
import com.kyros.technologies.bar.R;

/**
 * Created by Rohin on 29-05-2017.
 */

public class InventoryTypePurchase extends AppCompatActivity {

    private RecyclerView purchase_type_list;
    private InventoryPurchaseAdapter adapter;
    private TextView purchase_bottle_custom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.purchase_inventory_list);
        purchase_type_list=(RecyclerView) findViewById(R.id.purchase_type_list);
        purchase_bottle_custom=(TextView)findViewById(R.id.purchase_bottle_custom);
        adapter=new InventoryPurchaseAdapter(InventoryTypePurchase.this);
        Toast.makeText(getApplicationContext(),"working",Toast.LENGTH_SHORT).show();

        purchase_type_list.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        purchase_type_list.setItemAnimator(new DefaultItemAnimator());
        purchase_type_list.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        purchase_bottle_custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(InventoryTypePurchase.this,PurchaseAddToInventory.class);
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                InventoryTypePurchase.this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}