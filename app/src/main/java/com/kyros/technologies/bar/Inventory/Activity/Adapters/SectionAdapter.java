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
import com.kyros.technologies.bar.Inventory.Activity.Activity.SectionBottlesActivity;
import com.kyros.technologies.bar.Inventory.Activity.interfacesmodel.ItemTouchHelperViewHolder;
import com.kyros.technologies.bar.Inventory.Activity.interfacesmodel.OnSectionListChangedListener;
import com.kyros.technologies.bar.Inventory.Activity.interfacesmodel.OnStartDragListener;
import com.kyros.technologies.bar.R;
import com.kyros.technologies.bar.ServiceHandler.ServiceHandler;
import com.kyros.technologies.bar.utils.EndURL;
import com.kyros.technologies.bar.utils.MySection;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Rohin on 17-05-2017.
 */

public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.MyViewHolderEleven>implements ItemTouchHelperAdapter{
    private Context mContext;
//    String[]SectionName=new String[]{"Front Section","Side Section","Main Section"};
//    String[]SectionUpdates=new String[]{"Updated 3 days ago","Updated 2 days ago","Updated 4 days ago"};
    private ArrayList<MySection>sectionArrayList;
    private OnStartDragListener mDragStartListener;
    private OnSectionListChangedListener mListChangedListener;

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(sectionArrayList, fromPosition, toPosition);
        mListChangedListener.onSectionListChanged(sectionArrayList);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(final int position) {
        final MySection section=sectionArrayList.get(position);
        final int id=section.getSectionid();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setMessage("Are you sure wanted to delete?");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        DeleteBarbyBarID(String.valueOf(section.getSectionid()),position);
                        Log.d("BarID : ",section.getSectionid()+", Position : "+position);

                        Toast.makeText(mContext.getApplicationContext(),"Long clicked  yes @!"+position,Toast.LENGTH_SHORT).show();

                    }
                });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                notifyDataSetChanged();
                Toast.makeText(mContext.getApplicationContext(),"Long clicked no @!"+position,Toast.LENGTH_SHORT).show();

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    @Override
    public void swipeToDelete(final int position) {

        sectionArrayList.remove(position);
        notifyItemRemoved(position);
    }


    public class MyViewHolderEleven extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
        public LinearLayout add_section;
        public TextView section_add,section_updates;
        public ImageView right_arrow_adapter_section;
        public EditText edit_text_section_name;
        public ViewSwitcher viewSwitcher_section;

        public MyViewHolderEleven(View itemView) {
            super(itemView);
            add_section=(LinearLayout) itemView.findViewById(R.id.add_section);
            section_add=(TextView)itemView.findViewById(R.id.section_add);
            section_updates=(TextView)itemView.findViewById(R.id.section_updates);
            viewSwitcher_section=(ViewSwitcher)itemView.findViewById(R.id.viewSwitcher_section);
            edit_text_section_name=(EditText)itemView.findViewById(R.id.edit_text_section_name);
            right_arrow_adapter_section=(ImageView)itemView.findViewById(R.id.right_arrow_adapter_section);

        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);

        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);

        }
    }
    public SectionAdapter(Context mContext,ArrayList<MySection> sectionArrayList,OnStartDragListener dragLlistener,
                          OnSectionListChangedListener listChangedListener){
        this.mContext=mContext;
        this.sectionArrayList=sectionArrayList;
        this.mListChangedListener=listChangedListener;
        this.mDragStartListener=dragLlistener;

    }
    @Override
    public SectionAdapter.MyViewHolderEleven onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_section,parent,false);

        return new SectionAdapter.MyViewHolderEleven(view);
    }

    @Override
    public void onBindViewHolder(final SectionAdapter.MyViewHolderEleven holder, final int position) {

        MySection section=sectionArrayList.get(position);
        final int id=section.getSectionid();
        String sectionname=section.getSectionname();
        String sectioncreated=section.getSectioncreated();
        int barid=section.getBarid();

        if (sectionname==null){
            sectionname="";

        }

        if (sectioncreated==null){

            sectioncreated="";

        }

        holder.section_add.setText(sectionname);
        holder.section_updates.setText(sectioncreated);

        //   holder.bottle_pic.setBackgroundResource(Bottleimages[position]);

        holder.add_section.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.kyros.technologies.bar.SharedPreferences.PreferenceManager store= com.kyros.technologies.bar.SharedPreferences.PreferenceManager.getInstance(mContext);
                store.putSectionId(String.valueOf(id));
                Intent i=new Intent(mContext,SectionBottlesActivity.class);
                mContext.startActivity(i);
            }
        });

        holder.right_arrow_adapter_section.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(holder);
                }
                return false;
            }
        });
        final String finalSectionname = sectionname;
        holder.add_section.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                holder.edit_text_section_name.setText(finalSectionname);
                holder.viewSwitcher_section.showNext();
                holder.edit_text_section_name.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                        if(actionId== EditorInfo.IME_ACTION_DONE){
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                            alertDialogBuilder.setMessage("Update Section Name?");
                            final MySection bar=sectionArrayList.get(position);

                            alertDialogBuilder.setPositiveButton("yes",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {
                                            String value=  holder.edit_text_section_name.getText().toString();
                                            if(value!=null && !value.isEmpty()){

                                                holder.viewSwitcher_section.showPrevious();
                                                holder.section_add.setText(value);
                                                notifyDataSetChanged();
                                                UpdateName(String.valueOf(bar.getSectionid()),value);
                                                Log.d("BarID : ",bar.getSectionid()+", Position : "+position);
                                                Toast.makeText(mContext.getApplicationContext(),"BarId is : "+bar.getSectionid(),Toast.LENGTH_SHORT).show();
                                            }else{
                                                Toast.makeText(mContext.getApplicationContext(),"Please enter bar name !"+bar.getSectionid(),Toast.LENGTH_SHORT).show();

                                            }



                                        }
                                    });

                            alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(mContext.getApplicationContext(),"BarId is : "+bar.getSectionid(),Toast.LENGTH_SHORT).show();
                                    notifyDataSetChanged();

                                }
                            });

                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        }
                        return false;
                    }
                });
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return sectionArrayList.size();
    }
    private void DeleteBarbyBarID(String barId, final int position) {
        String tag_json_obj = "json_obj_req";
        String url = EndURL.URL+"DeleteSection/"+barId;
        Log.d("DeleteSection: ", url);

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
                        sectionArrayList.remove(position);
                        notifyDataSetChanged();
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
        String url = EndURL.URL+"updateSectionBottles";
        Log.d("updateSectionBottles: ", url);
        JSONObject inputjso=new JSONObject();
        try{
            inputjso.put("SectionId",BarId);
            inputjso.put("SectionName",BarName);
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
