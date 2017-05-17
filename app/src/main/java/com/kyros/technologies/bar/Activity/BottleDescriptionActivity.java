package com.kyros.technologies.bar.Activity;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.kyros.technologies.bar.R;

public class BottleDescriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_bottle_description);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_done,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){


            case android.R.id.home:
                BottleDescriptionActivity.this.finish();
                return true;
            case R.id.action_done:
                Toast.makeText(getApplicationContext(),"Saved successfully!",Toast.LENGTH_SHORT).show();
                BottleDescriptionActivity.this.finish();

                break;

        }

        return super.onOptionsItemSelected(item);
    }
}
