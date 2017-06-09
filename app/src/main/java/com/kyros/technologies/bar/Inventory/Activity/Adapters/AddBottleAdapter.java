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

import com.kyros.technologies.bar.Inventory.Activity.Activity.AddBottleDescription;
import com.kyros.technologies.bar.R;
import com.kyros.technologies.bar.utils.Purchase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Rohin on 17-05-2017.
 */

public class AddBottleAdapter extends RecyclerView.Adapter<AddBottleAdapter.MyViewHolderEleven>{
    private Context mContext;
//    String[]AlchoholBottle=new String[]{"Anchor Porter","Aristocrat Rum"};
//    String[]AlchoholTypeName=new String[]{"Beer","Rum"};
//    String[]MlType=new String[]{"750 Ml","500 Ml"};
      public int[]LiquorImagesList=new int[]{R.drawable.beer,R.drawable.rum};
    private ArrayList<Purchase> inventoryArrayList;


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
    public AddBottleAdapter(Context mContext,ArrayList<Purchase>inventoryArrayList){
        this.mContext=mContext;
        this.inventoryArrayList=inventoryArrayList;


    }
    @Override
    public AddBottleAdapter.MyViewHolderEleven onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_add_bottle,parent,false);

        return new AddBottleAdapter.MyViewHolderEleven(view);
    }

    @Override
    public void onBindViewHolder(AddBottleAdapter.MyViewHolderEleven holder, final int position) {

        Purchase purchase=inventoryArrayList.get(position);
        final int id=purchase.getId();
        String liquorname=purchase.getLiquorname();
        String liquorcapacity=purchase.getLiquorcapacity();
        String shots=purchase.getShots();
        String category=purchase.getCategory();
       // String subcategory=purchase.getSubcategory();
        String parlevel=purchase.getParlevel();
        String disname=purchase.getDistributorname();
        String price=purchase.getPriceunit();
        String binnumber=purchase.getBinnumber();
        String productcode=purchase.getProductcode();
        String smallpic=purchase.getSmall_picture_url();
        String pictureurl=purchase.getPictureurl();

        if (liquorname==null){
            liquorname="";
        }

        if (liquorcapacity==null){
            liquorcapacity="";
        }

        if (category==null){
            category="";
        }
        try {
            holder.bottle_name.setText(liquorname);
            holder.quantity.setText(liquorcapacity);
            holder.alchohol_type.setText(category);
        }catch (Exception e){
            e.printStackTrace();
        }

        //   holder.bottle_pic.setBackgroundResource(Bottleimages[position]);
        if(pictureurl!=null){
            try{
                Picasso.with(mContext)
                        .load(pictureurl)
                        .resize(65, 65)
                        .into(holder.liquor_bottle);
            }catch (Exception e){
e.printStackTrace();
            }
        }


        holder.add_bottles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(mContext,AddBottleDescription.class);
                mContext.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return inventoryArrayList.size();
    }

}
