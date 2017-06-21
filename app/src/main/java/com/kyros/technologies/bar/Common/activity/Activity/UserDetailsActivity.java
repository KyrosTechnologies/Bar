package com.kyros.technologies.bar.Common.activity.Activity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kyros.technologies.bar.Common.activity.Adapter.UserDetailsAdapter;
import com.kyros.technologies.bar.Common.activity.model.AdapterBarModel;
import com.kyros.technologies.bar.Common.activity.model.BarAccess;
import com.kyros.technologies.bar.R;
import com.kyros.technologies.bar.ServiceHandler.ServiceHandler;
import com.kyros.technologies.bar.SharedPreferences.PreferenceManager;
import com.kyros.technologies.bar.utils.EndURL;
import com.kyros.technologies.bar.utils.UserDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UserDetailsActivity extends AppCompatActivity
implements UserDetailsAdapter.OnClickInAdapter {

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
    private EditText email_user_details,name_user_details;
    private String selectrole=null;
    private String UserName=null;
    private String UserEmail=null;
    private String UserRole=null;
    private String VenueName=null;
    private String Country=null;

    private ArrayList<BarAccess> barAccess=new ArrayList<BarAccess>();
    private ArrayList<AdapterBarModel> adapterbaraccessdatalist=new ArrayList<AdapterBarModel>();
    private ProgressDialog progressDialog;
    private String BarAccessString=null;
    private RelativeLayout remove_usermanagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_user_details);
        store= PreferenceManager.getInstance(getApplicationContext());
