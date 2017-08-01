package com.kyros.technologies.bar.Common.activity.Activity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kyros.technologies.bar.Inventory.Activity.Activity.BarActivity;
import com.kyros.technologies.bar.R;
import com.kyros.technologies.bar.ServiceHandler.ServiceHandler;
import com.kyros.technologies.bar.ServiceHandler.SessionManager;
import com.kyros.technologies.bar.SharedPreferences.PreferenceManager;
import com.kyros.technologies.bar.utils.EndURL;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

public class LoginActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private TextView forget_password_login,login_user,signup_login;
    private AlertDialog forget_dialog;
    private EditText password;
    private AutoCompleteTextView email_address;
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
    private SessionManager session;
    private String InventoryTime=null;
    private boolean Isactive;
    private String Create=null;
    private String Modifi=null;
    private String Ids=null;
    private String Password=null;
    private PreferenceManager store;
    private ProgressDialog  progressDialog=null;
    private static final int REQUEST_READ_CONTACTS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_login);
        session = new SessionManager(getApplicationContext());
        changeStatusBarColor();

        signup_login=(TextView) findViewById(R.id.signup_login);
        login_user=(TextView)findViewById(R.id.login_user);
        forget_password_login=(TextView)findViewById(R.id.forget_password_login);
        email_address=(AutoCompleteTextView)findViewById(R.id.email_address);
        populateAutoComplete();

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
                if (mails==null && mails.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please enter valid email address!", Toast.LENGTH_SHORT).show();
                    return ;
                }
                if(passwords==null && passwords.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please enter valid Password!", Toast.LENGTH_SHORT).show();

                    return;
                }
                if(mails!=null &&!mails.isEmpty()&& passwords!=null && !passwords.isEmpty()){
                    StateChangeWaggonapi(mails,passwords);
                }else{
                    Toast.makeText(getApplicationContext(), "Email or Password is not valid!", Toast.LENGTH_SHORT).show();

                }
             //   StateChangeWaggonapi(mails,passwords);
                //Toast.makeText(getApplicationContext(),"Mail : "+mails+passwords,Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }
    private void showDialog(){
        if(progressDialog==null){
            progressDialog=new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage("Loading Please wait..");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(false);


        }
        progressDialog.show();
    }
    private void dismissDialog(){
        if(progressDialog!=null && progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }
    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(email_address, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }
    private void StateChangeWaggonapi(final String mails, String passwords) {
        String tag_json_obj = "json_obj_req";
        String url = EndURL.URL+"userLogin";
        showDialog();
        //  String url = "http://192.168.0.109:8080/Bar/rest/getLiquorList";
        Log.d("waggonurl", url);
        JSONObject inputLogin=new JSONObject();
        try{
            inputLogin.put("Password",passwords);
            inputLogin.put("UserEmail",mails);


        }catch (Exception e){
            e.printStackTrace();
        }
        Log.d("inputJsonuser",inputLogin.toString());
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, url, inputLogin, new Response.Listener<JSONObject>() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onResponse(JSONObject response) {
                Log.d("List Response",response.toString());
                dismissDialog();
                try {

                    JSONObject obj=new JSONObject(response.toString());
                    String message=obj.getString("Message");
                    boolean success=obj.getBoolean("IsSuccess");
                    if (success){

                        JSONArray array=obj.getJSONArray("UserList");
                        for (int i=0;i<array.length();i++){
                            JSONObject first=array.getJSONObject(i);
                            int userprofile=first.getInt("UserProfileId");
                            store.putUserProfileId(String.valueOf(userprofile));
                            String fname=first.getString("UserFirstName");
                            store.putFirstName(String.valueOf(fname));
                            String lname=first.getString("UserLastName");
                            store.putLastName(String.valueOf(lname));
                            String number=first.getString("UserMobileNumber");
                            store.putMobileNumber(String.valueOf(number));
                            String email=first.getString("UserEmail");
                            store.putUserEmail(String.valueOf(email));
                            String venue=first.getString("UserVenueName");
                            store.putVenue(String.valueOf(venue));
                            String country=first.getString("UserCountry");
                            store.putCountry(String.valueOf(country));
                            String inventory=first.getString("UserOftenInventory");
                            store.putInventory(String.valueOf(inventory));
                            int inventorytime=first.getInt("UserInventoryTime");
                            String ParentUserProfileId=first.getString("ParentUserProfileId");
                            String UserRole=first.getString("UserRole");
                            store.putUserRole(UserRole);
                                store.putParentUserProfileId(ParentUserProfileId);
                            store.putinventoryTime(String.valueOf(inventorytime));
                            boolean active=first.getBoolean("IsActive");
                            store.putIsactive(active);
                            String create=first.getString("CreatedOn");
                            store.putCreate(String.valueOf(create));
                            String modify=first.getString("ModifiedOn");
                            store.putModifi(String.valueOf(modify));
                            int id=first.getInt("Id");
                            store.putIds(String.valueOf(id));
                            String password=first.getString("Password");
                            store.putPassword(String.valueOf(password));
                            session.createLoginSession(email,password);

                        }
                        Intent i=new Intent(LoginActivity.this,BarActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
dismissDialog();
                Toast.makeText(getApplicationContext(),"Not Workings",Toast.LENGTH_SHORT).show();


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
        dismissDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closepopup();
        dismissDialog();
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
    closepopup();
    showDialog();
    JSONObject inputLogin=new JSONObject();
    try{
        inputLogin.put("UserEmail",mails);


    }catch (Exception e){
        e.printStackTrace();
    }
    Log.d("inputJsonuser",inputLogin.toString());
    JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, url, inputLogin, new Response.Listener<JSONObject>() {
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        public void onResponse(JSONObject response) {
            dismissDialog();
            Log.d("List Response",response.toString());
            try {

                JSONObject obj=new JSONObject(response.toString());
                String message=obj.getString("Message");
                boolean success=obj.getBoolean("IsSuccess");
                if (success){

                    Toast.makeText(getApplicationContext(),"Password has been Successfully sent to Registered Email",Toast.LENGTH_SHORT).show();
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
dismissDialog();
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

                    }else{
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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return  new CursorLoader(this,
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }
    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        email_address.setAdapter(adapter);
    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
