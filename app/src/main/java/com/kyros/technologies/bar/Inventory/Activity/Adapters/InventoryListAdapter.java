package com.kyros.technologies.bar.Inventory.Activity.Adapters;

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
import com.kyros.technologies.bar.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Rohin on 17-05-2017.
 */

public class InventoryListAdapter extends RecyclerView.Adapter<InventoryListAdapter.MyViewHolderEleven>{
    private Context mContext;
    String[]Alchohol=new String[]{"Anchor Porter","Aristocrat Rum"};
    String[]AlchoholType=new String[]{"Beer","Rum"};
    String[]Ml=new String[]{"750 Ml","500 Ml"};
    public int[]LiquorImages=new int[]{R.drawable.beer,R.drawable.rum};


    public class MyViewHolderEleven extends RecyclerView.ViewHolder{
        public LinearLayout description_act;
        public ImageView bottle_image;
        public TextView liquor_name,liquor_quantity,alchohol_types;

        public MyViewHolderEleven(View itemView) {
            super(itemView);
            description_act=(LinearLayout)itemView.findViewById(R.id.description_act);
            bottle_image=(ImageView) itemView.findViewById(R.id.bottle_image);
            liquor_name=(TextView)itemView.findViewById(R.id.liquor_name);
            liquor_quantity=(TextView)itemView.findViewById(R.id.liquor_quantity);
            alchohol_types=(TextView)itemView.findViewById(R.id.alchohol_types);
        }
    }
    public InventoryListAdapter(Context mContext){
        this.mContext=mContext;


    }
    @Override
    public InventoryListAdapter.MyViewHolderEleven onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_inventory_list,parent,false);

        return new InventoryListAdapter.MyViewHolderEleven(view);
    }

    @Override
    public void onBindViewHolder(InventoryListAdapter.MyViewHolderEleven holder, final int position) {

        holder.liquor_name.setText(Alchohol[position]);
        holder.liquor_quantity.setText(Ml[position]);
        holder.alchohol_types.setText(AlchoholType[position]);
        //   holder.bottle_pic.setBackgroundResource(Bottleimages[position]);
        try{
            Picasso.with(mContext)
                    .load(LiquorImages[position])
                    .resize(65, 65)
                    .into(holder.bottle_image);
        }catch (Exception e){

        }

        holder.description_act.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(mContext,BottleDescriptionActivity.class);
                mContext.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return Alchohol.length;
    }

}
