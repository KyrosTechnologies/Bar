package com.kyros.technologies.bar.Common.activity.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kyros.technologies.bar.Common.activity.Activity.AddEmailActivity;
import com.kyros.technologies.bar.Common.activity.Activity.DistributorDetails;
import com.kyros.technologies.bar.R;
import com.kyros.technologies.bar.utils.Purchase;
import com.kyros.technologies.bar.utils.UserDetail;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Rohin on 07-06-2017.
 */

public class DistributorListAdapter extends RecyclerView.Adapter<DistributorListAdapter.MyViewHolderEleven>{

    private Context mContext;
    private ArrayList<Purchase> purchaseArrayList;

    public class MyViewHolderEleven extends RecyclerView.ViewHolder{
        public TextView distributor_name;
        public LinearLayout distributors;

        public MyViewHolderEleven(View itemView) {
            super(itemView);
            distributor_name=(TextView)itemView.findViewById(R.id.distributor_name);
            distributors=(LinearLayout)itemView.findViewById(R.id.distributors);
        }
    }
    public DistributorListAdapter(Context mContext,ArrayList<Purchase>purchaseArrayList){
        this.mContext=mContext;
        this.purchaseArrayList=purchaseArrayList;


    }
    @Override
    public DistributorListAdapter.MyViewHolderEleven onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_distributor_list,parent,false);

        return new DistributorListAdapter.MyViewHolderEleven(view);
    }

    @Override
    public void onBindViewHolder(DistributorListAdapter.MyViewHolderEleven holder, final int position) {

        Purchase purchase=purchaseArrayList.get(position);
        final int id=purchase.getId();
        final int userprofileid=purchase.getUserprofileid();
        String distributorname=purchase.getDistributorname();

        try {
            holder.distributor_name.setText(distributorname);

        }catch (Exception e){
            e.printStackTrace();
        }
        holder.distributors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(mContext,DistributorDetails.class);
                mContext.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return purchaseArrayList.size();
    }

}