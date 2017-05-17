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
import android.widget.LinearLayout;

import com.kyros.technologies.bar.R;
import com.kyros.technologies.bar.Adapters.SectionBarAdapter;

public class SectionBottlesActivity extends AppCompatActivity {
    private LinearLayout add_bottle_act;
    private SectionBarAdapter adapter;
    private RecyclerView section_bar_recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_section_bottles);
        section_bar_recycler=(RecyclerView)findViewById(R.id.section_bar_recycler);
        adapter=new SectionBarAdapter(SectionBottlesActivity.this);
        RecyclerView.LayoutManager layoutManagersecond=new LinearLayoutManager(getApplicationContext());
        section_bar_recycler.setLayoutManager(layoutManagersecond);
        section_bar_recycler.setItemAnimator(new DefaultItemAnimator());
        section_bar_recycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        add_bottle_act=(LinearLayout)findViewById(R.id.add_bottle_act);
        add_bottle_act.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(SectionBottlesActivity.this,AddBottleActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                SectionBottlesActivity.this.finish();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }
}