VenueName=store.getVenue();
        Country=store.getCountry();
        role=(LinearLayout)findViewById(R.id.role);
        role.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openpopup();
            }
        });
        remove_usermanagement=(RelativeLayout)findViewById(R.id.remove_usermanagement);
        user_details_recycler=(RecyclerView)findViewById(R.id.user_details_recycler);
        name_user_details=(EditText)findViewById(R.id.name_user_details);
        email_user_details=(EditText)findViewById(R.id.email_user_details);
        select_role=(TextView)findViewById(R.id.select_role);
        try{
            Bundle bundle=getIntent().getExtras();
            UserName=bundle.getString("username");
            UserEmail=bundle.getString("useremail");
            UserRole=bundle.getString("userrole");
            BarAccessString=bundle.getString("baraccess");
            if(UserName!=null){
                name_user_details.setText(UserName);
            }if(UserEmail!=null){
                email_user_details.setText(UserEmail);
            }if(UserRole!=null){
                if(UserRole.equals("admin")){
                    select_role.setText("Admin");

                }else if(UserRole.equals("basic")){
                    select_role.setText("Basic");
                }else {
                    select_role.setText("Nope");
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            barAccess.clear();
            Gson gsons=new Gson();
            Type type1=new TypeToken<List<BarAccess>>(){}.getType();
            barAccess=gsons.fromJson(BarAccessString,type1);

        }catch (Exception e){
            e.printStackTrace();
        }
       // barAccess= TempStore.getHolder().getBarAccess();


        adapter=new UserDetailsAdapter(UserDetailsActivity.this,userDetailArrayList,barAccess);
        RecyclerView.LayoutManager layoutManagersecond=new LinearLayoutManager(getApplicationContext());
        user_details_recycler.setLayoutManager(layoutManagersecond);
        user_details_recycler.setItemAnimator(new DefaultItemAnimator());
        user_details_recycler.setAdapter(adapter);
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
        //GetUserDetailList();
        remove_usermanagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName=name_user_details.getText().toString();
                String userEmail=email_user_details.getText().toString();

                if(userName!=null&& !userName.isEmpty()&&userEmail!=null&&!userEmail.isEmpty()){
                    DeleteUserAPI(UserProfileId, String.valueOf(id));
                }else {
                    Toast.makeText(getApplicationContext(), "user name and user email is empty!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void DeleteUserAPI(String userProfileId,String Id) {
        String tag_json_obj = "json_obj_req";
        String url = EndURL.URL+"deleteUserManagement/"+userProfileId+"/"+Id;
        Log.d("waggonurl", url);
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, (String)null, new Response.Listener<JSONObject>() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onResponse(JSONObject response) {
                Log.d("List Response",response.toString());
                try {

                    JSONObject obj=new JSONObject(response.toString());
                    String message=obj.getString("Message");
                    boolean success=obj.getBoolean("IsSuccess");
                    if (success){
                        Toast.makeText(getApplicationContext(),"Successfully Deleted!",Toast.LENGTH_SHORT).show();

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

    private void showPdialog(){
        if(progressDialog==null){
            progressDialog=new ProgressDialog(UserDetailsActivity.this);
            progressDialog.setMessage("Please Wait!....");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(false);
            progressDialog.setTitle("Loading..");

        }
        progressDialog.show();
    }
    private void dismissPdialog(){
        if(progressDialog!=null && progressDialog.isShowing()){
            progressDialog.dismiss();
        }
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
                    String message=obj.getString("Message");
                    boolean success=obj.getBoolean("IsSuccess");
                    if (success){

                        JSONArray array=obj.getJSONArray("UserManagementList");
                        for (int i=0;i<array.length();i++){
                            JSONObject first=array.getJSONObject(i);
                            int userprofile=first.getInt("UserProfileId");
                            int id=first.getInt("Id");
                            String barname=first.getString("BarName");
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
        dismissPdialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closepopup();
        dismissPdialog();
    }

    private void closepopup(){
        if(forget_dialog!=null&& forget_dialog.isShowing()){
            forget_dialog.dismiss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        GetUserDetailList();
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
                    selectrole="basic";
                    select_role.setText("Basic");
                    closepopup();
                }
            });
            admin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectrole="admin";
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
                if(UserName==null){
                    Toast.makeText(getApplicationContext(),"New User Registration !",Toast.LENGTH_SHORT).show();

                    String userName=name_user_details.getText().toString();
                    String userEmail=email_user_details.getText().toString();
                    String role=select_role.getText().toString();
                    if(role.equals("Admin")){
                        role="admin";
                    }
                    if(role.equals("Basic")){
                        role="basic";
                    }
                    if(userName==null&& userName.isEmpty()&&userEmail==null&&userEmail.isEmpty()&&role==null&&role.equals("Select Role")&&role.isEmpty()){
                        Toast.makeText(getApplicationContext(),"Please enter all details !",Toast.LENGTH_SHORT).show();

                    }else{
                        insertUserData(userName,userEmail,role);
                    }


                }else{
                    Toast.makeText(getApplicationContext(),"Update User!",Toast.LENGTH_SHORT).show();

                }
//                Intent i=new Intent(UserDetailsActivity.this,UserManagementActivity.class);
//                startActivity(i);
                break;

            case android.R.id.home:
                UserDetailsActivity.this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void insertUserData(String userName, String userEmail, String role) {
        String tag_json_obj = "json_obj_req";
        String url = EndURL.URL+"insertUserManagement";
        Log.d("Insertuser Url",url);
        JSONObject inputLogin=new JSONObject();
        showPdialog();
        String arrayjson=null;
        try{
            Gson gson=new Gson();
             arrayjson=gson.toJson(adapterbaraccessdatalist);
            Log.d("Adapter String","value is :"+arrayjson);
        }catch (Exception e){
            Log.d("exception_conve_gson",e.getMessage());
        }
        JSONArray jsonArray=null;
        try {
             jsonArray=new JSONArray(arrayjson);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try{
            inputLogin.put("UserProfileId",UserProfileId);
            inputLogin.put("UserEmail",userEmail);
            inputLogin.put("UserName",userName);
            inputLogin.put("UserRole",role);
            inputLogin.put("VenueName",VenueName);
            inputLogin.put("Country",Country);
            inputLogin.put("BarList",jsonArray);



        }catch (Exception e){
            e.printStackTrace();
        }

        Log.d("inputJsonuser",inputLogin.toString());

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, url, inputLogin, new Response.Listener<JSONObject>() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onResponse(JSONObject response) {

                Log.d("List Response",response.toString());
                try {

                    JSONObject obj=new JSONObject(response.toString());
                    String message=obj.getString("Message");
                    boolean success=obj.getBoolean("IsSuccess");
                    if (success){

                        Toast.makeText(getApplicationContext(),"Success fully registered the user! ",Toast.LENGTH_SHORT).show();
                            UserDetailsActivity.this.finish();


                    }else {
                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
                dismissPdialog();


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
dismissPdialog();
                Toast.makeText(getApplicationContext(),"Not Working",Toast.LENGTH_SHORT).show();
//                texts.setText(error.toString());
            }
        }) {

        };
        objectRequest.setRetryPolicy(new DefaultRetryPolicy(
                20*1000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ServiceHandler.getInstance().addToRequestQueue(objectRequest, tag_json_obj);

    }

    @Override
    public void onClickInAdapter(int BarId, String BarName) {
        Log.d("adapter values : "," BarId : "+BarId+" / BarName : "+BarName );
        AdapterBarModel barAccess=new AdapterBarModel();
        barAccess.setBarName(BarName);
        barAccess.setBarId(BarId);
        adapterbaraccessdatalist.add(barAccess);
        try{
            Gson gson=new Gson();
            String adapterbaraccessdata=gson.toJson(adapterbaraccessdatalist);
                Log.d("Adapter String","value is :"+adapterbaraccessdata);
        }catch (Exception e){
            Log.d("exception_conve_gson",e.getMessage());
        }
    }

}
