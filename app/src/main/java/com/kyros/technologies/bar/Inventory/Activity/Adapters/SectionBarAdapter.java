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

import com.kyros.technologies.bar.Inventory.Activity.Activity.LiquorSlider;
import com.kyros.technologies.bar.R;
import com.kyros.technologies.bar.utils.UtilSectionBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Rohin on 17-05-2017.
 */

public class SectionBarAdapter extends RecyclerView.Adapter<SectionBarAdapter.MyViewHolderEleven>{
    private Context mContext;
//    String[]Alchohol=new String[]{"Anchor Porter","Aristocrat Rum"};
//    String[]AlchoholType=new String[]{"Beer","Rum"};
//    String[]Ml=new String[]{"750 Ml","900 Ml"};
    public int[]LiquorImages=new int[]{R.drawable.beer,R.drawable.rum};
    private ArrayList<UtilSectionBar> utilSectionBarArrayList;



    public class MyViewHolderEleven extends RecyclerView.ViewHolder{
        public LinearLayout slider_edit;
        public ImageView liquor_image;
        public TextView alchohol_name,bottle_ml,liquor_type;

        public MyViewHolderEleven(View itemView) {
            super(itemView);
            slider_edit=(LinearLayout)itemView.findViewById(R.id.slider_edit);
            liquor_image=(ImageView) itemView.findViewById(R.id.liquor_image);
            alchohol_name=(TextView)itemView.findViewById(R.id.alchohol_name);
            bottle_ml=(TextView)itemView.findViewById(R.id.bottle_ml);
            liquor_type=(TextView)itemView.findViewById(R.id.liquor_type);
        }
    }
    public SectionBarAdapter(Context mContext,ArrayList<UtilSectionBar>utilSectionBarArrayList){
        this.mContext=mContext;
        this.utilSectionBarArrayList=utilSectionBarArrayList;


    }
    @Override
    public SectionBarAdapter.MyViewHolderEleven onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_section_bar,parent,false);

        return new SectionBarAdapter.MyViewHolderEleven(view);
    }

    @Override
    public void onBindViewHolder(SectionBarAdapter.MyViewHolderEleven holder, final int position) {

        UtilSectionBar utilSectionBar=utilSectionBarArrayList.get(position);
        final int id=utilSectionBar.getId();
        final int barid=utilSectionBar.getBarid();
        final int sectionid=utilSectionBar.getSectionid();
        String liquorname=utilSectionBar.getLiquorname();
        String liquorcapacity=utilSectionBar.getLiquorcapacity();
        final String shots=utilSectionBar.getShots();
        String category=utilSectionBar.getCategory();
        String subcategory=utilSectionBar.getSubcategory();
        String parlevel=utilSectionBar.getParlevel();
        String disname=utilSectionBar.getDistributorname();
        String price=utilSectionBar.getPriceunit();
        String binnumber=utilSectionBar.getBinnumber();
        String productcode=utilSectionBar.getProductcode();
        final String pictureurl=utilSectionBar.getPictureurl();
        final double minvalue=utilSectionBar.getMinvalue();
        final double maxvalue=utilSectionBar.getMaxvalue();
        final String totalbottles=utilSectionBar.getTotalbottles();

        if (liquorname==null){
            liquorname="";
        }

        if (liquorcapacity==null){
            liquorcapacity="";
        }

        if (category==null){
            category="";
        }
        holder.alchohol_name.setText(liquorname);
        holder.bottle_ml.setText(String.valueOf(liquorcapacity));
        holder.liquor_type.setText(category);
        //   holder.bottle_pic.setBackgroundResource(Bottleimages[position]);

        try{
            Picasso.with(mContext)
                    .load(pictureurl)
                    .resize(65, 65)
                    .into(holder.liquor_image);
        }catch (Exception e){
            e.printStackTrace();
            try {
                Picasso.with(mContext)
                        .load( LiquorImages[position])
                        .resize(65, 65)
                        .into(holder.liquor_image);
            }catch (Exception eq){

            }

        }
        final String finalLiquorname = liquorname;
        final String finalLiquorcapacity = liquorcapacity;
        holder.slider_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(mContext,LiquorSlider.class);
                i.putExtra("name", finalLiquorname);
                i.putExtra("capacity", finalLiquorcapacity);
                i.putExtra("picture",pictureurl);
                i.putExtra("shots",shots);
                i.putExtra("minvalue",minvalue);
                i.putExtra("maxvalue",maxvalue);
                i.putExtra("totalbottles",totalbottles);
                mContext.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return utilSectionBarArrayList.size();
    }

}
