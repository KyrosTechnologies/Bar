package com.kyros.technologies.bar.Common.activity.Activity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kyros.technologies.bar.Inventory.Activity.Activity.BarActivity;
import com.kyros.technologies.bar.Purchase.Activity.Activity.PurchaseListActivity;
import com.kyros.technologies.bar.R;
import com.kyros.technologies.bar.ServiceHandler.ServiceHandler;
import com.kyros.technologies.bar.ServiceHandler.SessionManager;
import com.kyros.technologies.bar.SharedPreferences.PreferenceManager;
import com.kyros.technologies.bar.utils.EndURL;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LandingActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private AlertDialog logoutdialog;
    private LinearLayout bar_activity,purchase_list;
    private SessionManager session;
    private PreferenceManager store;
    private TextView username,email_id,total_quans,parvlie;
    private String userprofileid=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Venue Name");
        session = new SessionManager(getApplicationContext());
        session.checkLogin();
        store = PreferenceManager.getInstance(getApplicationContext());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
       String venue_name= store.getVenue();
        if(venue_name!=null){
            actionBar.setTitle(venue_name);
        }


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        View headerview = navigationView.getHeaderView(0);

        email_id = (TextView) headerview.findViewById(R.id.email_id);
        username = (TextView) headerview.findViewById(R.id.username);
        total_quans = (TextView) findViewById(R.id.total_quans);
        parvlie = (TextView) findViewById(R.id.parvlie);
        try {
            email_id.setText(store.getUserEmail());
            String name=store.getFirstName()+" "+store.getLastName();
            username.setText(name);
        }catch (Exception e){
            e.printStackTrace();
        }

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

            Intent i=new Intent(LandingActivity.this,ParList.class);
            startActivity(i);

        } else if (id == R.id.value_on_hand) {

            Intent intent=new Intent(LandingActivity.this,ValueOnHand.class);
            startActivity(intent);
        } else if (id == R.id.distri_list) {

            Intent i=new Intent(LandingActivity.this,DistributorList.class);
            startActivity(i);

        } else if (id == R.id.venue_sum) {

            Intent i=new Intent(LandingActivity.this,VenueSummary.class);
            startActivity(i);

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
                session.logoutUser();
                store.clear();
                Intent i=new Intent(LandingActivity.this,LoginActivity.class);
                startActivity(i);
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

    @Override
    protected void onResume() {
        super.onResume();
        store = PreferenceManager.getInstance(getApplicationContext());
        userprofileid=store.getUserProfileId();
        StateChangeWaggonapi();
        GetUnderParValue();

    }
    private void StateChangeWaggonapi() {
        String tag_json_obj = "json_obj_req";
        String url = EndURL.URL+ "GetUserTotalBottles/"+userprofileid;

        Log.d("waggonurl", url);

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, (String) null, new Response.Listener<JSONObject>() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onResponse(JSONObject response) {
                Log.d("List Response",response.toString());
                try {

                    JSONObject obj=new JSONObject(response.toString());
                    String message=obj.getString("Message");
                    boolean success=obj.getBoolean("IsSuccess");
                    if (success){

                        String totalbottles=obj.getString("TotalBottles");
                        if(totalbottles!=null){
                            total_quans.setText(totalbottles);
                        }



                    }else {
                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                        total_quans.setText("0");

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }




            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),"Not Working",Toast.LENGTH_SHORT).show();
//                texts.setText(error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders()throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("Authorization", store.getUserProfileId()+"|"+store.getAuthorizationKey());
                return params;
            }
        };
        ServiceHandler.getInstance().addToRequestQueue(objectRequest, tag_json_obj);

    }
    private void GetUnderParValue() {
        String tag_json_obj = "json_obj_req";
        String url = EndURL.URL+ "GetUnderParValue/"+userprofileid;

        Log.d("waggonurl", url);

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, (String) null, new Response.Listener<JSONObject>() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onResponse(JSONObject response) {
                Log.d("List Response",response.toString());
                try {

                    JSONObject obj=new JSONObject(response.toString());
                    String message=obj.getString("Message");
                    boolean success=obj.getBoolean("IsSuccess");
                    if (success){

                        String underparvalue=obj.getString("UnderParValue");
                        if(underparvalue!=null){
                            parvlie.setText(underparvalue);
                        }



                    }else {
                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                        parvlie.setText("0");
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }




            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),"Not Working",Toast.LENGTH_SHORT).show();
//                texts.setText(error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders()throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("Authorization", store.getUserProfileId()+"|"+store.getAuthorizationKey());
                return params;
            }
        };
        ServiceHandler.getInstance().addToRequestQueue(objectRequest, tag_json_obj);

    }
}
