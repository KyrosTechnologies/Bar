package com.kyros.technologies.bar.Inventory.Activity.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kyros.technologies.bar.Inventory.Activity.Activity.BottleDescriptionActivity;
import com.kyros.technologies.bar.Inventory.Activity.List.LiquorListClass;
import com.kyros.technologies.bar.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by Rohin on 17-05-2017.
 */

/**
 * Created by Rohin on 17-05-2017.
 */

public class LiquorApiAdapter extends RecyclerView.Adapter<LiquorApiAdapter.MyViewHolderEleven>{
    private Context mContext;
    private ArrayList<LiquorListClass>list;
    private String sharedbitmap=null;

    public class MyViewHolderEleven extends RecyclerView.ViewHolder{
        public LinearLayout add_bottles;
        public ImageView liquor_bottle;
        public TextView bottle_name,quantity,alchohol_type;

        public MyViewHolderEleven(View itemView) {
            super(itemView);
            add_bottles=(LinearLayout)itemView.findViewById(R.id.add_bottles);
            liquor_bottle=(ImageView) itemView.findViewById(R.id.liquor_bottle);
            bottle_name=(TextView)itemView.findViewById(R.id.bottle_name);
            quantity=(TextView)itemView.findViewById(R.id.quantity);
            alchohol_type=(TextView)itemView.findViewById(R.id.add_bottle_type);

        }
    }
    public LiquorApiAdapter(Context mContext,ArrayList<LiquorListClass>liquorListClasses){
        this.mContext=mContext;
        this.list=liquorListClasses;

    }
    @Override
    public LiquorApiAdapter.MyViewHolderEleven onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_add_bottle,parent,false);

        return new LiquorApiAdapter.MyViewHolderEleven(view);
    }

    @Override
    public void onBindViewHolder(final LiquorApiAdapter.MyViewHolderEleven holder, final int position) {
        final LiquorListClass listClass=list.get(position);
        String name=listClass.getName();
        String subtype=listClass.getAlcohol_subtype();
        final double minheight=listClass.getMin_height();
        final double maxheight=listClass.getMax_height();
        if(name==null){
            name="Dummy";
        }
        final int  quanti=listClass.getCapacity_mL();

        String alchotype=listClass.getAlcohol_type();
        if(alchotype==null){
            alchotype="Dummy";
        }
        final String smallpic=listClass.getSmall_picture_url();


        try {

            holder.bottle_name.setText(name);
            holder.quantity.setText(String.valueOf(quanti)+" ML");
            holder.alchohol_type.setText(alchotype);

        }catch (Exception e){
            e.printStackTrace();
        }


        //   holder.bottle_pic.setBackgroundResource(Bottleimages[position]);
//        try{
//            Picasso.with(mContext)
//                    .load(smallpic)
//                    .resize(65, 65)
//                    .into(holder.liquor_bottle);
//        }catch (Exception e){
//
//        }

        try {
            Picasso.with(mContext).load(smallpic).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    holder.liquor_bottle.setImageBitmap(bitmap);
                    sharedbitmap = Base64.encodeToString(getBytesFromBitmap(bitmap), Base64.DEFAULT);

                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

        final String finalName = name;
        holder.add_bottles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(mContext,BottleDescriptionActivity.class);
                i.putExtra("liquorname", finalName);
                i.putExtra("liquorcapacity",String.valueOf(quanti)+" ML");
                i.putExtra("category",listClass.getAlcohol_type());
                i.putExtra("subcategory",listClass.getAlcohol_subtype());
                i.putExtra("image",smallpic);
                i.putExtra("minvalue",String.valueOf(minheight));
                i.putExtra("whichtype","newone");
                i.putExtra("maxvalue",String.valueOf(maxheight));
                mContext.startActivity(i);
            }
        });


    }

    private byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
        return stream.toByteArray();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setFilter(ArrayList<LiquorListClass> list1){
        list=new ArrayList<>();
        list.addAll(list1);
        notifyDataSetChanged();
    }


}