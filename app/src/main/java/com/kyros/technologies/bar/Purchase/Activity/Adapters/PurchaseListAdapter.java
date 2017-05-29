package com.kyros.technologies.bar.Purchase.Activity.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kyros.technologies.bar.Purchase.Activity.Activity.BottlePurchaseStock;
import com.kyros.technologies.bar.R;
import com.kyros.technologies.bar.utils.Purchase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Rohin on 17-05-2017.
 */

public class PurchaseListAdapter extends RecyclerView.Adapter<PurchaseListAdapter.MyViewHolderEleven>{
    private Context mContext;
    public int[]PurchaseImages=new int[]{R.drawable.beer,R.drawable.rum};
    public int[]LiquorImages=new int[]{R.drawable.beer,R.drawable.rum};
    private ArrayList<Purchase> purchaseArrayList;

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
    public PurchaseListAdapter(Context mContext,ArrayList<Purchase>purchaseArrayList){
        this.mContext=mContext;
        this.purchaseArrayList=purchaseArrayList;

    }
    @Override
    public PurchaseListAdapter.MyViewHolderEleven onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_purchase_list,parent,false);

        return new PurchaseListAdapter.MyViewHolderEleven(view);
    }

    @Override
    public void onBindViewHolder(PurchaseListAdapter.MyViewHolderEleven holder, final int position) {

        Purchase purchase=purchaseArrayList.get(position);
        final int id=purchase.getId();
        String liquorname=purchase.getLiquorname();
        String liquorcapacity=purchase.getLiquorcapacity();
        String shots=purchase.getShots();
        String category=purchase.getCategory();
        String subcategory=purchase.getSubcategory();
        String parlevel=purchase.getParlevel();
        String disname=purchase.getDistributorname();
        String price=purchase.getPriceunit();
        String binnumber=purchase.getBinnumber();
        String productcode=purchase.getProductcode();

        if (liquorname==null){
            liquorname="";
        }

        if (liquorcapacity==null){
            liquorcapacity="";
        }

        if (category==null){
            category="";
        }

        holder.purchase_name.setText(liquorname);
        holder.purchase_quantity.setText(liquorcapacity);
        holder.purchase_type.setText(category);
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
                Intent i=new Intent(mContext,BottlePurchaseStock.class);
                mContext.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return purchaseArrayList.size();
    }

}
