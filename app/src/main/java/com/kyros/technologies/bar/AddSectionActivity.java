package com.kyros.technologies.bar;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AddSectionActivity extends AppCompatActivity {
        private LinearLayout add_section,section_bar;
    private AlertDialog sectionDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_add_section);
        add_section=(LinearLayout)findViewById(R.id.add_section);
        section_bar=(LinearLayout)findViewById(R.id.section_bar);
        section_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBarDialog();
            }
        });
        add_section.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddSectionActivity.this,SectionBottlesActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                AddSectionActivity.this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showBarDialog(){
        if(sectionDialog==null){
            AlertDialog.Builder builder=new AlertDialog.Builder(AddSectionActivity.this);
            LayoutInflater inflater =getLayoutInflater();
            View view=inflater.inflate(R.layout.add_section_dialog,null);
            final EditText barname_bar=(EditText)view.findViewById(R.id.barname_section);
            TextView back_bar=(TextView)view.findViewById(R.id.back_section);
            TextView done_bar=(TextView)view.findViewById(R.id.done_section);
            back_bar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismissBarDialog();
                }
            });
            done_bar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String barname=barname_bar.getText().toString();
                    if(barname!=null &&!barname.isEmpty()){
                        Toast.makeText(getApplicationContext(),"Section name : "+barname,Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"Done clicked!",Toast.LENGTH_SHORT).show();

                    }
                }
            });
            builder.setView(view);
            sectionDialog=builder.create();
            sectionDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            sectionDialog.setCancelable(false);
            sectionDialog.setCanceledOnTouchOutside(false);
        }
        sectionDialog.show();

    }private void dismissBarDialog(){
        if(sectionDialog!=null && sectionDialog.isShowing()){
            sectionDialog.dismiss();
        }

    }
    @Override
    protected void onStop() {
        super.onStop();
        dismissBarDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissBarDialog();
    }
}
