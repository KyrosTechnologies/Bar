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

import com.kyros.technologies.bar.Common.activity.Activity.VenueBottleDescription;
import com.kyros.technologies.bar.Common.activity.Activity.VenueKegDescription;
import com.kyros.technologies.bar.Purchase.Activity.Activity.AddKegDescriptionPurchase;
import com.kyros.technologies.bar.Purchase.Activity.Activity.BottlePurchaseStock;
import com.kyros.technologies.bar.Purchase.Activity.Activity.MyPurchaseListActivity;
import com.kyros.technologies.bar.R;
import com.kyros.technologies.bar.utils.Purchase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Rohin on 06-06-2017.
 */

public class VenueSummaryAdapter extends RecyclerView.Adapter<VenueSummaryAdapter.MyViewHolderEleven>{

    private Context mContext;
    private ArrayList<Purchase> purchases;

    public class MyViewHolderEleven extends RecyclerView.ViewHolder{
        public TextView venue_sum_name,venue_capacity,venue_bottles,venue_type;
        public ImageView venue_image;
        public LinearLayout venue_details;

        public MyViewHolderEleven(View itemView) {
            super(itemView);
            venue_sum_name=(TextView)itemView.findViewById(R.id.venue_sum_name);
            venue_capacity=(TextView)itemView.findViewById(R.id.venue_capacity);
            venue_bottles=(TextView)itemView.findViewById(R.id.venue_bottles);
            venue_type=(TextView)itemView.findViewById(R.id.venue_type);
            venue_image=(ImageView)itemView.findViewById(R.id.venue_image);
            venue_details=(LinearLayout)itemView.findViewById(R.id.venue_details);
        }
    }
    public VenueSummaryAdapter(Context mContext,ArrayList<Purchase>purchaseArrayList){
        this.mContext=mContext;
        this.purchases=purchaseArrayList;


    }
    @Override
    public VenueSummaryAdapter.MyViewHolderEleven onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_venue_summary,parent,false);

        return new VenueSummaryAdapter.MyViewHolderEleven(view);
    }

    @Override
    public void onBindViewHolder(VenueSummaryAdapter.MyViewHolderEleven holder, final int position) {

        Purchase purchase=purchases.get(position);
        final int id=purchase.getId();
        final int userprofileid=purchase.getUserprofileid();
        String barid=purchase.getBarid();
        String sectionid=purchase.getSectionid();
        final String liquorname=purchase.getLiquorname();
        final String liquorcapacity=purchase.getLiquorcapacity();
        final String shots=purchase.getShots();
        final String category=purchase.getCategory();
        final String subcategory=purchase.getSubcategory();
        final String parlevel=purchase.getParlevel();
        final String distributorname=purchase.getDistributorname();
        final String price=purchase.getPriceunit();
        final String binnumber=purchase.getBinnumber();
        final String productcode=purchase.getProductcode();
        String createdon=purchase.getCreatedon();
        String modifiedon=purchase.getModifiedon();
        final String minval=purchase.getMinvalue();
        final String maxval=purchase.getMaxvalue();
        final String picture=purchase.getSmall_picture_url();
        String totalbottles=purchase.getTotalbottles();
        final String type=purchase.getType();
        final String fullweight=purchase.getFullweight();
        final String emptyweight=purchase.getEmptyweight();


        try{
            Picasso.with(mContext)
                    .load(picture)
                    .resize(65, 65)
                    .into(holder.venue_image);
        }catch (Exception e){
            e.printStackTrace();

        }

        try {
            holder.venue_sum_name.setText(liquorname);
            holder.venue_capacity.setText(liquorcapacity);
            String ftotalbottles=totalbottles+" Bottles";
            holder.venue_type.setText(type);
            holder.venue_bottles.setText(ftotalbottles);

        }catch (Exception e){
            e.printStackTrace();
        }

        holder.venue_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type.equals("bottle")){
                    Intent i=new Intent(mContext,VenueBottleDescription.class);
                    i.putExtra("name", liquorname);
                    i.putExtra("capacity", liquorcapacity);
                    i.putExtra("category", category);
                    i.putExtra("subcategory",subcategory);
                    i.putExtra("shots", shots);
                    i.putExtra("parvalue", parlevel);
                    i.putExtra("distributorname", distributorname);
                    i.putExtra("price", price);
                    i.putExtra("binnumber", binnumber);
                    i.putExtra("productcode", productcode);
                    i.putExtra("image",picture);
                    i.putExtra("minvalue",minval);
                    i.putExtra("maxvalue",maxval);
                    i.putExtra("type","update");
                    i.putExtra("id",String.valueOf(id));
                    mContext.startActivity(i);
                }else if(type.equals("keg")) {
                    Intent i = new Intent(mContext, VenueKegDescription.class);
                    i.putExtra("name", liquorname);
                    i.putExtra("fullweight", fullweight);
                    i.putExtra("emptyweight", emptyweight);
                    i.putExtra("category", category);
                    i.putExtra("subcategory", subcategory);
                    i.putExtra("shots", shots);
                    i.putExtra("parvalue", parlevel);
                    i.putExtra("distributorname", distributorname);
                    i.putExtra("price", price);
                    i.putExtra("binnumber", binnumber);
                    i.putExtra("productcode", productcode);
                    i.putExtra("image", picture);
                    i.putExtra("minvalue", minval);
                    i.putExtra("maxvalue", maxval);
                    i.putExtra("type", "update");
                    i.putExtra("id", String.valueOf(id));

                    mContext.startActivity(i);
                }else{
                    Intent i=new Intent(mContext,VenueBottleDescription.class);
                    mContext.startActivity(i);
                }
            }
        });

        //   holder.bottle_pic.setBackgroundResource(Bottleimages[position]);
    }

    @Override
    public int getItemCount() {
        return purchases.size();
    }

}