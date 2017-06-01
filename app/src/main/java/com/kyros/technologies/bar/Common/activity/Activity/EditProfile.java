package com.kyros.technologies.bar.Common.activity.Activity;

import android.annotation.TargetApi;
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

import org.json.JSONArray;
import org.json.JSONObject;

public class EditProfile extends AppCompatActivity {

    private EditText profile_first_name,profile_last_name,profile_mobile_number,profile_email,profile_venue_name,profile_country;
    private String fn;
    private String ln;
    private String mob;
    private String mail;
    private String con;
    private String venue;
    private String pass;
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
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_edit_profile);
        profile_first_name=(EditText)findViewById(R.id.profile_first_name);
        profile_last_name=(EditText)findViewById(R.id.profile_last_name);
        profile_mobile_number=(EditText)findViewById(R.id.profile_mobile_number);
        profile_email=(EditText)findViewById(R.id.profile_email);
        profile_venue_name=(EditText)findViewById(R.id.profile_venue_name);
        profile_country=(EditText)findViewById(R.id.profile_country);
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
        if (Firstname==null){
            Firstname="";

        }
        profile_first_name.setText(Firstname);

        if (Lastname==null){
            Lastname="";

        }
        profile_last_name.setText(Lastname);

        if (MobileNumber==null){
            MobileNumber="";

        }
        profile_mobile_number.setText(MobileNumber);

        if (UserEmail==null){
            UserEmail="";

        }
        profile_email.setText(UserEmail);

        if (Venue==null){
            Venue="";

        }
        profile_venue_name.setText(Venue);

        if (Country==null){
            Country="";

        }
        profile_country.setText(Country);


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

    private void StateChangeWaggonapi(final String fn, String ln, String mob, String mail, String con, String venue, String pass) {
        String tag_json_obj = "json_obj_req";
        String url = EndURL.URL+"updateUserbyProfileId";
        //  String url = "http://192.168.0.109:8080/Bar/rest/getLiquorList";
        Log.d("waggonurl", url);
        JSONObject inputLogin=new JSONObject();
        try{
            inputLogin.put("userprofileid",Integer.parseInt(UserProfileId));
            inputLogin.put("password",pass);
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
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.PUT, url, inputLogin, new Response.Listener<JSONObject>() {
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


                        Toast.makeText(getApplicationContext(),"Sucessfully Updated",Toast.LENGTH_SHORT).show();

                        EditProfile.this.finish();

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
                fn=profile_first_name.getText().toString();
                ln=profile_last_name.getText().toString();
                mob=profile_mobile_number.getText().toString();
                mail=profile_email.getText().toString();
                con=profile_country.getText().toString();
                venue=profile_venue_name.getText().toString();
                StateChangeWaggonapi(fn,ln,mob,mail,con,venue,pass);
                break;

            case android.R.id.home:
                EditProfile.this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
