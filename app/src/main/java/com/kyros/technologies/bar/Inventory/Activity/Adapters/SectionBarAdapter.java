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
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kyros.technologies.bar.Inventory.Activity.Activity.LiquorSlider;
import com.kyros.technologies.bar.Inventory.Activity.interfacesmodel.ItemTouchHelperViewHolder;
import com.kyros.technologies.bar.Inventory.Activity.interfacesmodel.OnBottleListChangedListener;
import com.kyros.technologies.bar.Inventory.Activity.interfacesmodel.OnStartDragListener;
import com.kyros.technologies.bar.R;
import com.kyros.technologies.bar.ServiceHandler.ServiceHandler;
import com.kyros.technologies.bar.utils.EndURL;
import com.kyros.technologies.bar.utils.UtilSectionBar;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Rohin on 17-05-2017.
 */

public class SectionBarAdapter extends RecyclerView.Adapter<SectionBarAdapter.MyViewHolderEleven>implements ItemTouchHelperAdapter{
    private Context mContext;
    //    String[]Alchohol=new String[]{"Anchor Porter","Aristocrat Rum"};
//    String[]AlchoholType=new String[]{"Beer","Rum"};
//    String[]Ml=new String[]{"750 Ml","900 Ml"};
    public int[]LiquorImages=new int[]{R.drawable.beer,R.drawable.rum};
    private ArrayList<UtilSectionBar> utilSectionBarArrayList;
    private OnStartDragListener mDragStartListener;
    private OnBottleListChangedListener mListChangedListener;


    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(utilSectionBarArrayList, fromPosition, toPosition);
        mListChangedListener.onBottleListChanged(utilSectionBarArrayList);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(final int position) {
        final UtilSectionBar section=utilSectionBarArrayList.get(position);
        final int id=section.getSectionid();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setMessage("Are you sure wanted to delete?");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        DeleteBarbyBarID(String.valueOf(section.getBottleId()),position);
                        Log.d("BarID : ",section.getBottleId()+", Position : "+position);

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
        utilSectionBarArrayList.remove(position);
        notifyItemRemoved(position);
    }


    public class MyViewHolderEleven extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
        public LinearLayout slider_edit;
        public ImageView liquor_image,right_to_drag;
        public TextView alchohol_name,bottle_ml,liquor_type;

        public MyViewHolderEleven(View itemView) {
            super(itemView);
            slider_edit=(LinearLayout)itemView.findViewById(R.id.slider_edit);
            liquor_image=(ImageView) itemView.findViewById(R.id.liquor_image);
            right_to_drag=(ImageView) itemView.findViewById(R.id.right_to_drag);
            alchohol_name=(TextView)itemView.findViewById(R.id.alchohol_name);
            bottle_ml=(TextView)itemView.findViewById(R.id.bottle_ml);
            liquor_type=(TextView)itemView.findViewById(R.id.liquor_type);
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

    public SectionBarAdapter(Context mContext,ArrayList<UtilSectionBar>utilSectionBarArrayList,OnStartDragListener dragLlistener,
                             OnBottleListChangedListener listChangedListener){
        this.mContext=mContext;
        this.utilSectionBarArrayList=utilSectionBarArrayList;
        this.mListChangedListener=listChangedListener;
        this.mDragStartListener=dragLlistener;

    }
    @Override
    public SectionBarAdapter.MyViewHolderEleven onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_section_bar,parent,false);

        return new SectionBarAdapter.MyViewHolderEleven(view);
    }

    @Override
    public void onBindViewHolder(final SectionBarAdapter.MyViewHolderEleven holder, final int position) {

        UtilSectionBar utilSectionBar=utilSectionBarArrayList.get(position);
        final int id=utilSectionBar.getId();
        final int barid=utilSectionBar.getBarid();
        final int sectionid=utilSectionBar.getSectionid();
        String liquorname=utilSectionBar.getLiquorname();
        String liquorcapacity=utilSectionBar.getLiquorcapacity();
        String shots=utilSectionBar.getShots();
        String category=utilSectionBar.getCategory();
        String subcategory=utilSectionBar.getSubcategory();
        String parlevel=utilSectionBar.getParlevel();
        String disname=utilSectionBar.getDistributorname();
        String price=utilSectionBar.getPriceunit();
        String binnumber=utilSectionBar.getBinnumber();
        String productcode=utilSectionBar.getProductcode();
        String pictureurl=utilSectionBar.getPictureurl();
        final double minvalue=utilSectionBar.getMinvalue();
        final double maxvalue=utilSectionBar.getMaxvalue();
        String totalbottles=utilSectionBar.getTotalbottles();
        final String type=utilSectionBar.getType();
        String fullweight=utilSectionBar.getFullweight();
        String emptyweight=utilSectionBar.getEmptyweight();

        if (liquorname==null){
            liquorname="";
        }

        if (liquorcapacity==null){
            liquorcapacity="";
        }

        if (category==null){
            category="";
        }
        if (shots==null){
            shots="";
        }
        if (subcategory==null){
            subcategory="";
        }
        if (parlevel==null){
            parlevel="";
        }
        if (disname==null){
            disname="";
        }
        if (price==null){
            price="";
        }
        if (binnumber==null){
            binnumber="";
        }
        if (productcode==null){
            productcode="";
        }
        if (pictureurl==null){
            pictureurl="";
        }
        if (totalbottles==null){
            totalbottles="";
        }
        if (fullweight==null){
            fullweight="";
        }
        if (emptyweight==null){
            emptyweight="";
        }
        holder.alchohol_name.setText(liquorname);
        holder.bottle_ml.setText(String.valueOf(liquorcapacity));
        holder.liquor_type.setText(category);
        //   holder.bottle_pic.setBackgroundResource(Bottleimages[position]);

        try{
            Picasso.with(mContext)
                    .load(pictureurl)
                    .resize(65, 65)
                    .into(holder.liquor_image);
        }catch (Exception e){
            e.printStackTrace();
            try {
                Picasso.with(mContext)
                        .load( LiquorImages[position])
                        .resize(65, 65)
                        .into(holder.liquor_image);
            }catch (Exception eq){

            }

        }

        final String finalLiquorname = liquorname;
        final String finalLiquorcapacity = liquorcapacity;
        final String finalPictureurl = pictureurl;
        final String finalShots = shots;
        final String finalSubcategory = subcategory;
        final String finalParlevel = parlevel;
        final String finalDisname = disname;
        final String finalPrice = price;
        final String finalBinnumber = binnumber;
        final String finalProductcode = productcode;
        final String finalTotalbottles = totalbottles;
        final String finalFullweight = fullweight;
        final String finalEmptyweight = emptyweight;
        final String finalCategory = category;
        holder.slider_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(mContext,LiquorSlider.class);
                i.putExtra("position", position);
                mContext.startActivity(i);
            }
        });

        holder.right_to_drag.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(holder);
                }
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return utilSectionBarArrayList.size();
    }

    public void setFilter(ArrayList<UtilSectionBar> utilsection1){
        utilSectionBarArrayList=new ArrayList<>();
        utilSectionBarArrayList.addAll(utilsection1);
        notifyDataSetChanged();
    }
    private void DeleteBarbyBarID(String barId, final int position) {
        String tag_json_obj = "json_obj_req";
        String url = EndURL.URL+"DeleteUserLiquor/"+barId;
        Log.d("DeleteUserLiquor: ", url);

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
                        utilSectionBarArrayList.remove(position);
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

}