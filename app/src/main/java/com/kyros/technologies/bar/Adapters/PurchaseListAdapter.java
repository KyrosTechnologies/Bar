package com.kyros.technologies.bar.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kyros.technologies.bar.Activity.BottleDescriptionActivity;
import com.kyros.technologies.bar.Activity.LiquorSlider;
import com.kyros.technologies.bar.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Rohin on 17-05-2017.
 */

public class PurchaseListAdapter extends RecyclerView.Adapter<PurchaseListAdapter.MyViewHolderEleven>{
    private Context mContext;
    String[]Purchasename=new String[]{"Anchor Porter","Aristocrat Rum"};
    String[]PurchaseType=new String[]{"Beer","Rum"};
    String[]PurchaseMl=new String[]{"750 Ml","500 Ml"};
    public int[]PurchaseImages=new int[]{R.drawable.beer,R.drawable.rum};


    public class MyViewHolderEleven extends RecyclerView.ViewHolder{
        public LinearLayout purchases;
        public ImageView purchase_image;
        public TextView purchase_name,purchase_quantity,purchase_type;

        public MyViewHolderEleven(View itemView) {
            super(itemView);
            purchases=(LinearLayout)itemView.findViewById(R.id.purchases);
            purchase_image=(ImageView) itemView.findViewById(R.id.purchase_image);
            purchase_name=(TextView)itemView.findViewById(R.id.purchase_name);
            purchase_quantity=(TextView)itemView.findViewById(R.id.purchase_quantity);
            purchase_type=(TextView)itemView.findViewById(R.id.purchase_type);
        }
    }
    public PurchaseListAdapter(Context mContext){
        this.mContext=mContext;


    }
    @Override
    public PurchaseListAdapter.MyViewHolderEleven onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_purchase_list,parent,false);

        return new PurchaseListAdapter.MyViewHolderEleven(view);
    }

    @Override
    public void onBindViewHolder(PurchaseListAdapter.MyViewHolderEleven holder, final int position) {

        holder.purchase_name.setText(Purchasename[position]);
        holder.purchase_quantity.setText(PurchaseMl[position]);
        holder.purchase_type.setText(PurchaseType[position]);
        //   holder.bottle_pic.setBackgroundResource(Bottleimages[position]);
        try{
            Picasso.with(mContext)
                    .load(PurchaseImages[position])
                    .resize(65, 65)
                    .into(holder.purchase_image);
        }catch (Exception e){

        }

        holder.purchases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(mContext,BottleDescriptionActivity.class);
                mContext.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return Purchasename.length;
    }

}
