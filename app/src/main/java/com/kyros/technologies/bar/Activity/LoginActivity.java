package com.kyros.technologies.bar.Activity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kyros.technologies.bar.R;
import com.kyros.technologies.bar.ServiceHandler.ServiceHandler;
import com.kyros.technologies.bar.SharedPreferences.PreferenceManager;
import com.kyros.technologies.bar.utils.EndURL;

import org.json.JSONArray;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private TextView forget_password_login,login_user,signup_login;
    private AlertDialog forget_dialog;
    private EditText email_address,password;
    private String  mails;
    private String passwords;
    private String UserProfileId=null;
    private String Firstname=null;
    private String Lastname=null;
    private String UserEmail=null;
    private String MobileNumber=null;
    private String Venue=null;
    private String Country=null;
    private String Inventory=null;
    private String InventoryTime=null;
    private boolean Isactive;
    private String Create=null;
    private String Modifi=null;
    private String Ids=null;
    private String Password=null;
    private PreferenceManager store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_login);
        changeStatusBarColor();

        signup_login=(TextView) findViewById(R.id.signup_login);
        login_user=(TextView)findViewById(R.id.login_user);
        forget_password_login=(TextView)findViewById(R.id.forget_password_login);
        email_address=(EditText)findViewById(R.id.email_address);
        password=(EditText)findViewById(R.id.password);
        store= PreferenceManager.getInstance(getApplicationContext());
        UserProfileId=store.getUserProfileId();
        Firstname=store.getFirstName();
        Lastname=store.getLastName();
        UserEmail=store.getUserEmail();
        MobileNumber=store.getMobileNumber();
        Venue=store.getVenue();
        Country=store.getCountry();
        Inventory=store.getInventory();
        InventoryTime=store.getInventoryTime();
        Isactive=store.getIsactive();
        Create=store.getCreate();
        Modifi=store.getModifi();
        Ids=store.getIds();
        Password=store.getPassword();
        forget_password_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openpopup();
            }
        });
        signup_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });
        login_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mails=email_address.getText().toString();
                passwords=password.getText().toString();
                StateChangeWaggonapi(mails,passwords);
                //Toast.makeText(getApplicationContext(),"Mail : "+mails+passwords,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void StateChangeWaggonapi(final String mails, String passwords) {
        String tag_json_obj = "json_obj_req";
        String url = EndURL.URL+"userLogin";
        //  String url = "http://192.168.0.109:8080/Bar/rest/getLiquorList";
        Log.d("waggonurl", url);
        JSONObject inputLogin=new JSONObject();
        try{
            inputLogin.put("password",passwords);
            inputLogin.put("useremail",mails);


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
                    String message=obj.getString("message");
                    boolean success=obj.getBoolean("IsSuccess");
                    if (success){

                        JSONArray array=obj.getJSONArray("userList");
                        for (int i=0;i<array.length();i++){
                            JSONObject first=array.getJSONObject(i);
                            int userprofile=first.getInt("userprofileid");
                            store.putUserProfileId(String.valueOf(userprofile));
                            String fname=first.getString("userfirstname");
                            store.putFirstName(String.valueOf(fname));
                            String lname=first.getString("userlastname");
                            store.putLastName(String.valueOf(lname));
                            String number=first.getString("usermobilenumber");
                            store.putMobileNumber(String.valueOf(number));
                            String email=first.getString("useremail");
                            store.putUserEmail(String.valueOf(email));
                            String venue=first.getString("uservenuename");
                            store.putVenue(String.valueOf(venue));
                            String country=first.getString("usercountry");
                            store.putCountry(String.valueOf(country));
                            String inventory=first.getString("userofteninventory");
                            store.putInventory(String.valueOf(inventory));
                            int inventorytime=first.getInt("userinventorytime");
                            store.putinventoryTime(String.valueOf(inventorytime));
                            boolean active=first.getBoolean("isactive");
                            store.putIsactive(active);
                            String create=first.getString("createdon");
                            store.putCreate(String.valueOf(create));
                            String modify=first.getString("modifiedon");
                            store.putModifi(String.valueOf(modify));
                            int id=first.getInt("id");
                            store.putIds(String.valueOf(id));
                            String password=first.getString("password");
                            store.putPassword(String.valueOf(password));
                        }

                        Toast.makeText(getApplicationContext(),"Sucessfully Logged In",Toast.LENGTH_SHORT).show();

                        Intent i=new Intent(LoginActivity.this,LandingActivity.class);
                        startActivity(i);

                    }else {
                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();

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

        };
        ServiceHandler.getInstance().addToRequestQueue(objectRequest, tag_json_obj);

    }


    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
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

private void ForgotPasswordApi(final String mails){
    String tag_json_obj = "json_obj_req";
    String url = EndURL.URL+"ForgotPassword";
    //  String url = "http://192.168.0.109:8080/Bar/rest/getLiquorList";
    Log.d("waggonurl", url);
    JSONObject inputLogin=new JSONObject();
    try{
        inputLogin.put("useremail",mails);


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
                String message=obj.getString("message");
                boolean success=obj.getBoolean("IsSuccess");
                if (success){


                    Toast.makeText(getApplicationContext(),"Password has been Successfully sent to Registered Email",Toast.LENGTH_SHORT).show();
                        closepopup();
                }else {
                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();

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

    };
    objectRequest.setRetryPolicy(new DefaultRetryPolicy(
            20*1000,
            0,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    ServiceHandler.getInstance().addToRequestQueue(objectRequest, tag_json_obj);

}

    private void openpopup() {
        if(forget_dialog==null){
            AlertDialog.Builder builder=new AlertDialog.Builder(LoginActivity.this);
            LayoutInflater inflater=getLayoutInflater();
            View view=inflater.inflate(R.layout.forgot_password_layout,null);
            builder.setView(view);
            final EditText email_forget_password=(EditText)view.findViewById(R.id.email_forget_password);
            TextView back_forget=(TextView)view.findViewById(R.id.back_forget);
            TextView reset_forget=(TextView)view.findViewById(R.id.reset_forget);
            back_forget.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    closepopup();
                }
            });
            reset_forget.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String email=email_forget_password.getText().toString();
                    if(!email.isEmpty()&&email!=null){
                        ForgotPasswordApi(email);
                        Toast.makeText(getApplicationContext(),"Email is: "+email,Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(getApplicationContext(),"Reset is clicked !",Toast.LENGTH_SHORT).show();
                    }
                }
            });
            forget_dialog=builder.create();
            forget_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            forget_dialog.setCancelable(false);
            forget_dialog.setCanceledOnTouchOutside(false);

        }
        forget_dialog.show();

    }
}
