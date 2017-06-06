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

import com.kyros.technologies.bar.Inventory.Activity.Activity.BottleDescriptionActivity;
import com.kyros.technologies.bar.Inventory.Activity.Adapters.LiquorApiAdapter;
import com.kyros.technologies.bar.Inventory.Activity.List.LiquorListClass;
import com.kyros.technologies.bar.Purchase.Activity.Activity.BottlePurchaseStock;
import com.kyros.technologies.bar.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Rohin on 29-05-2017.
 */

public class PurchaseApiAdapter extends RecyclerView.Adapter<PurchaseApiAdapter.MyViewHolderEleven>{
    private Context mContext;
    private ArrayList<LiquorListClass> list;

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
    public PurchaseApiAdapter(Context mContext,ArrayList<LiquorListClass>liquorListClasses){
        this.mContext=mContext;
        this.list=liquorListClasses;

    }
    @Override
    public PurchaseApiAdapter.MyViewHolderEleven onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_add_bottle,parent,false);

        return new PurchaseApiAdapter.MyViewHolderEleven(view);
    }

    @Override
    public void onBindViewHolder(PurchaseApiAdapter .MyViewHolderEleven holder, final int position) {
        final LiquorListClass listClass=list.get(position);
        String name=listClass.getName();
        String subtype=listClass.getAlcohol_subtype();
        if(subtype==null){
            subtype="";
        }
        if(name==null){
            name="";
        }
        final int  quanti=listClass.getCapacity_mL();

        String alchotype=listClass.getAlcohol_type();
        if(alchotype==null){
            alchotype="";
        }
        final String smallpic=listClass.getSmall_picture_url();
        final double minvalue=listClass.getMin_height();
        final double maxvalue=listClass.getMax_height();


        try {

            holder.bottle_name.setText(name);
            holder.quantity.setText(String.valueOf(quanti)+" ML");
            holder.alchohol_type.setText(alchotype);

        }catch (Exception e){
            e.printStackTrace();
        }


        //   holder.bottle_pic.setBackgroundResource(Bottleimages[position]);
        try{
            Picasso.with(mContext)
                    .load(smallpic)
                    .resize(65, 65)
                    .into(holder.liquor_bottle);
        }catch (Exception e){

        }

        final String finalName = name;
        final String finalSubtype = subtype;
        final String finalAlchotype = alchotype;
        holder.add_bottles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(mContext,BottlePurchaseStock.class);
                i.putExtra("name", finalName);
                i.putExtra("capacity",String.valueOf(quanti));
                i.putExtra("category", finalAlchotype);
                i.putExtra("subcategory", finalSubtype);
                i.putExtra("minvalue",String.valueOf(minvalue));
                i.putExtra("maxvalue",String.valueOf(maxvalue));
                i.putExtra("picture",smallpic);
                mContext.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}