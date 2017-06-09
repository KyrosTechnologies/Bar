package com.kyros.technologies.bar.Common.activity.Activity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kyros.technologies.bar.Common.activity.Adapter.UserDetailsAdapter;
import com.kyros.technologies.bar.R;
import com.kyros.technologies.bar.ServiceHandler.ServiceHandler;
import com.kyros.technologies.bar.SharedPreferences.PreferenceManager;
import com.kyros.technologies.bar.utils.EndURL;
import com.kyros.technologies.bar.utils.UserDetail;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserDetailsActivity extends AppCompatActivity {

    private LinearLayout role;
    private AlertDialog forget_dialog;
    private UserDetailsAdapter adapter;
    private ArrayList<UserDetail> userDetailArrayList=new ArrayList<UserDetail>();
    private PreferenceManager store;
    private String UserProfileId=null;
    private RecyclerView user_details_recycler;
    private int id=0;
    private String barname=null;
    private String createdon=null;
    private String modifiedon=null;
    private TextView select_role,admin,basic;
    private String selectrole=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_user_details);
        role=(LinearLayout)findViewById(R.id.role);
        role.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openpopup();
            }
        });
        user_details_recycler=(RecyclerView)findViewById(R.id.user_details_recycler);
        select_role=(TextView)findViewById(R.id.select_role);

        adapter=new UserDetailsAdapter(UserDetailsActivity.this,userDetailArrayList);
        RecyclerView.LayoutManager layoutManagersecond=new LinearLayoutManager(getApplicationContext());
        user_details_recycler.setLayoutManager(layoutManagersecond);
        user_details_recycler.setItemAnimator(new DefaultItemAnimator());
        user_details_recycler.setAdapter(adapter);
        store= PreferenceManager.getInstance(getApplicationContext());
        UserProfileId=store.getUserProfileId();
        id=UserDetail.getHolder().getId();
        barname=UserDetail.getHolder().getBarname();
        createdon=UserDetail.getHolder().getCreatedon();
        modifiedon=UserDetail.getHolder().getModifiedon();
         try {

         }catch (Exception e){
             e.printStackTrace();
         }
        adapter.notifyDataSetChanged();
        GetUserDetailList();
        adapter.notifyDataSetChanged();

    }

    private void GetUserDetailList() {
        String tag_json_obj = "json_obj_req";
        String url = EndURL.URL+"GetUserManagementBarList/"+UserProfileId;
        //  String url = "http://192.168.0.109:8080/Bar/rest/getLiquorList";
        Log.d("waggonurl", url);

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, (String)null, new Response.Listener<JSONObject>() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onResponse(JSONObject response) {
                Log.d("List Response",response.toString());
                try {

                    JSONObject obj=new JSONObject(response.toString());
                    String message=obj.getString("message");
                    boolean success=obj.getBoolean("issuccess");
                    if (success){

                        JSONArray array=obj.getJSONArray("usermanagementlist");
                        for (int i=0;i<array.length();i++){
                            JSONObject first=array.getJSONObject(i);
                            int userprofile=first.getInt("userprofileid");
                            int id=first.getInt("id");
                            String barname=first.getString("barname");
                            String number=null;
                            try {
                                number=first.getString("createdon");
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            String modify=null;
                            try {
                                modify=first.getString("modifiedon");
                            }catch (Exception e) {
                                e.printStackTrace();
                            }

                            UserDetail userDetail=new UserDetail();
                            userDetail.setId(id);
                            userDetail.setUserprofileid(userprofile);
                            userDetail.setCreatedon(number);
                            userDetail.setBarname(barname);
                            userDetail.setModifiedon(modify);
                            userDetailArrayList.add(userDetail);
                        }


                    }else {
                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

                adapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),"Not Working",Toast.LENGTH_SHORT).show();

            }
        }) {

        };
        ServiceHandler.getInstance().addToRequestQueue(objectRequest, tag_json_obj);

    }


    @Override
    protected void onStop() {
        super.onStop();
        closepopup();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closepopup();
    }

    private void closepopup(){
        if(forget_dialog!=null&& forget_dialog.isShowing()){
            forget_dialog.dismiss();
        }
    }


    private void openpopup() {
        if(forget_dialog==null){
            AlertDialog.Builder builder=new AlertDialog.Builder(UserDetailsActivity.this);
            LayoutInflater inflater=getLayoutInflater();
            View view=inflater.inflate(R.layout.user_details_role,null);
            builder.setView(view);
            TextView back_forget=(TextView)view.findViewById(R.id.back_forget);
            TextView admin=(TextView)view.findViewById(R.id.admin);
            TextView basic=(TextView)view.findViewById(R.id.basic);

            basic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectrole="Basic";
                    select_role.setText("Basic");
                    closepopup();
                }
            });
            admin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectrole="Admin";
                    select_role.setText("Admin");
                    closepopup();
                }
            });
            back_forget.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    closepopup();
                }
            });
            forget_dialog=builder.create();
            forget_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            forget_dialog.setCancelable(false);
            forget_dialog.setCanceledOnTouchOutside(false);

        }
        forget_dialog.show();

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
                Intent i=new Intent(UserDetailsActivity.this,UserManagementActivity.class);
                startActivity(i);
                break;

            case android.R.id.home:
                UserDetailsActivity.this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
