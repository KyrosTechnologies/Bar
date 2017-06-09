package com.kyros.technologies.bar.Common.activity.Activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
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

import org.json.JSONObject;

public class SignUpActivity extends AppCompatActivity {

    private EditText first_name,last_name,mobile_number,email,venue_name,country;
    private String fn;
    private String ln;
    private String mob;
    private String mail;
    private String con;
    private String venue;
    private PreferenceManager store;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_sign_up);
        first_name=(EditText)findViewById(R.id.first_name);
        last_name=(EditText)findViewById(R.id.last_name);
        mobile_number=(EditText)findViewById(R.id.mobile_number);
        email=(EditText)findViewById(R.id.email);
        venue_name=(EditText)findViewById(R.id.venue_name);
        country=(EditText)findViewById(R.id.country);
        store= PreferenceManager.getInstance(getApplicationContext());

        try {
            Bundle bundle = getIntent().getExtras();
            String title = bundle.getString("title");
            if (title != null && !title.isEmpty()) {
                actionBar.setTitle(title);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void StateChangeWaggonapi(final String fn, String ln, String mob, String mail, String con, String venue) {
        String tag_json_obj = "json_obj_req";
        String url = EndURL.URL+"manageUserProfile";
        //  String url = "http://192.168.0.109:8080/Bar/rest/getLiquorList";
        Log.d("waggonurl", url);
        JSONObject inputLogin=new JSONObject();
        try{
            inputLogin.put("userfirstname",fn);
            inputLogin.put("userlastname",ln);
            inputLogin.put("usermobilenumber",mob);
            inputLogin.put("useremail",mail);
            inputLogin.put("uservenuename",venue);
            inputLogin.put("usercountry",con);
            inputLogin.put("userofteninventory","");
            inputLogin.put("userinventorytime",0);


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
                    boolean success=obj.getBoolean("issuccess");
                    if (success){

//                        JSONArray array=obj.getJSONArray("userList");
//                        for (int i=0;i<array.length();i++){
//                            JSONObject first=array.getJSONObject(i);
//                            int userprofile=first.getInt("userprofileid");
//                            String fname=first.getString("userfirstname");
//                            String lname=first.getString("userlastname");
//                            String number=first.getString("usermobilenumber");
//                            String email=first.getString("useremail");
//                            String venue=first.getString("uservenuename");
//                            String country=first.getString("usercountry");
//                            String inventory=first.getString("userofteninventory");
//                            int inventorytime=first.getInt("userinventorytime");
//                            boolean active=first.getBoolean("isactive");
//                            String create=first.getString("createdon");
//                            String modify=first.getString("modifiedon");
//                            int id=first.getInt("id");
//                        }

                        Toast.makeText(getApplicationContext(),"Sucessfully registered",Toast.LENGTH_SHORT).show();

                        Intent i=new Intent(SignUpActivity.this,LoginActivity.class);
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
                if(error!=null){
                    Toast.makeText(getApplicationContext(),"Error: "+error.toString(),Toast.LENGTH_SHORT).show();
                }
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_done, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_done:
                fn=first_name.getText().toString();
                ln=last_name.getText().toString();
                mob=mobile_number.getText().toString();
                mail=email.getText().toString();
                con=country.getText().toString();
                venue=venue_name.getText().toString();

//
//                if (fn==null&&fn.isEmpty()){
//                    Toast.makeText(getApplicationContext(),"Please Enter First Name",Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                if (ln==null&&ln.isEmpty()){
//                    Toast.makeText(getApplicationContext(),"Please Enter Last Name",Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                if (mob==null&&mob.isEmpty()){
//                    Toast.makeText(getApplicationContext(),"Please Enter Mobile Number",Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                if (mail==null&&mail.isEmpty()){
//                    Toast.makeText(getApplicationContext(),"Please Enter Email Address",Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                if (con==null&&con.isEmpty()){
//                    Toast.makeText(getApplicationContext(),"Please Enter Country",Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                if (venue==null&&venue.isEmpty()){
//                    Toast.makeText(getApplicationContext(),"Please Enter Venue",Toast.LENGTH_SHORT).show();
//                    return;
//                }
                StateChangeWaggonapi(fn,ln,mob,mail,con,venue);
                break;

            case android.R.id.home:
                SignUpActivity.this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
