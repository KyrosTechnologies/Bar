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

public class AddEmailActivity extends AppCompatActivity {

    private EditText add_email;
    private String  email;
    private String userprofile;
    private PreferenceManager store;
    private String Mail=null;
    private String UserprofileId=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_add_email);
        add_email=(EditText)findViewById(R.id.add_email);
        store= PreferenceManager.getInstance(getApplicationContext());
        Mail=store.getUserEmail();
        UserprofileId=store.getUserProfileId();

        try {
            Bundle bundle=getIntent().getExtras();
            String name=bundle.getString("name");
            if (name!=null){
                add_email.setText(name);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void AddEmailApi(final int userprofile ,String email) {
        String tag_json_obj = "json_obj_req";
        String url = EndURL.URL+"insertEmailMangement";
        //  String url = "http://192.168.0.109:8080/Bar/rest/getLiquorList";
        Log.d("waggonurl", url);
        final JSONObject inputLogin=new JSONObject();
        try{
            inputLogin.put("userprofileid",userprofile);
            inputLogin.put("useremail",email);

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

                        JSONArray array=obj.getJSONArray("model");
                        for (int i=0;i<array.length();i++){
                            JSONObject first=array.getJSONObject(i);
                            int id=first.getInt("id");
                            int userprofile=first.getInt("userprofileid");
                            String email=first.getString("useremail");
                            String cretaeon=first.getString("createdon");

                        }

                        Toast.makeText(getApplicationContext(),"Sucessfully Logged In",Toast.LENGTH_SHORT).show();


                    }else {
                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

                Intent i=new Intent(AddEmailActivity.this,EmailManagementActivity.class);
                startActivity(i);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.email_done, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){


            case android.R.id.home:
                AddEmailActivity.this.finish();
                return true;
            case R.id.email_done:
                String mail=add_email.getText().toString();
                AddEmailApi(Integer.parseInt(UserprofileId),mail);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
