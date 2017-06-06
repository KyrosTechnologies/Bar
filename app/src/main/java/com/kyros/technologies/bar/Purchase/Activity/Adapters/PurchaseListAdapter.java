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

import com.kyros.technologies.bar.Inventory.Activity.Activity.AddKegDescription;
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
//    public int[]LiquorImages=new int[]{R.drawable.beer,R.drawable.rum};
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
        int userprofileid=purchase.getUserprofileid();
        String liquorname=purchase.getLiquorname();
        String liquorcapacity=purchase.getLiquorcapacity();
        String shots=purchase.getShots();
        if(shots==null){
            shots="";
        }
        String category=purchase.getCategory();
        final String subcategory=purchase.getSubcategory();
        if(subcategory==null){

        }
        String parlevel=purchase.getParlevel();
        if(parlevel==null){
            parlevel="";
        }
        String disname=purchase.getDistributorname();
        if(disname==null){
            disname="";
        }
        String price=purchase.getPriceunit();
        if(price==null){
            price="";
        }
        String binnumber=purchase.getBinnumber();
        if(binnumber==null){
            binnumber="";
        }
        String productcode=purchase.getProductcode();
        if(productcode==null){
            productcode="";
        }
        String createdon=purchase.getCreatedon();
        if(createdon==null){
            createdon="";
        }
        final String minvalue=purchase.getMinvalue();
        final String maxvalue=purchase.getMaxvalue();
        final String pictureurl=purchase.getPictureurl();
        final String type=purchase.getType();
        String totalbottles=purchase.getTotalbottles();
        if(totalbottles==null){
            totalbottles="";
        }
        String emptyweight=purchase.getEmptyweight();
        if(emptyweight==null){
            emptyweight="";
        }
        String fullweight=purchase.getFullweight();
        if(fullweight==null){
            fullweight="";
        }


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
                    .load(pictureurl)
                    .resize(65, 65)
                    .into(holder.purchase_image);
        }catch (Exception e){
            try{
                Picasso.with(mContext)
                        .load(PurchaseImages[position])
                        .resize(65, 65)
                        .into(holder.purchase_image);
            }catch (Exception es){
                es.printStackTrace();
            }

        }

        final String finalLiquorname = liquorname;
        final String finalFullweight = fullweight;
        final String finalEmptyweight = emptyweight;
        final String finalCategory = category;
        final String finalShots = shots;
        final String finalParlevel = parlevel;
        final String finalDisname = disname;
        final String finalPrice = price;
        final String finalBinnumber = binnumber;
        final String finalProductcode = productcode;
        final String finalLiquorcapacity = liquorcapacity;
        holder.purchases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(type.equals("bottle")){
                    Intent i=new Intent(mContext,BottlePurchaseStock.class);
                    i.putExtra("name", finalLiquorname);
                    i.putExtra("capacity", finalLiquorcapacity);
                    i.putExtra("category", finalCategory);
                    i.putExtra("subcategory",subcategory);
                    i.putExtra("shots", finalShots);
                    i.putExtra("parvalue", finalParlevel);
                    i.putExtra("distributorname", finalDisname);
                    i.putExtra("price", finalPrice);
                    i.putExtra("binnumber", finalBinnumber);
                    i.putExtra("productcode", finalProductcode);
                    i.putExtra("image",pictureurl);
                    i.putExtra("minvalue",minvalue);
                    i.putExtra("maxvalue",maxvalue);
                    i.putExtra("type","update");
                    i.putExtra("id",String.valueOf(id));
                    mContext.startActivity(i);
                }else if(type.equals("keg")){
                    Intent i=new Intent(mContext,AddKegDescription.class);
                    i.putExtra("name", finalLiquorname);
                    i.putExtra("fullweight", finalFullweight);
                    i.putExtra("emptyweight", finalEmptyweight);
                    i.putExtra("category", finalCategory);
                    i.putExtra("subcategory",subcategory);
                    i.putExtra("shots", finalShots);
                    i.putExtra("parvalue", finalParlevel);
                    i.putExtra("distributorname", finalDisname);
                    i.putExtra("price", finalPrice);
                    i.putExtra("binnumber", finalBinnumber);
                    i.putExtra("productcode", finalProductcode);
                    i.putExtra("image",pictureurl);
                    i.putExtra("minvalue",minvalue);
                    i.putExtra("maxvalue",maxvalue);
                    i.putExtra("type","update");
                    i.putExtra("id",String.valueOf(id));


                    mContext.startActivity(i);
                }else{
                    Intent i=new Intent(mContext,BottlePurchaseStock.class);
                    mContext.startActivity(i);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return purchaseArrayList.size();
    }

}
