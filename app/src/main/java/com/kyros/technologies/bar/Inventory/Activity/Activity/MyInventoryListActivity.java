package com.kyros.technologies.bar.Inventory.Activity.Activity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kyros.technologies.bar.Inventory.Activity.Adapters.LiquorApiAdapter;
import com.kyros.technologies.bar.Inventory.Activity.List.LiquorListClass;
import com.kyros.technologies.bar.R;
import com.kyros.technologies.bar.ServiceHandler.ServiceHandler;
import com.kyros.technologies.bar.utils.EndURL;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyInventoryListActivity extends AppCompatActivity {
    private RecyclerView recycler_database;
    private LiquorApiAdapter adapter;
    private ArrayList<LiquorListClass> liquorlist=new ArrayList<LiquorListClass>();
    private SearchView my_inventory_auto_complete;
    private String Category=null;
    private AlertDialog online,alertDialog;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_my_inventory_list);
        recycler_database=(RecyclerView)findViewById(R.id.recycler_database);
        adapter=new LiquorApiAdapter(MyInventoryListActivity.this,liquorlist);
        my_inventory_auto_complete=(SearchView) findViewById(R.id.my_inventory_auto_complete);
        RecyclerView.LayoutManager layoutManagersecond=new LinearLayoutManager(getApplicationContext());
        recycler_database.setLayoutManager(layoutManagersecond);
        recycler_database.setItemAnimator(new DefaultItemAnimator());
        recycler_database.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        //StateChangeWaggonapi();

        String s= getIntent().getStringExtra("search");
        if(s!=null){
            final ArrayList<LiquorListClass> filterlistdd=filter(liquorlist,s);
            adapter. setFilter(filterlistdd);
        }
        try{
            Bundle bundle=getIntent().getExtras();
            Category=bundle.getString("category");
        }catch (Exception e){
            e.printStackTrace();
        }

        my_inventory_auto_complete.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if(query!=null){
                    final ArrayList<LiquorListClass> filterlistdd=filter(liquorlist,query);
                    adapter. setFilter(filterlistdd);

                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if(newText!=null){
                    final ArrayList<LiquorListClass> filterlistdd=filter(liquorlist,newText);
                    adapter. setFilter(filterlistdd);
                }

                return false;
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
        online= new AlertDialog.Builder(MyInventoryListActivity.this).create();
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
            pDialog = new ProgressDialog(MyInventoryListActivity.this);
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
            alertDialog= new AlertDialog.Builder(MyInventoryListActivity.this).create();
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

    private ArrayList<LiquorListClass>filter(ArrayList<LiquorListClass>movies,String query){
        query=query.toLowerCase();
        final ArrayList<LiquorListClass>filterdlist=new ArrayList<>();
        for(LiquorListClass movie:movies){
            final String text=movie.getName().toLowerCase();
            if(text.contains(query)){
                filterdlist.add(movie);
            }
        }
        return filterdlist;
    }

    private void StateChangeWaggonapi() {
        String tag_json_obj = "json_obj_req";
        String url = EndURL.URL+ "getLiquorList";
        //  String url = "http://192.168.0.109:8080/Bar/rest/getLiquorList";
        //     showProgressDialog();
        Log.d("waggonurl", url);

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, (String) null, new Response.Listener<JSONObject>() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onResponse(JSONObject response) {
                Log.d("List Response",response.toString());
                showProgressDialog();

                try {

                    JSONObject obj=new JSONObject(response.toString());
                    String message=obj.getString("Message");
                    boolean success=obj.getBoolean("IsSuccess");
                    if (success){

                        liquorlist.clear();

                        JSONArray array=obj.getJSONArray("UserList");
                        for (int i=0;i<array.length();i++){
                            JSONObject first=array.getJSONObject(i);
                            String name=first.getString("name");
                            int quantity=first.getInt("capacity_mL");
                            String type=first.getString("alcohol_type");
                            String pic=first.getString("small_picture_url");
                            String subtype=first.getString("alcohol_subtype");
                            LiquorListClass liquorListClass=new LiquorListClass();
                            liquorListClass.setName(name);
                            liquorListClass.setCapacity_mL(quantity);
                            liquorListClass.setAlcohol_subtype(subtype);
                            liquorListClass.setAlcohol_type(type);
                            liquorListClass.setSmall_picture_url(pic);
                            liquorlist.add(liquorListClass);
                        }


                    }else {
                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

                adapter.notifyDataSetChanged();
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

    @Override
    protected void onResume() {
        super.onResume();
        if(Category!=null){
            CategoryList(Category);
        }else{
            StateChangeWaggonapi();
        }
    }

    private void CategoryList(String category) {
        String tag_json_obj = "json_obj_req";
        String url = EndURL.URL+ "getLiquorListCategory/"+category;
        Log.d("category", url);
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, (String) null, new Response.Listener<JSONObject>() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onResponse(JSONObject response) {
                Log.d("List Response",response.toString());
                showProgressDialog();
                liquorlist.clear();
                try {

                    JSONObject obj=new JSONObject(response.toString());
                    String message=obj.getString("Message");
                    boolean success=obj.getBoolean("IsSuccess");
                    if (success){

                        liquorlist.clear();

                        JSONArray array=obj.getJSONArray("UserList");
                        for (int i=0;i<array.length();i++){
                            JSONObject first=array.getJSONObject(i);
                            String name=first.getString("name");
                            int quantity=first.getInt("capacity_mL");
                            String type=first.getString("alcohol_type");
                            String pic=first.getString("picture_url");
//                            String pic=first.getString("small_picture_url");
                            String subtype=first.getString("alcohol_subtype");
                            double max_height=first.getDouble("max_height");
                            double min_height=first.getDouble("min_height");
                            LiquorListClass liquorListClass=new LiquorListClass();
                            liquorListClass.setName(name);
                            liquorListClass.setCapacity_mL(quantity);
                            liquorListClass.setAlcohol_subtype(subtype);
                            liquorListClass.setAlcohol_type(type);
                            liquorListClass.setSmall_picture_url(pic);
                            liquorListClass.setMin_height(min_height);
                            liquorListClass.setMax_height(max_height);
                            liquorlist.add(liquorListClass);
                        }


                    }else {
                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

                adapter.notifyDataSetChanged();

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

    @Override
    protected void onStop() {
        super.onStop();
        dismissProgressDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissonlineDialog();
        dismissProgressDialog();
        dismissErrorDialog();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){


            case android.R.id.home:
                MyInventoryListActivity.this.finish();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }
}
