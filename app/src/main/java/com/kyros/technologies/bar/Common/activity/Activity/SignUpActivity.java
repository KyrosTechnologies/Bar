package com.kyros.technologies.bar.Common.activity.Activity;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity {

    private EditText first_name,last_name,mobile_number,email,venue_name,country,country_code;
    private String fn;
    private String ln;
    private String mob;
    private String mail;
    private String con;
    private String venue;
    private String countrycode;
    private PreferenceManager store;
    private ProgressDialog progressDialog;
    private ArrayList<EditText>containerEditText=new ArrayList<EditText>();


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
        country_code=(EditText)findViewById(R.id.country_code);
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

    @Override
    protected void onStop() {
        dismissPdialog();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissPdialog();
    }

    private void StateChangeWaggonapi(final String fn, String ln, String mob, String mail, String con, String venue) {
        String tag_json_obj = "json_obj_req";
        String url = EndURL.URL+"manageUserProfile";
        //  String url = "http://192.168.0.109:8080/Bar/rest/getLiquorList";
        Log.d("waggonurl", url);
        showPdialog();
        JSONObject inputLogin=new JSONObject();
        try{
            inputLogin.put("UserFirstName",fn);
            inputLogin.put("UserLastName",ln);
            inputLogin.put("UserMobileNumber",mob);
            inputLogin.put("UserEmail",mail);
            inputLogin.put("UserVenueName",venue);
            inputLogin.put("UserCountry",con);
            inputLogin.put("UserOftenInventory","");
            inputLogin.put("UserInventoryTime",0);
            inputLogin.put("UserRole","admin");
            inputLogin.put("ParentUserProfileId",JSONObject.NULL);


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
dismissPdialog();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
dismissPdialog();
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
    private void showPdialog(){
        if(progressDialog==null){
            progressDialog=new ProgressDialog(SignUpActivity.this);
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
                containerEditText.add(first_name);
                ln=last_name.getText().toString();
                containerEditText.add(last_name);

                mob=mobile_number.getText().toString();
                containerEditText.add(mobile_number);

                mail=email.getText().toString();
                containerEditText.add(email);

                con=country.getText().toString();
                containerEditText.add(country);

                venue=venue_name.getText().toString();
                containerEditText.add(venue_name);

                countrycode=country_code.getText().toString();
                containerEditText.add(country_code);



                    if(fn!=null &&!fn.isEmpty()&& ln!=null && !ln.isEmpty()&&mob!=null && !mob.isEmpty()&&mail!=null&& !mail.isEmpty()&&con!=null &&!con.isEmpty()&&venue!=null &&!venue.isEmpty()&&countrycode!=null &&!countrycode.isEmpty()){
                        StateChangeWaggonapi(fn,ln,countrycode+" "+mob,mail,con,venue);

                    }else{
                        Toast.makeText(getApplicationContext(),"Some fileds are missing ! Please check that",Toast.LENGTH_SHORT).show();
                    }
               //shfkjshdfkhsdkfhksdhfk
                break;

            case android.R.id.home:
                SignUpActivity.this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
