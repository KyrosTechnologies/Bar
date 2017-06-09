package com.kyros.technologies.bar.Common.activity.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kyros.technologies.bar.Common.activity.Activity.AddEmailActivity;
import com.kyros.technologies.bar.R;
import com.kyros.technologies.bar.utils.EmailManagement;
import com.kyros.technologies.bar.utils.Purchase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Rohin on 06-06-2017.
 */

public class ValueOnHandAdapter extends RecyclerView.Adapter<ValueOnHandAdapter.MyViewHolderEleven>{

    private Context mContext;
    private ArrayList<Purchase> purchaseArrayList;

    public class MyViewHolderEleven extends RecyclerView.ViewHolder{
        public TextView value_name,value_type,value_bottles,value_price;
        public ImageView value_image;

        public MyViewHolderEleven(View itemView) {
            super(itemView);
            value_name=(TextView)itemView.findViewById(R.id.value_name);
            value_type=(TextView)itemView.findViewById(R.id.value_type);
            value_bottles=(TextView)itemView.findViewById(R.id.value_bottles);
            value_price=(TextView)itemView.findViewById(R.id.value_price);
            value_image=(ImageView)itemView.findViewById(R.id.value_image);
        }
    }
    public ValueOnHandAdapter(Context mContext,ArrayList<Purchase>purchaseArrayList){
        this.mContext=mContext;
        this.purchaseArrayList=purchaseArrayList;


    }
    @Override
    public ValueOnHandAdapter.MyViewHolderEleven onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_value_on_hand,parent,false);

        return new ValueOnHandAdapter.MyViewHolderEleven(view);
    }

    @Override
    public void onBindViewHolder(ValueOnHandAdapter.MyViewHolderEleven holder, final int position) {

        Purchase purchase=purchaseArrayList.get(position);
        final int id=purchase.getId();
        final int userprofileid=purchase.getUserprofileid();
        String barid=purchase.getBarid();
        String sectionid=purchase.getSectionid();
        String liquorname=purchase.getLiquorname();
        String liquorcapacity=purchase.getLiquorcapacity();
        String shots=purchase.getShots();
        String category=purchase.getCategory();
        String subcategory=purchase.getSubcategory();
        String parlevel=purchase.getParlevel();
        String distributorname=purchase.getDistributorname();
        String price=purchase.getPriceunit();
        String binnumber=purchase.getBinnumber();
        String productcode=purchase.getProductcode();
        String createdon=purchase.getCreatedon();
        String modifiedon=purchase.getModifiedon();
        String minval=purchase.getMinvalue();
        String maxval=purchase.getMaxvalue();
        String picture=purchase.getSmall_picture_url();
        String totalbottles=purchase.getTotalbottles();
        String type=purchase.getType();
        String fullweight=purchase.getFullweight();
        String emptyweight=purchase.getEmptyweight();

        if (price!=null&&totalbottles!=null&&!price.equals("null")&&!totalbottles.equals("null")&&!price.isEmpty()&&!totalbottles.isEmpty()){
            float fprice=Float.parseFloat(price);
            float fbottles=Float.parseFloat(totalbottles);
            float fvalue=fprice*fbottles;
            String cvalue=String.valueOf(fvalue)+" Price";

                holder.value_price.setText(cvalue);

        }

        try{
            Picasso.with(mContext)
                    .load(picture)
                    .resize(65, 65)
                    .into(holder.value_image);
        }catch (Exception e){
            e.printStackTrace();

        }

        try {
            holder.value_name.setText(liquorname);
            holder.value_type.setText(category);
            String ftotalbottles=totalbottles+" Bottles";
            holder.value_bottles.setText(ftotalbottles);

        }catch (Exception e){
            e.printStackTrace();
        }

        //   holder.bottle_pic.setBackgroundResource(Bottleimages[position]);
    }

    @Override
    public int getItemCount() {
        return purchaseArrayList.size();
    }

}