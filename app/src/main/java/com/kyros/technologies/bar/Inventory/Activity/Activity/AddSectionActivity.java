package com.kyros.technologies.bar.Inventory.Activity.Activity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kyros.technologies.bar.Inventory.Activity.Adapters.SectionAdapter;
import com.kyros.technologies.bar.Inventory.Activity.Adapters.SimpleItemTouchHelperSection;
import com.kyros.technologies.bar.Inventory.Activity.interfacesmodel.OnSectionListChangedListener;
import com.kyros.technologies.bar.Inventory.Activity.interfacesmodel.OnStartDragListener;
import com.kyros.technologies.bar.R;
import com.kyros.technologies.bar.ServiceHandler.ServiceHandler;
import com.kyros.technologies.bar.SharedPreferences.PreferenceManager;
import com.kyros.technologies.bar.utils.EndURL;
import com.kyros.technologies.bar.utils.MySection;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AddSectionActivity extends AppCompatActivity implements OnSectionListChangedListener,OnStartDragListener {
    private LinearLayout section_bar;
    private AlertDialog sectionDialog,online,alertDialog;
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
    private ProgressDialog pDialog;
    private String SectionListInString=null;
    private ItemTouchHelper mItemTouchHelper;
    private SwipeRefreshLayout section_swipe;

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
        BarId=store.getBarId();
        SectionListInString=store.getSection("Section"+BarId);
        section_swipe=(SwipeRefreshLayout)findViewById(R.id.section_swipe);
        section_bar=(LinearLayout)findViewById(R.id.section_bar);
        adapter=new SectionAdapter(AddSectionActivity.this,mySectionArrayList,this,this);
        section_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBarDialog();
            }
        });
        //GetSectionList();
