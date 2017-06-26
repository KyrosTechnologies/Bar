package com.kyros.technologies.bar.Inventory.Activity.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kyros.technologies.bar.Inventory.Activity.Activity.LiquorSlider;
import com.kyros.technologies.bar.Inventory.Activity.interfacesmodel.ItemTouchHelperViewHolder;
import com.kyros.technologies.bar.Inventory.Activity.interfacesmodel.OnBottleListChangedListener;
import com.kyros.technologies.bar.Inventory.Activity.interfacesmodel.OnStartDragListener;
import com.kyros.technologies.bar.R;
import com.kyros.technologies.bar.utils.UtilSectionBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Rohin on 17-05-2017.
 */

public class SectionBarAdapter extends RecyclerView.Adapter<SectionBarAdapter.MyViewHolderEleven>implements ItemTouchHelperAdapter{
    private Context mContext;
    //    String[]Alchohol=new String[]{"Anchor Porter","Aristocrat Rum"};
//    String[]AlchoholType=new String[]{"Beer","Rum"};
//    String[]Ml=new String[]{"750 Ml","900 Ml"};
    public int[]LiquorImages=new int[]{R.drawable.beer,R.drawable.rum};
    private ArrayList<UtilSectionBar> utilSectionBarArrayList;
    private OnStartDragListener mDragStartListener;
    private OnBottleListChangedListener mListChangedListener;


    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(utilSectionBarArrayList, fromPosition, toPosition);
        mListChangedListener.onBottleListChanged(utilSectionBarArrayList);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        utilSectionBarArrayList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void swipeToDelete(int position) {
        utilSectionBarArrayList.remove(position);
        notifyItemRemoved(position);
    }


    public class MyViewHolderEleven extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
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

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);

        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);

        }

    }

    public SectionBarAdapter(Context mContext,ArrayList<UtilSectionBar>utilSectionBarArrayList,OnStartDragListener dragLlistener,
                             OnBottleListChangedListener listChangedListener){
        this.mContext=mContext;
        this.utilSectionBarArrayList=utilSectionBarArrayList;
        this.mListChangedListener=listChangedListener;
        this.mDragStartListener=dragLlistener;

    }
    @Override
    public SectionBarAdapter.MyViewHolderEleven onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_section_bar,parent,false);

        return new SectionBarAdapter.MyViewHolderEleven(view);
    }

    @Override
    public void onBindViewHolder(final SectionBarAdapter.MyViewHolderEleven holder, final int position) {

        UtilSectionBar utilSectionBar=utilSectionBarArrayList.get(position);
        final int id=utilSectionBar.getId();
        final int barid=utilSectionBar.getBarid();
        final int sectionid=utilSectionBar.getSectionid();
        String liquorname=utilSectionBar.getLiquorname();
        String liquorcapacity=utilSectionBar.getLiquorcapacity();
        String shots=utilSectionBar.getShots();
        String category=utilSectionBar.getCategory();
        String subcategory=utilSectionBar.getSubcategory();
        String parlevel=utilSectionBar.getParlevel();
        String disname=utilSectionBar.getDistributorname();
        String price=utilSectionBar.getPriceunit();
        String binnumber=utilSectionBar.getBinnumber();
        String productcode=utilSectionBar.getProductcode();
        String pictureurl=utilSectionBar.getPictureurl();
        final double minvalue=utilSectionBar.getMinvalue();
        final double maxvalue=utilSectionBar.getMaxvalue();
        String totalbottles=utilSectionBar.getTotalbottles();
        final String type=utilSectionBar.getType();
        String fullweight=utilSectionBar.getFullweight();
        String emptyweight=utilSectionBar.getEmptyweight();

        if (liquorname==null){
            liquorname="";
        }

        if (liquorcapacity==null){
            liquorcapacity="";
        }

        if (category==null){
            category="";
        }
        if (shots==null){
            shots="";
        }
        if (subcategory==null){
            subcategory="";
        }
        if (parlevel==null){
            parlevel="";
        }
        if (disname==null){
            disname="";
        }
        if (price==null){
            price="";
        }
        if (binnumber==null){
            binnumber="";
        }
        if (productcode==null){
            productcode="";
        }
        if (pictureurl==null){
            pictureurl="";
        }
        if (totalbottles==null){
            totalbottles="";
        }
        if (fullweight==null){
            fullweight="";
        }
        if (emptyweight==null){
            emptyweight="";
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
        final String finalPictureurl = pictureurl;
        final String finalShots = shots;
        final String finalSubcategory = subcategory;
        final String finalParlevel = parlevel;
        final String finalDisname = disname;
        final String finalPrice = price;
        final String finalBinnumber = binnumber;
        final String finalProductcode = productcode;
        final String finalTotalbottles = totalbottles;
        final String finalFullweight = fullweight;
        final String finalEmptyweight = emptyweight;
        final String finalCategory = category;
        holder.slider_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(mContext,LiquorSlider.class);
                i.putExtra("position", position);
                mContext.startActivity(i);
            }
        });

        holder.slider_edit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(holder);
                }
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return utilSectionBarArrayList.size();
    }

    public void setFilter(ArrayList<UtilSectionBar> utilsection1){
        utilSectionBarArrayList=new ArrayList<>();
        utilSectionBarArrayList.addAll(utilsection1);
        notifyDataSetChanged();
    }

}