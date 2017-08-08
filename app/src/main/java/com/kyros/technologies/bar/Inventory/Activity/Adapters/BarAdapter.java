package com.kyros.technologies.bar.Inventory.Activity.Adapters;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.kyros.technologies.bar.Inventory.Activity.Activity.AddSectionActivity;
import com.kyros.technologies.bar.Inventory.Activity.interfacesmodel.ItemTouchHelperViewHolder;
import com.kyros.technologies.bar.Inventory.Activity.interfacesmodel.OnBarListChangedListner;
import com.kyros.technologies.bar.Inventory.Activity.interfacesmodel.OnStartDragListener;
import com.kyros.technologies.bar.R;
import com.kyros.technologies.bar.ServiceHandler.ServiceHandler;
import com.kyros.technologies.bar.SharedPreferences.PreferenceManager;
import com.kyros.technologies.bar.utils.EndURL;
import com.kyros.technologies.bar.utils.MyBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Rohin on 17-05-2017.
 */

public class BarAdapter extends RecyclerView.Adapter<BarAdapter.MyViewHolderEleven> implements ItemTouchHelperAdapter{
    private Context mContext;
//    String[]BarName=new String[]{"Front Bar","Side Bar","Main Bar"};
//    String[]Updates=new String[]{"Updated 3 days ago","Updated 2 days ago","Updated 4 days ago"};
    private ArrayList<MyBar>barArrayList;
    private PreferenceManager store;
    private AlertDialog forget_dialog;
    private int Id=0;
    private String pro;
    private ArrayList<MyBar>myBarArrayList=new ArrayList<MyBar>();

    private OnStartDragListener mDragStartListener;
    private OnBarListChangedListner mListChangedListener;


    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(barArrayList, fromPosition, toPosition);
        mListChangedListener.onBarListChanged(barArrayList);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(final int position) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setMessage("Are you sure wanted to delete?");
        final MyBar bar=barArrayList.get(position);

        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        DeleteBarbyBarID(String.valueOf(bar.getid()),position,String.valueOf(pro));
                        Log.d("BarID : ",bar.getid()+", Position : "+position);
                        Toast.makeText(mContext.getApplicationContext(),"BarId is : "+bar.getid(),Toast.LENGTH_SHORT).show();


                    }
                });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(mContext.getApplicationContext(),"BarId is : "+bar.getid(),Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    @Override
    public void swipeToDelete(final int position) {

        barArrayList.remove(position);

        notifyItemRemoved(position);


    }


    public class MyViewHolderEleven extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
        public LinearLayout front_bar;
        public TextView first_bar,updates;
        public ImageView right_arrow_adapter_bar;
        public ViewSwitcher view_switcher_change;
        public EditText edit_barname;

        public MyViewHolderEleven(View itemView) {
            super(itemView);
            front_bar=(LinearLayout) itemView.findViewById(R.id.front_bar);
            first_bar=(TextView)itemView.findViewById(R.id.first_bar);
            updates=(TextView)itemView.findViewById(R.id.updates);
            right_arrow_adapter_bar=(ImageView)itemView.findViewById(R.id.right_arrow_adapter_bar);
            store= PreferenceManager.getInstance(mContext.getApplicationContext());
            edit_barname=(EditText)itemView.findViewById(R.id.edit_barname);
            view_switcher_change=(ViewSwitcher)itemView.findViewById(R.id.view_switcher_change);

        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);

        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);

        }
        public void remove(int position) {
            barArrayList.remove(position);
            notifyItemRemoved(position);
        }
    }
    public BarAdapter(Context mContext, ArrayList<MyBar>barArrayList,OnStartDragListener dragLlistener,
                      OnBarListChangedListner listChangedListener){
        this.mContext=mContext;
        this.barArrayList=barArrayList;
        this.mListChangedListener=listChangedListener;
        this.mDragStartListener=dragLlistener;

    }
