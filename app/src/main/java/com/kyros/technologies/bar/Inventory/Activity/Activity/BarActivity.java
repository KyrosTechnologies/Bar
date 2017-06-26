package com.kyros.technologies.bar.Inventory.Activity.Activity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.kyros.technologies.bar.Inventory.Activity.Adapters.BarAdapter;
import com.kyros.technologies.bar.Inventory.Activity.Adapters.SimpleItemTouchHelperCallback;
import com.kyros.technologies.bar.Inventory.Activity.interfacesmodel.OnBarListChangedListner;
import com.kyros.technologies.bar.Inventory.Activity.interfacesmodel.OnStartDragListener;
import com.kyros.technologies.bar.R;
import com.kyros.technologies.bar.ServiceHandler.ServiceHandler;
import com.kyros.technologies.bar.SharedPreferences.PreferenceManager;
import com.kyros.technologies.bar.utils.EndURL;
import com.kyros.technologies.bar.utils.MyBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BarActivity extends AppCompatActivity implements OnBarListChangedListner,OnStartDragListener {
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
    private String UserRole=null;
    private String ParentUserProfileId=null;
    private String BarListInString=null;
    private ItemTouchHelper mItemTouchHelper;
    private SwipeRefreshLayout bar_swipe;


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
        BarListInString=store.getBar();

        UserRole=store.getUserRole();
        ParentUserProfileId=store.getParentUserProfileId();
        BarName=store.getBarName();
        BarCreated=store.getBarDateCreated();
        bar_swipe=(SwipeRefreshLayout)findViewById(R.id.bar_swipe);
        add_bar_acti=(LinearLayout)findViewById(R.id.add_bar_acti);
        add_bar_acti.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBarDialog();
            }
        });
        if(BarListInString!=null){
            try{
                myBarArrayList.clear();
                Gson gsons=new Gson();
                Type type1=new TypeToken<List<MyBar>>(){}.getType();
                myBarArrayList=gsons.fromJson(BarListInString,type1);

            }catch (Exception e){
                e.printStackTrace();
            }
            if(myBarArrayList!=null && myBarArrayList.size()!=0){

                bar_recycler=(RecyclerView)findViewById(R.id.bar_recycler);
                adapter=new BarAdapter(BarActivity.this,myBarArrayList,this,this);
                ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
                mItemTouchHelper = new ItemTouchHelper(callback);
                mItemTouchHelper.attachToRecyclerView(bar_recycler);

                RecyclerView.LayoutManager layoutManagersecond=new LinearLayoutManager(getApplicationContext());
                bar_recycler.setLayoutManager(layoutManagersecond);
                bar_recycler.setItemAnimator(new DefaultItemAnimator());
                bar_recycler.setHasFixedSize(true);
                bar_recycler.setAdapter(adapter);

                adapter.notifyDataSetChanged();

            }else{
                Toast.makeText(getApplicationContext(), "List is empty !", Toast.LENGTH_SHORT).show();
            }

        }else{

            bar_recycler=(RecyclerView)findViewById(R.id.bar_recycler);
            adapter=new BarAdapter(BarActivity.this,myBarArrayList,this,this);
            ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
            mItemTouchHelper = new ItemTouchHelper(callback);
            mItemTouchHelper.attachToRecyclerView(bar_recycler);

            RecyclerView.LayoutManager layoutManagersecond=new LinearLayoutManager(getApplicationContext());
            bar_recycler.setLayoutManager(layoutManagersecond);
            bar_recycler.setItemAnimator(new DefaultItemAnimator());
            bar_recycler.setHasFixedSize(true);
            bar_recycler.setAdapter(adapter);

            adapter.notifyDataSetChanged();
        }
        bar_swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                bar_swipe.setRefreshing(true);
                ParentUserProfileId=store.getParentUserProfileId();
                UserRole=store.getUserRole();
                if(UserRole.equals("basic")){
                    if(ParentUserProfileId!=null){
                        GetBarList(ParentUserProfileId);

                    }else{
                        Toast.makeText(getApplicationContext(),"Parent User ProfileId must not be null!",Toast.LENGTH_SHORT).show();

                    }

                }else if(UserRole.equals("admin")){
                    GetBarList(UserProfileId);

                }else {
                    Toast.makeText(getApplicationContext(),"UserRole must specified",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){


            case android.R.id.home:
                BarActivity.this.finish();
                return true;
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
            inputLogin.put("UserProfileId",userprofile);
            inputLogin.put("BarName",barname);


        }catch (Exception e){
            e.printStackTrace();
        }
        Log.d("inputJsonuser",inputLogin.toString());
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, url, inputLogin, new Response.Listener<JSONObject>() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onResponse(JSONObject response) {
                Log.d("List Response",response.toString());
                dismissBarDialog();
                myBarArrayList.clear();
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
                            String fname=first.getString("BarName");
                            store.putBarName(String.valueOf(fname));
                            int lname=first.getInt("BarId");
                            //store.putBarId(String.valueOf(lname));
                            String number=null;
                            try {
                                number=first.getString("CreatedOn");
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



    private void GetBarList(String proid) {
        String tag_json_obj = "json_obj_req";
        String url = EndURL.URL+"getBarbyUserProfileId/"+proid;
        //  String url = "http://192.168.0.109:8080/Bar/rest/getLiquorList";
        Log.d("waggonurl", url);

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, (String)null, new Response.Listener<JSONObject>() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onResponse(JSONObject response) {
                Log.d("List Response",response.toString());
                bar_swipe.setRefreshing(false);

                try {

                    JSONObject obj=new JSONObject(response.toString());
                    String message=obj.getString("Message");
                    boolean success=obj.getBoolean("IsSuccess");
                    if (success){
                        myBarArrayList.clear();

                        myBarArrayList.clear();

                        JSONArray array=obj.getJSONArray("Model");
                        for (int i=0;i<array.length();i++){
                            JSONObject first=array.getJSONObject(i);
                            int userprofile=first.getInt("UserProfileId");
                            store.putUserProfileId(String.valueOf(userprofile));
                            String fname=first.getString("BarName");
                            store.putBarName(String.valueOf(fname));
                            int lname=first.getInt("BarId");
                            //store.putBarId(String.valueOf(lname));
                            String number=null;
                            try {
                                number=first.getString("CreatedOn");
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

                        dismissBarDialog();


                    }else {
                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                        store.putBar(null);

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

                adapter.notifyDataSetChanged();
                try{
                    Gson gson=new Gson();
                    String barlist=gson.toJson(myBarArrayList);
                    store.putBar(barlist);

                }catch (Exception e){
                    Log.d("exception_conve_gson",e.getMessage());
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                bar_swipe.setRefreshing(false);

                Toast.makeText(getApplicationContext(),"Not Working",Toast.LENGTH_SHORT).show();

            }
        }) {

        };
        ServiceHandler.getInstance().addToRequestQueue(objectRequest, tag_json_obj);

    }

    @Override
    protected void onResume() {
        super.onResume();
        ParentUserProfileId=store.getParentUserProfileId();
        UserRole=store.getUserRole();
        if(BarListInString==null){
            if(UserRole.equals("basic")){
                if(ParentUserProfileId!=null){
                    GetBarList(ParentUserProfileId);

                }else{
                    Toast.makeText(getApplicationContext(),"Parent User ProfileId must not be null!",Toast.LENGTH_SHORT).show();

                }

            }else if(UserRole.equals("admin")){
                GetBarList(UserProfileId);

            }else {
                Toast.makeText(getApplicationContext(),"UserRole must specified",Toast.LENGTH_SHORT).show();
            }
        }

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
                    if(UserRole.equals("admin")){
                        if(ParentUserProfileId!=null){
                            if(ParentUserProfileId.equals("null")){
                                AddBarApi(UserProfileId,barname);
                            }else{
                                AddBarApi(ParentUserProfileId,barname);

                            }
                        }else{
                            Toast.makeText(getApplicationContext(),"parent id could not be null!",Toast.LENGTH_SHORT).show();

                        }


                    }else if(UserRole.equals("basic")){
//                        if(ParentUserProfileId!=null){
//                            AddBarApi(ParentUserProfileId,barname);
//
//                        }else{
                            Toast.makeText(getApplicationContext(),"user is basic version could not be add!",Toast.LENGTH_SHORT).show();

                        //}

                    }else{
                        Toast.makeText(getApplicationContext(),"User role must be specified!",Toast.LENGTH_SHORT).show();
                    }
                }else{

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

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);

    }

    @Override
    public void onBarListChanged(ArrayList<MyBar> myBarArrayList) {
        try{
            Gson gson=new Gson();
            String barlist=gson.toJson(myBarArrayList);
            Log.d("Changed BarLIst",barlist);
            store.putBar(barlist);

        }catch (Exception e){
            Log.d("exception_conve_gson",e.getMessage());
        }
    }
}
