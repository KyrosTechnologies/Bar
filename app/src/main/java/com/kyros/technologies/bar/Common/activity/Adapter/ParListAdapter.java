package com.kyros.technologies.bar.Common.activity.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kyros.technologies.bar.R;
import com.kyros.technologies.bar.utils.Purchase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Rohin on 06-06-2017.
 */

public class ParListAdapter extends RecyclerView.Adapter<ParListAdapter.MyViewHolderEleven>{

    private Context mContext;
    private ArrayList<Purchase> purchaseArrayList;

    public class MyViewHolderEleven extends RecyclerView.ViewHolder{
        public TextView par_name,par_type,par_level;
        public ImageView par_image;

        public MyViewHolderEleven(View itemView) {
            super(itemView);
            par_name=(TextView)itemView.findViewById(R.id.par_name);
            par_type=(TextView)itemView.findViewById(R.id.par_type);
            par_level=(TextView)itemView.findViewById(R.id.par_level);
            par_image=(ImageView)itemView.findViewById(R.id.par_image);
        }
    }
    public ParListAdapter(Context mContext,ArrayList<Purchase>purchaseArrayList){
        this.mContext=mContext;
        this.purchaseArrayList=purchaseArrayList;


    }
    @Override
    public ParListAdapter.MyViewHolderEleven onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_par_list,parent,false);

        return new ParListAdapter.MyViewHolderEleven(view);
    }

    @Override
    public void onBindViewHolder(ParListAdapter.MyViewHolderEleven holder, final int position) {

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

        try{
            Picasso.with(mContext)
                    .load(picture)
                    .resize(65, 65)
                    .into(holder.par_image);
        }catch (Exception e){
            e.printStackTrace();

        }

        try {
            holder.par_name.setText(liquorname);
            holder.par_type.setText(category);
            String ftotalbottles=totalbottles+" Bottles";
            holder.par_level.setText(ftotalbottles);

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