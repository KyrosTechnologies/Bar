package com.kyros.technologies.bar;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BarActivity extends AppCompatActivity {
    private LinearLayout front_bar,add_bar_acti;
    private AlertDialog barDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_bar);
        front_bar=(LinearLayout)findViewById(R.id.front_bar);

        add_bar_acti=(LinearLayout)findViewById(R.id.add_bar_acti);
        add_bar_acti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBarDialog();
            }
        });
        front_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BarActivity.this,AddSectionActivity.class);
                startActivity(intent);
            }
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.home_bar, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){


            case android.R.id.home:
                BarActivity.this.finish();
                return true;
//            case R.id.home_bar:
//                BarActivity.this.finish();
//                break;
        }

        return super.onOptionsItemSelected(item);
    }
private void showBarDialog(){
    if(barDialog==null){
        AlertDialog.Builder builder=new AlertDialog.Builder(BarActivity.this);
        LayoutInflater inflater =getLayoutInflater();
        View view=inflater.inflate(R.layout.add_bar_dialog,null);
      final  EditText barname_bar=(EditText)view.findViewById(R.id.barname_bar);
        TextView back_bar=(TextView)view.findViewById(R.id.back_bar);
        TextView done_bar=(TextView)view.findViewById(R.id.done_bar);
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
                    Toast.makeText(getApplicationContext(),"Bar name : "+barname,Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Done clicked!",Toast.LENGTH_SHORT).show();

                }
            }
        });
        builder.setView(view);
        barDialog=builder.create();
        barDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        barDialog.setCancelable(false);
        barDialog.setCanceledOnTouchOutside(false);
    }
    barDialog.show();

}private void dismissBarDialog(){
        if(barDialog!=null && barDialog.isShowing()){
            barDialog.dismiss();
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
