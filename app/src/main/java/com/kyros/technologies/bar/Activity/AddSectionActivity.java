package com.kyros.technologies.bar.Activity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kyros.technologies.bar.R;
import com.kyros.technologies.bar.Adapters.SectionAdapter;
import com.kyros.technologies.bar.ServiceHandler.ServiceHandler;
import com.kyros.technologies.bar.SharedPreferences.PreferenceManager;
import com.kyros.technologies.bar.utils.EndURL;
import com.kyros.technologies.bar.utils.MyBar;
import com.kyros.technologies.bar.utils.MySection;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddSectionActivity extends AppCompatActivity {
        private LinearLayout section_bar;
        private AlertDialog sectionDialog;
        private RecyclerView section_recycler;
        private SectionAdapter adapter;
        private String sectionname;
        private String userprofile;
        private String BarId=null;
        private String UserProfileId=null;
        private String BarName=null;
        private String BarCreated=null;
        private String SectionId=null;
        private PreferenceManager store;
        private ArrayList<MySection>mySectionArrayList =new ArrayList<MySection>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_add_section);
        store= PreferenceManager.getInstance(getApplicationContext());
        UserProfileId=store.getUserProfileId();
        sectionname=store.getSectionName();
        BarCreated=store.getBarDateCreated();
        SectionId=store.getSectionId();
        section_recycler=(RecyclerView)findViewById(R.id.section_recycler);
        adapter=new SectionAdapter(AddSectionActivity.this,mySectionArrayList);
        RecyclerView.LayoutManager layoutManagersecond=new LinearLayoutManager(getApplicationContext());
        section_recycler.setLayoutManager(layoutManagersecond);
        section_recycler.setItemAnimator(new DefaultItemAnimator());
        section_recycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        BarId=store.getBarId();
        section_bar=(LinearLayout)findViewById(R.id.section_bar);
        section_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBarDialog();
            }
        });
        GetSectionList();
        adapter.notifyDataSetChanged();
        Toast.makeText(getApplicationContext(),"Bar Id :"+BarId,Toast.LENGTH_SHORT).show();
//        add_section.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(AddSectionActivity.this,SectionBottlesActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    private void AddSectionApi(final String userprofile, final String sectionname,final int BarId) {
        String tag_json_obj = "json_obj_req";
        String url = EndURL.URL+"InsertSection";
        //  String url = "http://192.168.0.109:8080/Bar/rest/getLiquorList";
        Log.d("waggonurl", url);
        JSONObject inputLogin=new JSONObject();
        try{
            inputLogin.put("userprofileid",userprofile);
            inputLogin.put("sectionname",sectionname);
            inputLogin.put("barid",BarId);


        }catch (Exception e){
            e.printStackTrace();
        }
        Log.d("inputJsonuser",inputLogin.toString());
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, url, inputLogin, new Response.Listener<JSONObject>() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onResponse(JSONObject response) {
                Log.d("List Response",response.toString());
                mySectionArrayList.clear();
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
                            String fname=first.getString("sectionname");
                            store.putSectionName(String.valueOf(fname));
                            int lname=first.getInt("barid");
                            //store.putBarId(String.valueOf(lname));
                            int sectionid=first.getInt("sectionid");
                            //store.putSectionId(String .valueOf(sectionid));
                            String number=null;
                            try {
                                number=first.getString("datecreated");
                                store.putBarDateCreated(String.valueOf(number));
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                            MySection section=new MySection();
                            section.setSectionid(sectionid);
                            section.setBarid(lname);
                            section.setSectionname(fname);
                            section.setUserprofile(userprofile);
                            section.setSectioncreated(number);
                            mySectionArrayList.add(section);
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


    private void GetSectionList() {
        String tag_json_obj = "json_obj_req";
        String url = EndURL.URL+"getSectionByUserProfileID/"+BarId;
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
                            String fname=first.getString("sectionname");
                            store.putSectionName(String.valueOf(fname));
                            int sectionid=first.getInt("sectionid");
                            //store.putSectionId(String .valueOf(sectionid));
                            int lname=first.getInt("barid");
                            //store.putBarId(String.valueOf(lname));
                            String number=null;
                            try {
                                number=first.getString("datecreated");
                                store.putBarDateCreated(String.valueOf(number));
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                            MySection section=new MySection();
                            section.setSectionid(sectionid);
                            section.setBarid(lname);
                            section.setSectionname(fname);
                            section.setUserprofile(userprofile);
                            section.setSectioncreated(number);
                            mySectionArrayList.add(section);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                AddSectionActivity.this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showBarDialog(){
        if(sectionDialog==null){
            AlertDialog.Builder builder=new AlertDialog.Builder(AddSectionActivity.this);
            LayoutInflater inflater =getLayoutInflater();
            View view=inflater.inflate(R.layout.add_section_dialog,null);
            final EditText barname_bar=(EditText)view.findViewById(R.id.barname_section);
            TextView back_bar=(TextView)view.findViewById(R.id.back_section);
            TextView done_bar=(TextView)view.findViewById(R.id.done_section);
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
                        Toast.makeText(getApplicationContext(),"Section name : "+barname,Toast.LENGTH_SHORT).show();
                        AddSectionApi(UserProfileId,barname, Integer.parseInt(BarId));

                    }else{
                        Toast.makeText(getApplicationContext(),"Done clicked!",Toast.LENGTH_SHORT).show();

                    }
                }
            });
            builder.setView(view);
            sectionDialog=builder.create();
            sectionDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            sectionDialog.setCancelable(false);
            sectionDialog.setCanceledOnTouchOutside(false);
        }
        sectionDialog.show();

    }private void dismissBarDialog(){
        if(sectionDialog!=null && sectionDialog.isShowing()){
            sectionDialog.dismiss();
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