//        add_section.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(AddSectionActivity.this,SectionBottlesActivity.class);
//                startActivity(intent);
//            }
//        });

        if(SectionListInString!=null){
            try{
                mySectionArrayList.clear();
                Gson gsons=new Gson();
                Type type1=new TypeToken<List<MySection>>(){}.getType();
                mySectionArrayList=gsons.fromJson(SectionListInString,type1);

            }catch (Exception e){
                e.printStackTrace();
            }
            if(mySectionArrayList!=null && mySectionArrayList.size()!=0){

                section_recycler=(RecyclerView)findViewById(R.id.section_recycler);
                adapter=new SectionAdapter(AddSectionActivity.this,mySectionArrayList,this,this);
                ItemTouchHelper.Callback callback = new SimpleItemTouchHelperSection(adapter);
                mItemTouchHelper = new ItemTouchHelper(callback);
                mItemTouchHelper.attachToRecyclerView(section_recycler);

                RecyclerView.LayoutManager layoutManagersecond=new LinearLayoutManager(getApplicationContext());
                section_recycler.setLayoutManager(layoutManagersecond);
                section_recycler.setItemAnimator(new DefaultItemAnimator());
                section_recycler.setHasFixedSize(true);
                section_recycler.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }else{
                Toast.makeText(getApplicationContext(), "List is empty !", Toast.LENGTH_SHORT).show();
            }

        }else{

            section_recycler=(RecyclerView)findViewById(R.id.section_recycler);
            adapter=new SectionAdapter(AddSectionActivity.this,mySectionArrayList,this,this);
            ItemTouchHelper.Callback callback = new SimpleItemTouchHelperSection(adapter);
            mItemTouchHelper = new ItemTouchHelper(callback);
            mItemTouchHelper.attachToRecyclerView(section_recycler);

            RecyclerView.LayoutManager layoutManagersecond=new LinearLayoutManager(getApplicationContext());
            section_recycler.setLayoutManager(layoutManagersecond);
            section_recycler.setItemAnimator(new DefaultItemAnimator());
            section_recycler.setHasFixedSize(true);
            section_recycler.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        section_swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                section_swipe.setRefreshing(true);
                GetSectionList();

            }
        });

    }

    public boolean checkOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            return true;

        }else {
            onlineDialog();

        }

        return false;
    }

    public void onlineDialog(){
        online= new AlertDialog.Builder(AddSectionActivity.this).create();
        online.setTitle("No Internet Connection");
        online.setMessage("We cannot detect any internet connectivity.Please check your internet connection and try again");
        //   alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
        online.setButton("Try Again",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                checkOnline();
            }
        });
        online.show();

    }
    private void dismissonlineDialog(){
        if(online!=null && online.isShowing()){
            online.dismiss();
        }
    }

    private void showProgressDialog() {
        if (pDialog == null) {
            pDialog = new ProgressDialog(AddSectionActivity.this);
            pDialog.setMessage("Loading. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
        }
        pDialog.show();
    }

    private void dismissProgressDialog() {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }
    private void showErrorDialog() {
        if (alertDialog == null) {
            alertDialog= new AlertDialog.Builder(AddSectionActivity.this).create();
            alertDialog.setTitle("Network/Connection Error");
            alertDialog.setMessage(getString(R.string.server_error_dialog));
            //   alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
            alertDialog.setButton("Ok",new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    alertDialog.dismiss();
                }
            });
        }
        alertDialog.show();
    }

    private void dismissErrorDialog() {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }

    private void AddSectionApi(final String userprofile, final String sectionname,final int BarId) {
        String tag_json_obj = "json_obj_req";
        String url = EndURL.URL+"InsertSection";
        //  String url = "http://192.168.0.109:8080/Bar/rest/getLiquorList";
        Log.d("waggonurl", url);
        JSONObject inputLogin=new JSONObject();
        try{
            inputLogin.put("UserProfileId",userprofile);
            inputLogin.put("SectionName",sectionname);
            inputLogin.put("BarId",BarId);


        }catch (Exception e){
            e.printStackTrace();
        }
        Log.d("inputJsonuser",inputLogin.toString());
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, url, inputLogin, new Response.Listener<JSONObject>() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onResponse(JSONObject response) {
                Log.d("List Response",response.toString());
                showProgressDialog();
                dismissBarDialog();
                mySectionArrayList.clear();
                try {

                    JSONObject obj=new JSONObject(response.toString());
                    String message=obj.getString("Message");
                    boolean success=obj.getBoolean("IsSuccess");
                    if (success){

                        JSONArray array=obj.getJSONArray("Model");
                        for (int i=0;i<array.length();i++){
                            JSONObject first=array.getJSONObject(i);
                            int userprofile=first.getInt("UserProfileId");
                            store.putUserProfileId(String.valueOf(userprofile));
                            String fname=first.getString("SectionName");
                            store.putSectionName(String.valueOf(fname));
                            int lname=first.getInt("BarId");
                            //store.putBarId(String.valueOf(lname));
                            int sectionid=first.getInt("SectionId");
                            //store.putSectionId(String .valueOf(sectionid));
                            String number=null;
                            try {
                                number=first.getString("CreatedOn");
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



                    }else {
                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
                try{
                    Gson gson=new Gson();
                    String sectionlist=gson.toJson(mySectionArrayList);
                    store.putSection("Section"+BarId,sectionlist);

                }catch (Exception e){
                    Log.d("exception_conve_gson",e.getMessage());
                }
                dismissProgressDialog();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                dismissProgressDialog();
                showErrorDialog();
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
                section_swipe.setRefreshing(false);
                showProgressDialog();

                try {

                    JSONObject obj=new JSONObject(response.toString());
                    String message=obj.getString("Message");
                    boolean success=obj.getBoolean("IsSuccess");
                    if (success){

                        mySectionArrayList.clear();

                        JSONArray array=obj.getJSONArray("Model");
                        for (int i=0;i<array.length();i++){
                            JSONObject first=array.getJSONObject(i);
                            int userprofile=first.getInt("UserProfileId");
                            store.putUserProfileId(String.valueOf(userprofile));
                            String fname=first.getString("SectionName");
                            store.putSectionName(String.valueOf(fname));
                            int sectionid=first.getInt("SectionId");
                            //store.putSectionId(String .valueOf(sectionid));
                            int lname=first.getInt("BarId");
                            //store.putBarId(String.valueOf(lname));
                            String number=null;
                            try {
                                number=first.getString("CreatedOn");
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

                        dismissBarDialog();


                    }else {
                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                            store.putSection("Section"+BarId,null);

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }


                if(adapter!=null){
                    adapter.notifyDataSetChanged();
                }
                dismissProgressDialog();
                try{
                    Gson gson=new Gson();
                    String sectionlist=gson.toJson(mySectionArrayList);
                    store.putSection("Section"+BarId,sectionlist);

                }catch (Exception e){
                    Log.d("exception_conve_gson",e.getMessage());
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                dismissProgressDialog();
                showErrorDialog();
                Toast.makeText(getApplicationContext(),"Not Working",Toast.LENGTH_SHORT).show();

//                texts.setText(error.toString());
            }
        }) {

        };
        ServiceHandler.getInstance().addToRequestQueue(objectRequest, tag_json_obj);

    }

    @Override
    protected void onResume() {
        super.onResume();
        SectionListInString=store.getSection("Section"+BarId);
        if(SectionListInString==null){
            GetSectionList();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                Intent i=new Intent(AddSectionActivity.this,BarActivity.class);
                startActivity(i);
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
                        AddSectionApi(UserProfileId,barname, Integer.parseInt(BarId));

                    }else{

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
        dismissProgressDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissBarDialog();
        dismissonlineDialog();
        dismissProgressDialog();
        dismissErrorDialog();
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewholder) {
        mItemTouchHelper.startDrag(viewholder);

    }

    @Override
    public void onSectionListChanged(ArrayList<MySection> mySectionArrayList) {
        try{
            Gson gson=new Gson();
            String sectionlist=gson.toJson(mySectionArrayList);
            Log.d("Changed SectionList",sectionlist);
            store.putSection("Section"+BarId,sectionlist);

        }catch (Exception e){
            Log.d("exception_conve_gson",e.getMessage());
        }
    }
}