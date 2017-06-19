package com.kyros.technologies.bar.Purchase.Activity.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kyros.technologies.bar.Purchase.Activity.Activity.MyPurchaseListActivity;
import com.kyros.technologies.bar.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Rohin on 29-05-2017.
 */

public class InventoryPurchaseAdapter extends RecyclerView.Adapter<InventoryPurchaseAdapter.MyViewHolderEleven>{
    private Context mContext;
    public int[]Bottleimages=new int[]{R.drawable.absinthe,R.drawable.bitters,R.drawable.bourbon,R.drawable.brandy,R.drawable.gin,
            R.drawable.liquier,R.drawable.rum,R.drawable.tequila,R.drawable.vodka,R.drawable.whiskey,R.drawable.whisky,R.drawable.bottledwater,
            R.drawable.juices,R.drawable.milk,R.drawable.softdrinks,R.drawable.syrup,R.drawable.champagne,R.drawable.chinesewine,R.drawable.desertwine,
            R.drawable.japanesesake,R.drawable.japaneseshochu,R.drawable.koreansoju,R.drawable.port,R.drawable.redwine,R.drawable.whitewine,
            R.drawable.rose,R.drawable.sparklingwine,R.drawable.beer,R.drawable.others};
    String[]Bottles=new String[]{"Absinthe","Bitters","Bourbon","Brandy","Gin","Liquier","Rum","Tequila","Vodka","Whiskey","Whisky",
            "Bottled Water","Juices","Milk","Soft Drinks","Syrup","Champagne","Chinese Wine","Desert Wine","Japanese Sake","Japanese Sho Chu",
            "Korean Soju","Port","Red Wine","White Wine","Rose","Sparkling Wine","Beer","Others"};
    String [] BottleName=new String[]{"Absinthe","Beer","Bitters","Bourbon","Brandy","Cachaca","Cider","Cognac","Gin","Liquier","Mezcal","Non-Alcoholic",
    "Others","Rum","Rye","Sake","Scotch","Soju","Tequila","Vermouth","Vodka","Whisky","Wine"};
    int[] BottlesImage=new int[]{R.drawable.absinthe,R.drawable.beer,R.drawable.bitters,R.drawable.bourbon,R.drawable.brandy,R.drawable.cachasa,R.drawable.cider,
    R.drawable.cognac,R.drawable.gin,R.drawable.liquier,R.drawable.mezcal,R.drawable.non_alcoholic_image,R.drawable.others,R.drawable.rum,R.drawable.rye,
            R.drawable.japanesesake,R.drawable.scotch,R.drawable.koreansoju,R.drawable.tequila,R.drawable.vermouth,R.drawable.vodka,R.drawable.whiskey,R.drawable.desertwine};



    public class MyViewHolderEleven extends RecyclerView.ViewHolder{
        public CardView purchase_lists;
        public ImageView purchase_bottle_pic;
        public TextView purchase_brand_name;

        public MyViewHolderEleven(View itemView) {
            super(itemView);
            purchase_lists=(CardView)itemView.findViewById(R.id.purchase_lists);
            purchase_bottle_pic=(ImageView) itemView.findViewById(R.id.purchase_bottle_pic);
            purchase_brand_name=(TextView)itemView.findViewById(R.id.purchase_brand_name);

        }
    }
    public InventoryPurchaseAdapter(Context mContext){
        this.mContext=mContext;


    }
    @Override
    public InventoryPurchaseAdapter.MyViewHolderEleven onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.purchase_type,parent,false);

        return new InventoryPurchaseAdapter.MyViewHolderEleven(view);
    }

    @Override
    public void onBindViewHolder(InventoryPurchaseAdapter.MyViewHolderEleven holder, final int position) {

        holder.purchase_brand_name.setText(BottleName[position]);
        //   holder.bottle_pic.setBackgroundResource(Bottleimages[position]);
        try{
            Picasso.with(mContext)
                    .load(BottlesImage[position])
                    .resize(200, 200)
                    .into(holder.purchase_bottle_pic);
        }catch (Exception e){

        }

        holder.purchase_lists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(mContext,MyPurchaseListActivity.class);
                String value=BottleName[position];
//                if(value.equals("Others")){
//                    value="Blanks";
//                }
                i.putExtra("category",value);
                mContext.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return BottleName.length;
    }

}