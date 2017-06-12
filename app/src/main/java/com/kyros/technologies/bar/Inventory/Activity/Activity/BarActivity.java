package com.kyros.technologies.bar.Inventory.Activity.Activity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
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
import com.kyros.technologies.bar.Inventory.Activity.Adapters.BarAdapter;
import com.kyros.technologies.bar.OnStartDragListener;
import com.kyros.technologies.bar.R;
import com.kyros.technologies.bar.ServiceHandler.ServiceHandler;
import com.kyros.technologies.bar.SharedPreferences.PreferenceManager;
import com.kyros.technologies.bar.utils.EndURL;
import com.kyros.technologies.bar.utils.MyBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class BarActivity extends AppCompatActivity implements OnStartDragListener {
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

    private ItemTouchHelper mItemTouchHelper;
//        public BarActivity(){
//
//        }

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
        bar_recycler.setHasFixedSize(true);
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
        //GetBarList();
        adapter.notifyDataSetChanged();
//        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
//            @Override
//            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//                int fromPosition =viewHolder.getAdapterPosition();
//                int toPosition =target.getAdapterPosition();
//
//                if(fromPosition<toPosition){
//                    for(int i=fromPosition; i<toPosition;i++){
//                        Collections.swap(myBarArrayList,i,i+1);
//                    }
//                }else{
//                    for(int i=fromPosition;i> toPosition;i++){
//                        Collections.swap(myBarArrayList,i,i-1);
//                    }
//                }
//                adapter.notifyItemMoved(fromPosition,toPosition);
//
//                return false;
//            }
//
//            @Override
//            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
//                final int position = viewHolder.getAdapterPosition();
//                switch (direction){
//                    case ItemTouchHelper.RIGHT:
//                        Toast.makeText(getApplicationContext(),"right position :"+position,Toast.LENGTH_SHORT).show();
//                        break;
//                    case ItemTouchHelper.ACTION_STATE_DRAG:
//                        Toast.makeText(getApplicationContext(),"drag position :"+position,Toast.LENGTH_SHORT).show();
//
//                        break;
//                    case ItemTouchHelper.ACTION_STATE_IDLE:
//                        Toast.makeText(getApplicationContext(),"idle position :"+position,Toast.LENGTH_SHORT).show();
//
//                        break;
//                    case ItemTouchHelper.ACTION_STATE_SWIPE:
//                        Toast.makeText(getApplicationContext(),"swipe position :"+position,Toast.LENGTH_SHORT).show();
//
//                        break;
//                    case ItemTouchHelper.LEFT:
//                        Toast.makeText(getApplicationContext(),"left position :"+position,Toast.LENGTH_SHORT).show();
//
//                        break;
//
//                }
////                if (direction == ItemTouchHelper.LEFT) {
////
////                    AlertDialog.Builder builder = new AlertDialog.Builder(BarActivity.this);
////                    builder.setMessage("Are you sure to delete?");
////
////                    builder.setPositiveButton("REMOVE", new DialogInterface.OnClickListener() {
////                        @Override
////                        public void onClick(DialogInterface dialog, int which) {
////                            myBarArrayList.remove(position);
////                            adapter.notifyItemRemoved(position);
////
////                            return;
////                        }
////                    }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
////                        @Override
////                        public void onClick(DialogInterface dialog, int which) {
////                            adapter.notifyItemRemoved(position + 1);
////                            adapter.notifyItemRangeChanged(position, adapter.getItemCount());
////                            return;
////                        }
////                    }).show();
////                }else if(direction==ItemTouchHelper.RIGHT){
////                    String barname=myBarArrayList.get(position).getBarname();
////                    int  barid=myBarArrayList.get(position).getid();
////                    Toast.makeText(getApplicationContext(),"Barname and bar id : "+barname+" / "+barid,Toast.LENGTH_SHORT).show();
////                }else if(direction==ItemTouchHelper.ACTION_STATE_DRAG){
////                    Toast.makeText(getApplicationContext(),"Dragging : ",Toast.LENGTH_SHORT).show();
////
////                }
//            }
//        };
//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
//        itemTouchHelper.attachToRecyclerView(bar_recycler);


     /*   ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(bar_recycler);*/
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
                dismissBarDialog();
                myBarArrayList.clear();
                try {

                    JSONObject obj=new JSONObject(response.toString());
                    String message=obj.getString("message");
                    boolean success=obj.getBoolean("issuccess");
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
                                number=first.getString("createdon");
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
                    boolean success=obj.getBoolean("issuccess");
                    if (success){
                        myBarArrayList.clear();

                        myBarArrayList.clear();

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
                                number=first.getString("createdon");
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

    @Override
    protected void onResume() {
        super.onResume();
        GetBarList();
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
}
