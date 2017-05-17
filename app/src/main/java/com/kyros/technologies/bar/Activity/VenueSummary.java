package com.kyros.technologies.bar.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.kyros.technologies.bar.R;

/**
 * Created by Rohin on 04-05-2017.
 */

public class VenueSummary extends AppCompatActivity {

    private LinearLayout venue_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.venue_summary);
        venue_details=(LinearLayout)findViewById(R.id.venue_details);
        venue_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(VenueSummary.this,BottleDetails.class);
                startActivity(i);
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                VenueSummary.this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
