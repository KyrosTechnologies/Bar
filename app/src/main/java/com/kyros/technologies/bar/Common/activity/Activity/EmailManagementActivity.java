package com.kyros.technologies.bar.Common.activity.Activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kyros.technologies.bar.Common.activity.Adapter.EmailManagementAdapter;
import com.kyros.technologies.bar.R;
import com.kyros.technologies.bar.ServiceHandler.ServiceHandler;
import com.kyros.technologies.bar.SharedPreferences.PreferenceManager;
import com.kyros.technologies.bar.utils.EmailManagement;
import com.kyros.technologies.bar.utils.EndURL;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class EmailManagementActivity extends AppCompatActivity {
    private LinearLayout add_email_management,management;
    private TextView email_manage_get;
    private RecyclerView email_recycler;
    private PreferenceManager store;
    private EmailManagementAdapter adapter;
    private ArrayList<EmailManagement> emailManagementArrayList=new ArrayList<EmailManagement>();
    private String UserprofileId=null;
    private String UserEmail=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_email_management);
        email_recycler=(RecyclerView)findViewById(R.id.email_recycler);
        adapter=new EmailManagementAdapter(EmailManagementActivity.this,emailManagementArrayList);
        RecyclerView.LayoutManager layoutManagersecond=new LinearLayoutManager(getApplicationContext());
        store= PreferenceManager.getInstance(getApplicationContext());
        UserprofileId=store.getUserProfileId();
        UserEmail=store.getUserEmail();
        Log.d("Email : "+UserprofileId,UserEmail);
//        GetEmail();
        adapter.notifyDataSetChanged();
        email_recycler.setLayoutManager(layoutManagersecond);
        email_recycler.setItemAnimator(new DefaultItemAnimator());
        email_recycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        add_email_management=(LinearLayout)findViewById(R.id.add_email_management);
        management=(LinearLayout)findViewById(R.id.management);
        email_manage_get=(TextView)findViewById(R.id.email_manage_get);
        add_email_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EmailManagementActivity.this,AddEmailActivity.class);
                startActivity(intent);
            }
        });
//        management.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(EmailManagementActivity.this,AddEmailActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    private void GetEmail() {
        String tag_json_obj = "json_obj_req";
        final String url = EndURL.URL+"getEmailbyUserprofileid/"+UserprofileId;
        //  String url = "http://192.168.0.109:8080/Bar/rest/getLiquorList";
        Log.d("waggonurl", url);

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, (String)null, new Response.Listener<JSONObject>() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Email id",response.toString());
                try {

                    JSONObject obj=new JSONObject(response.toString());
                    String message=obj.getString("message");
                    boolean success=obj.getBoolean("issuccess");
                    if (success){

                        JSONArray array=obj.getJSONArray("model");
                        for (int i=0;i<array.length();i++){
                            JSONObject first=array.getJSONObject(i);
                            int id=first.getInt("id");
                            int userprofile=first.getInt("userprofileid");
                            String useremail=first.getString("useremail");
                            String createdon=first.getString("createdon");
                            EmailManagement emailManagement=new EmailManagement();
                            emailManagement.setCreatedon(createdon);
                            emailManagement.setUseremail(useremail);
                            emailManagement.setId(id);
                            emailManagement.setUserprofile(userprofile);
                            emailManagementArrayList.add(emailManagement);
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

//                texts.setText(error.toString());
            }
        }) {

        };
        ServiceHandler.getInstance().addToRequestQueue(objectRequest, tag_json_obj);

    }

    @Override
    protected void onResume() {
        super.onResume();
        GetEmail();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){


            case android.R.id.home:
                EmailManagementActivity.this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
