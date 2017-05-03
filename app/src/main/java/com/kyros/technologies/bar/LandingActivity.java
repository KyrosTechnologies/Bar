package com.kyros.technologies.bar;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LandingActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
        private AlertDialog logoutdialog;
    private LinearLayout bar_activity,purchase_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Venue Name");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        bar_activity=(LinearLayout)findViewById(R.id.go_to_bar);
        purchase_list=(LinearLayout)findViewById(R.id.purchase_list);

        bar_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LandingActivity.this,BarActivity.class);
                startActivity(intent);
            }
        });
        purchase_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LandingActivity.this,PurchaseListActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.par_value) {
        } else if (id == R.id.value_on_hand) {

        } else if (id == R.id.distri_list) {

        } else if (id == R.id.venue_sum) {

        } else if (id == R.id.profile) {
            Intent intent=new Intent(LandingActivity.this,ProfileViewActivity.class);
            startActivity(intent);


        } else if (id == R.id.logout) {
            showLogoutDialog();
        }else if (id == R.id.settings) {
            Intent intent=new Intent(LandingActivity.this,SettingActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
private void showLogoutDialog(){
    if(logoutdialog==null){
        AlertDialog.Builder builder=new AlertDialog.Builder(LandingActivity.this);
        LayoutInflater inflater=getLayoutInflater();
        View view=inflater.inflate(R.layout.logout_dialog,null);
        builder.setView(view);
        TextView yes_logout=(TextView)view.findViewById(R.id.yes_logout);
        TextView back_logout=(TextView)view.findViewById(R.id.back_logout);
        back_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissLogoutDialog();
            }
        });
        yes_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LandingActivity.this.finish();
                Toast.makeText(getApplicationContext(),"Logout successfully!",Toast.LENGTH_SHORT).show();
            }
        });

        logoutdialog=builder.create();
        logoutdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        logoutdialog.setCancelable(false);
        logoutdialog.setCanceledOnTouchOutside(false);
    }
    logoutdialog.show();

}private void dismissLogoutDialog(){
        if(logoutdialog!=null && logoutdialog.isShowing()){
            logoutdialog.dismiss();
        }

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissLogoutDialog();
    }

    @Override
    protected void onStop() {
        super.onStop();
        dismissLogoutDialog();

    }
}
