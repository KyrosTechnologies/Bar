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
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kyros.technologies.bar.R;
import com.kyros.technologies.bar.Adapters.BarAdapter;
import com.kyros.technologies.bar.ServiceHandler.ServiceHandler;
import com.kyros.technologies.bar.SharedPreferences.PreferenceManager;
import com.kyros.technologies.bar.utils.EndURL;
import com.kyros.technologies.bar.utils.MyBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class BarActivity extends AppCompatActivity {
    private LinearLayout front_bar,add_bar_acti;
    private AlertDialog barDialog;
    private RecyclerView bar_recycler;
    private BarAdapter adapter;
    private String barname;
    private String userprofile;
    private String UserProfileId=null;
    private String BarName=null;
    private String BarCreated=null;
    private PreferenceManager store;
    private ArrayList<MyBar>myBarArrayList=new ArrayList<MyBar>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_bar);
        store= PreferenceManager.getInstance(getApplicationContext());
        UserProfileId=store.getUserProfileId();
        BarName=store.getBarName();
        BarCreated=store.getBarDateCreated();
        bar_recycler=(RecyclerView)findViewById(R.id.bar_recycler);
        adapter=new BarAdapter(BarActivity.this,myBarArrayList);
        RecyclerView.LayoutManager layoutManagersecond=new LinearLayoutManager(getApplicationContext());
        bar_recycler.setLayoutManager(layoutManagersecond);
        bar_recycler.setItemAnimator(new DefaultItemAnimator());
        bar_recycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        front_bar=(LinearLayout)findViewById(R.id.front_bar);
        add_bar_acti=(LinearLayout)findViewById(R.id.add_bar_acti);
        add_bar_acti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBarDialog();
            }
        });
        GetBarList();
        adapter.notifyDataSetChanged();
//        front_bar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(BarActivity.this,AddSectionActivity.class);
//                startActivity(intent);
//            }
//        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.home_bar, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){


            case android.R.id.home:
                BarActivity.this.finish();
                return true;
//            case R.id.home_bar:
//                BarActivity.this.finish();
//                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void AddBarApi(final String userprofile, final String barname) {
        String tag_json_obj = "json_obj_req";
        String url = EndURL.URL+"insertBar";
        //  String url = "http://192.168.0.109:8080/Bar/rest/getLiquorList";
        Log.d("waggonurl", url);
        JSONObject inputLogin=new JSONObject();
        try{
            inputLogin.put("userprofileid",userprofile);
            inputLogin.put("barname",barname);


        }catch (Exception e){
            e.printStackTrace();
        }
        Log.d("inputJsonuser",inputLogin.toString());
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, url, inputLogin, new Response.Listener<JSONObject>() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onResponse(JSONObject response) {
                Log.d("List Response",response.toString());
                myBarArrayList.clear();
                try {

                    JSONObject obj=new JSONObject(response.toString());
                    String message=obj.getString("message");
                    boolean success=obj.getBoolean("issucess");
                    if (success){

                        JSONArray array=obj.getJSONArray("model");
                        for (int i=0;i<array.length();i++){
                            JSONObject first=array.getJSONObject(i);
                            int userprofile=first.getInt("userprofileid");
                            store.putUserProfileId(String.valueOf(userprofile));
                            String fname=first.getString("barname");
                            store.putBarName(String.valueOf(fname));
                            int lname=first.getInt("barid");
                            //store.putBarId(String.valueOf(lname));
                            String number=null;
                            try {
                                number=first.getString("datecreated");
                                store.putBarDateCreated(String.valueOf(number));
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                            MyBar bars=new MyBar();
                            bars.setid(lname);
                            bars.setBarname(fname);
                            bars.setUserprofileid(userprofile);
                            bars.setDatecreated(number);
                            myBarArrayList.add(bars);
                        }

                        Toast.makeText(getApplicationContext(),"Sucessfully Created",Toast.LENGTH_SHORT).show();
                        dismissBarDialog();


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


    private void GetBarList() {
        String tag_json_obj = "json_obj_req";
        String url = EndURL.URL+"getBarbyUserProfileId/"+UserProfileId;
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
                    boolean success=obj.getBoolean("issucess");
                    if (success){

                        JSONArray array=obj.getJSONArray("model");
                        for (int i=0;i<array.length();i++){
                            JSONObject first=array.getJSONObject(i);
                            int userprofile=first.getInt("userprofileid");
                            store.putUserProfileId(String.valueOf(userprofile));
                            String fname=first.getString("barname");
                            store.putBarName(String.valueOf(fname));
                            int lname=first.getInt("barid");
                            //store.putBarId(String.valueOf(lname));
                            String number=null;
                            try {
                                number=first.getString("datecreated");
                                store.putBarDateCreated(String.valueOf(number));
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                            MyBar bars=new MyBar();
                            bars.setid(lname);
                            bars.setBarname(fname);
                            bars.setUserprofileid(userprofile);
                            bars.setDatecreated(number);
                            myBarArrayList.add(bars);
                        }

                        Toast.makeText(getApplicationContext(),"Sucessfully Created",Toast.LENGTH_SHORT).show();
                        dismissBarDialog();


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

    private void showBarDialog(){
    if(barDialog==null){
        AlertDialog.Builder builder=new AlertDialog.Builder(BarActivity.this);
        LayoutInflater inflater =getLayoutInflater();
        View view=inflater.inflate(R.layout.add_bar_dialog,null);
      final  EditText barname_bar=(EditText)view.findViewById(R.id.barname_bar);
        TextView back_bar=(TextView)view.findViewById(R.id.back_bar);
        TextView done_bar=(TextView)view.findViewById(R.id.done_bar);
        back_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissBarDialog();
            }
        });
        done_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String barname=barname_bar.getText().toString();
                if(barname!=null &&!barname.isEmpty()){
                    AddBarApi(UserProfileId,barname);
                    Toast.makeText(getApplicationContext(),"Bar name : "+barname,Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Done clicked!",Toast.LENGTH_SHORT).show();

                }
            }
        });
        builder.setView(view);
        barDialog=builder.create();
        barDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        barDialog.setCancelable(false);
        barDialog.setCanceledOnTouchOutside(false);
    }
    barDialog.show();

}private void dismissBarDialog(){
        if(barDialog!=null && barDialog.isShowing()){
            barDialog.dismiss();
        }

    }
    @Override
    protected void onStop() {
        super.onStop();
        dismissBarDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissBarDialog();
    }
}
