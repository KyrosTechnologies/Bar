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
import com.kyros.technologies.bar.Inventory.Activity.Activity.AddKegDescription;
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
        final String shots=purchase.getShots();
        String category=purchase.getCategory();
        final String subcategory=purchase.getSubcategory();
        final String parlevel=purchase.getParlevel();
        final String disname=purchase.getDistributorname();
        final String price=purchase.getPriceunit();
        final String binnumber=purchase.getBinnumber();
        final String productcode=purchase.getProductcode();
        String smallpic=purchase.getSmall_picture_url();
        final String minvalue=purchase.getMinvalue();
        final String maxvalue=purchase.getMaxvalue();
        String totalbotles=purchase.getTotalbottles();
        final String emptyweight=purchase.getEmptyweight();
        final String fullweight=purchase.getFullweight();
        final String createdon=purchase.getCreatedon();

        final String pictureurl=purchase.getPictureurl();
        final String type=purchase.getType();

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


        final String finalLiquorname = liquorname;
        final String finalLiquorcapacity = liquorcapacity;
        final String finalCategory = category;
        holder.add_bottles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type!=null){
                    switch (type){
                        case "bottle":
                            Intent i=new Intent(mContext,AddBottleDescription.class);
                            i.putExtra("image",pictureurl);
                            i.putExtra("liquorname", finalLiquorname);
                            i.putExtra("liquorcapacity", finalLiquorcapacity);
                            i.putExtra("shots",shots);
                            i.putExtra("minvalue",minvalue);
                            i.putExtra("maxvalue",maxvalue);
                            i.putExtra("id",id);
                            i.putExtra("createdon",createdon);
                            i.putExtra("productcode",productcode);
                            i.putExtra("binnumber",binnumber);
                            i.putExtra("price",price);
                            i.putExtra("category", finalCategory);
                            i.putExtra("subcategory",subcategory);
                            i.putExtra("parvalue",parlevel);
                            i.putExtra("distributorname",disname);
                            i.putExtra("update","update");

                            mContext.startActivity(i);
                            break;
                        case "keg":
                            Intent ip=new Intent(mContext,AddKegDescription.class);
                            ip.putExtra("image",pictureurl);
                            ip.putExtra("liquorname", finalLiquorname);
                            ip.putExtra("fullweight", fullweight);
                            ip.putExtra("emptyweight",emptyweight);
                            ip.putExtra("shots",shots);
                            ip.putExtra("minvalue",minvalue);
                            ip.putExtra("maxvalue",maxvalue);
                            ip.putExtra("id",id);
                            ip.putExtra("createdon",createdon);
                            ip.putExtra("productcode",productcode);
                            ip.putExtra("binnumber",binnumber);
                            ip.putExtra("price",price);
                            ip.putExtra("category", finalCategory);
                            ip.putExtra("subcategory",subcategory);
                            ip.putExtra("parvalue",parlevel);
                            ip.putExtra("distributorname",disname);
                            ip.putExtra("update","update");

                            mContext.startActivity(ip);
                            break;
                        default:
                            Intent is=new Intent(mContext,AddBottleDescription.class);
                            is.putExtra("image",pictureurl);
                            is.putExtra("liquorname", finalLiquorname);
                            is.putExtra("liquorcapacity", finalLiquorcapacity);
                            is.putExtra("shots",shots);
                            is.putExtra("minvalue",minvalue);
                            is.putExtra("maxvalue",maxvalue);
                            is.putExtra("id",id);
                            is.putExtra("createdon",createdon);
                            is.putExtra("productcode",productcode);
                            is.putExtra("binnumber",binnumber);
                            is.putExtra("price",price);
                            is.putExtra("category", finalCategory);
                            is.putExtra("subcategory",subcategory);
                            is.putExtra("parvalue",parlevel);
                            is.putExtra("distributorname",disname);
                            is.putExtra("update","update");

                            mContext.startActivity(is);
                            break;

                    }
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return inventoryArrayList.size();
    }
    public void setFilter(ArrayList<Purchase> inventory1){
        inventoryArrayList=new ArrayList<>();
        inventoryArrayList.addAll(inventory1);
        notifyDataSetChanged();
    }
}
