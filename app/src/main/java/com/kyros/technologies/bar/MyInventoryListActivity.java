package com.kyros.technologies.bar;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.kyros.technologies.bar.database.DatabaseAccess;

import java.util.List;

public class MyInventoryListActivity extends AppCompatActivity {
    private RecyclerView recycler_database;
    private DummyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_my_inventory_list);
        recycler_database=(RecyclerView)findViewById(R.id.recycler_database);
        DatabaseAccess databaseAccess = DatabaseAccess.getDatabaseAccess(this);
        databaseAccess.openDatabase();
        List<String> quotes = databaseAccess.getQuotes();
        databaseAccess.closeDatabase();

        adapter=new DummyAdapter(MyInventoryListActivity.this,quotes);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        recycler_database.setLayoutManager(layoutManager);
        recycler_database.setItemAnimator(new DefaultItemAnimator());
        recycler_database.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){


            case android.R.id.home:
                MyInventoryListActivity.this.finish();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }
}
