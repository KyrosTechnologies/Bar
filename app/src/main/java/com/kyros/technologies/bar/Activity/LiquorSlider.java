package com.kyros.technologies.bar.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.kyros.technologies.bar.R;

/**
 * Created by Rohin on 05-05-2017.
 */

public class LiquorSlider extends AppCompatActivity {

    private TextView edit_bottle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.liquor_slider);
        edit_bottle=(TextView)findViewById(R.id.edit_bottle);
        edit_bottle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LiquorSlider.this,BottleDescriptionActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_done, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_done:
                Intent i=new Intent(LiquorSlider.this,SectionBottlesActivity.class);
                startActivity(i);
                break;
            case android.R.id.home:
                LiquorSlider.this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}