package com.kyros.technologies.bar;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

public class SectionBottlesActivity extends AppCompatActivity {
    private LinearLayout add_bottle_act,slider_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_section_bottles);
        add_bottle_act=(LinearLayout)findViewById(R.id.add_bottle_act);
        add_bottle_act.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(SectionBottlesActivity.this,AddBottleActivity.class);
                startActivity(intent);
            }
        });
        slider_edit=(LinearLayout)findViewById(R.id.slider_edit);
        slider_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SectionBottlesActivity.this,LiquorSlider.class);
                startActivity(i);
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