//    public BarAdapter(Context mContext,OnStartDragListener dragStartListener){
//        this.mContext=mContext;
//        mDragStartListener = dragStartListener;
//
//    }
    @Override
    public BarAdapter.MyViewHolderEleven onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_bar,parent,false);

        return new BarAdapter.MyViewHolderEleven(view);
    }

    @Override
    public void onBindViewHolder(final BarAdapter.MyViewHolderEleven holder, final int position) {
        MyBar bar=barArrayList.get(position);
        final int id=bar.getid();
        Id=id;
        String name=bar.getBarname();
        String datecreated=bar.getDatecreated();
        store= PreferenceManager.getInstance(mContext.getApplicationContext());
        int pro1=bar.getUserprofileid();
        pro=String.valueOf(pro1);

        if (name==null){
            name="";

        }

        if (datecreated==null){

            datecreated="";

        }
        holder.first_bar.setText(name);
        holder.updates.setText("Updated on "+datecreated);

        //   holder.bottle_pic.setBackgroundResource(Bottleimages[position]);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date strDate = sdf.parse(valid_until);
//        if (new Date().after(strDate)) {
//            catalog_outdated = 1;
//        }
        holder.front_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.kyros.technologies.bar.SharedPreferences.PreferenceManager store= com.kyros.technologies.bar.SharedPreferences.PreferenceManager.getInstance(mContext);
                store.putBarId(String.valueOf(id));
                Intent i=new Intent(mContext,AddSectionActivity.class);
                mContext.startActivity(i);
            }
        });
        holder.right_arrow_adapter_bar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(holder);
                }
                return false;
            }
        });
        final String finalName = name;
        holder.front_bar.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                holder.edit_barname.setText(finalName);
                holder.view_switcher_change.showNext();
                holder.edit_barname.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if(actionId== EditorInfo.IME_ACTION_DONE){
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                            alertDialogBuilder.setMessage("Update Bar Name?");
                            final MyBar bar=barArrayList.get(position);

                            alertDialogBuilder.setPositiveButton("yes",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {
                                                String value=  holder.edit_barname.getText().toString();
                                            if(value!=null && !value.isEmpty()){

                                                holder.view_switcher_change.showPrevious();
                                                holder.first_bar.setText(value);
                                                notifyDataSetChanged();
                                                UpdateName(String.valueOf(bar.getid()),value);
                                                Log.d("BarID : ",bar.getid()+", Position : "+position);
                                                Toast.makeText(mContext.getApplicationContext(),"BarId is : "+bar.getid(),Toast.LENGTH_SHORT).show();
                                            }else{
                                                Toast.makeText(mContext.getApplicationContext(),"Please enter bar name !"+bar.getid(),Toast.LENGTH_SHORT).show();

                                            }



                                        }
                                    });

                            alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(mContext.getApplicationContext(),"BarId is : "+bar.getid(),Toast.LENGTH_SHORT).show();
                                    notifyDataSetChanged();

                                }
                            });

                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        }
                        return false;
                    }
                });
                //holder.first_bar.setVisibility(View.GONE);
               // holder.edit_barname.setVisibility(View.VISIBLE);
                return false;
            }
        });


    }

    @Override
    public int getItemCount() {
        return barArrayList.size();
    }
    private void DeleteBarbyBarID(String barId, final int position,String pro) {
        String tag_json_obj = "json_obj_req";

        String url = EndURL.URL+"DeleteBar/"+pro+"/"+barId;
        Log.d("deletebarurl: ", url);

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, (String)null, new Response.Listener<JSONObject>() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onResponse(JSONObject response) {
                Log.d("List Response",response.toString());


                try {

                    JSONObject obj=new JSONObject(response.toString());
                    String message=obj.getString("Message");
                    boolean success=obj.getBoolean("IsSuccess");
                    if (success){
                        barArrayList.remove(position);
                        notifyDataSetChanged();
                        myBarArrayList.clear();
                        JSONArray array=obj.getJSONArray("Model");
                        for (int i=0;i<array.length();i++){
                            JSONObject first=array.getJSONObject(i);
                            int userprofile=first.getInt("UserProfileId");
                            store.putUserProfileId(String.valueOf(userprofile));
                            String fname=first.getString("BarName");
                            store.putBarName(String.valueOf(fname));
                            int BarId=first.getInt("BarId");
                            //store.putBarId(String.valueOf(lname));
                            String number=null;
                            try {
                                number=first.getString("CreatedOn");
                                store.putBarDateCreated(String.valueOf(number));
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                            MyBar bars=new MyBar();
                            bars.setid(BarId);
                            bars.setBarname(fname);
                            bars.setUserprofileid(userprofile);
                            bars.setDatecreated(number);
                            myBarArrayList.add(bars);
                        }
                        try{
                            Gson gson=new Gson();
                            String barlist=gson.toJson(myBarArrayList);
                            store.putBar(barlist);

                        }catch (Exception e){
                            Log.d("exception_conve_gson",e.getMessage());
                        }
                        Toast.makeText(mContext.getApplicationContext(),"Successfully deleted!",Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(mContext.getApplicationContext(),message,Toast.LENGTH_SHORT).show();

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                    notifyDataSetChanged();
                Toast.makeText(mContext.getApplicationContext(),"Some error occured!",Toast.LENGTH_SHORT).show();

            }
        }) {

        };
        ServiceHandler.getInstance().addToRequestQueue(objectRequest, tag_json_obj);

    }

    private void UpdateName(String BarId,String BarName) {
        String tag_json_obj = "json_obj_req";
        String url = EndURL.URL+"updateBarName";
        Log.d("deletebarurl: ", url);
        JSONObject inputjso=new JSONObject();
        try{
            inputjso.put("BarId",BarId);
            inputjso.put("BarName",BarName);
            inputjso.put("UserProfileId",pro);
        }catch (Exception e){
            e.printStackTrace();
        }

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.PUT, url, inputjso, new Response.Listener<JSONObject>() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onResponse(JSONObject response) {
                Log.d("List Response",response.toString());


                try {

                    JSONObject obj=new JSONObject(response.toString());
                    String message=obj.getString("Message");
                    boolean success=obj.getBoolean("IsSuccess");
                    if (success){
                        notifyDataSetChanged();
                        myBarArrayList.clear();
                        JSONArray array=obj.getJSONArray("Model");
                        for (int i=0;i<array.length();i++){
                            JSONObject first=array.getJSONObject(i);
                            int userprofile=first.getInt("UserProfileId");
                            store.putUserProfileId(String.valueOf(userprofile));
                            String fname=first.getString("BarName");
                            store.putBarName(String.valueOf(fname));
                            int BarId=first.getInt("BarId");
                            //store.putBarId(String.valueOf(lname));
                            String number=null;
                            try {
                                number=first.getString("CreatedOn");
                                store.putBarDateCreated(String.valueOf(number));
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                            MyBar bars=new MyBar();
                            bars.setid(BarId);
                            bars.setBarname(fname);
                            bars.setUserprofileid(userprofile);
                            bars.setDatecreated(number);
                            myBarArrayList.add(bars);
                        }
                        try{
                            Gson gson=new Gson();
                            String barlist=gson.toJson(myBarArrayList);
                            store.putBar(barlist);

                        }catch (Exception e){
                            Log.d("exception_conve_gson",e.getMessage());
                        }
                        Toast.makeText(mContext.getApplicationContext(),"Successfully updated!",Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(mContext.getApplicationContext(),message,Toast.LENGTH_SHORT).show();

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                notifyDataSetChanged();
                Toast.makeText(mContext.getApplicationContext(),"Some error occured!",Toast.LENGTH_SHORT).show();

            }
        }) {

        };
        ServiceHandler.getInstance().addToRequestQueue(objectRequest, tag_json_obj);

    }

    
}
