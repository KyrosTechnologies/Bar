package com.kyros.technologies.bar.Common.activity.Activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kyros.technologies.bar.Common.activity.Adapter.UserManagementAdapter;
import com.kyros.technologies.bar.Common.activity.model.BarAccess;
import com.kyros.technologies.bar.Common.activity.model.UserManagementModel;
import com.kyros.technologies.bar.R;
import com.kyros.technologies.bar.ServiceHandler.ServiceHandler;
import com.kyros.technologies.bar.SharedPreferences.PreferenceManager;
import com.kyros.technologies.bar.utils.EndURL;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserManagementActivity extends AppCompatActivity {
        private LinearLayout add_user;
    private RecyclerView recycler_usermangemet;
    private ArrayList<UserManagementModel>userlist=new ArrayList<UserManagementModel>();
    private UserManagementAdapter adapter;
    private PreferenceManager store;
    private String UserprofileId=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_user_management);
        store= PreferenceManager.getInstance(getApplicationContext());
        UserprofileId=store.getUserProfileId();
        recycler_usermangemet=(RecyclerView)findViewById(R.id.recycler_usermangemet);
        adapter=new UserManagementAdapter(UserManagementActivity.this,userlist);
        RecyclerView.LayoutManager layoutManagersecond=new LinearLayoutManager(getApplicationContext());
        recycler_usermangemet.setLayoutManager(layoutManagersecond);
        recycler_usermangemet.setItemAnimator(new DefaultItemAnimator());
        recycler_usermangemet.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        add_user=(LinearLayout)findViewById(R.id.add_user);
        add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserManagementActivity.this,UserDetailsActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        GetUserDetailList();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                UserManagementActivity.this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void GetUserDetailList() {
        String tag_json_obj = "json_obj_req";
        String url = EndURL.URL+"GetUserManagementByUserProfileId/"+UserprofileId;
        //  String url = "http://192.168.0.109:8080/Bar/rest/getLiquorList";
        Log.d("waggonurl", url);

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, (String)null, new Response.Listener<JSONObject>() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onResponse(JSONObject response) {
                Log.d("List Response",response.toString());
                userlist.clear();
                ArrayList<BarAccess>barAccessArrayList=new ArrayList<BarAccess>();
                barAccessArrayList.clear();
                try {

                    JSONObject obj=new JSONObject(response.toString());
                    String message=obj.getString("Message");
                    boolean success=obj.getBoolean("IsSuccess");
                    if (success){

                        JSONArray array=obj.getJSONArray("UserManagementList");
                        for (int i=0;i<array.length();i++){
                            JSONObject first=array.getJSONObject(i);
                            int userprofile=first.getInt("UserProfileId");
                            int barid=first.getInt("BarId");
                            int usermanagementid=first.getInt("UserManagementId");
//                            String barname=first.getString("BarName");
                            String username=first.getString("UserName");
                            String email=first.getString("UserEmail");
                            String userrole=first.getString("UserRole");
                            String ParentUserProfileId=first.getString("ParentUserProfileId");
                            JSONArray BarList=first.getJSONArray("BarList");
                            for(int j=0;j<BarList.length();j++){
                                JSONObject second=BarList.getJSONObject(j);
                                String Id=second.getString("Id");
                                String BarName=second.getString("BarName");
                                String CreatedOn=second.getString("CreatedOn");
                                String UserProfileId=second.getString("UserProfileId");
                                int BarId=second.getInt("BarId");
                                int UserManagementId=second.getInt("UserManagementId");
                                BarAccess barAccess=new BarAccess();
                                barAccess.setId(Id);
                                barAccess.setBarName(BarName);
                                barAccess.setCreatedOn(CreatedOn);
                                barAccess.setUserProfileId(UserProfileId);
                                barAccess.setBarId(BarId);
                                barAccess.setUserManagementId(UserManagementId);
                                barAccessArrayList.add(barAccess);
                            }
                            String number=null;
                            try {
                                number=first.getString("CreatedOn");
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            String modify=null;
                            try {
                                modify=first.getString("ModifiedOn");
                            }catch (Exception e) {
                                e.printStackTrace();
                            }
                            UserManagementModel managementModel=new UserManagementModel();
                            managementModel.setId(usermanagementid);
                            managementModel.setUserEmail(email);
                            managementModel.setUserRole(userrole);
                            managementModel.setUserName(username);
                            managementModel.setBarAccess(barAccessArrayList);
                            managementModel.setParentUserProfileId(ParentUserProfileId);
                            userlist.add(managementModel);


                        }


                    }else {
                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();

                    }
adapter.notifyDataSetChanged();
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
