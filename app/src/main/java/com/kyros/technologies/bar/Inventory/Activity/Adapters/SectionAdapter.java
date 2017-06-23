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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kyros.technologies.bar.Inventory.Activity.Activity.SectionBottlesActivity;
import com.kyros.technologies.bar.Inventory.Activity.interfacesmodel.ItemTouchHelperViewHolder;
import com.kyros.technologies.bar.Inventory.Activity.interfacesmodel.OnSectionListChangedListener;
import com.kyros.technologies.bar.Inventory.Activity.interfacesmodel.OnStartDragListener;
import com.kyros.technologies.bar.R;
import com.kyros.technologies.bar.utils.MySection;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Rohin on 17-05-2017.
 */

public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.MyViewHolderEleven>implements ItemTouchHelperAdapter{
    private Context mContext;
//    String[]SectionName=new String[]{"Front Section","Side Section","Main Section"};
//    String[]SectionUpdates=new String[]{"Updated 3 days ago","Updated 2 days ago","Updated 4 days ago"};
    private ArrayList<MySection>sectionArrayList;
    private OnStartDragListener mDragStartListener;
    private OnSectionListChangedListener mListChangedListener;

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(sectionArrayList, fromPosition, toPosition);
        mListChangedListener.onSectionListChanged(sectionArrayList);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        sectionArrayList.remove(position);
        notifyItemRemoved(position);
    }


    public class MyViewHolderEleven extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
        public LinearLayout add_section;
        public TextView section_add,section_updates;

        public MyViewHolderEleven(View itemView) {
            super(itemView);
            add_section=(LinearLayout) itemView.findViewById(R.id.add_section);
            section_add=(TextView)itemView.findViewById(R.id.section_add);
            section_updates=(TextView)itemView.findViewById(R.id.section_updates);

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
    public SectionAdapter(Context mContext,ArrayList<MySection> sectionArrayList,OnStartDragListener dragLlistener,
                          OnSectionListChangedListener listChangedListener){
        this.mContext=mContext;
        this.sectionArrayList=sectionArrayList;
        this.mListChangedListener=listChangedListener;
        this.mDragStartListener=dragLlistener;

    }
    @Override
    public SectionAdapter.MyViewHolderEleven onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_section,parent,false);

        return new SectionAdapter.MyViewHolderEleven(view);
    }

    @Override
    public void onBindViewHolder(final SectionAdapter.MyViewHolderEleven holder, final int position) {

        MySection section=sectionArrayList.get(position);
        final int id=section.getSectionid();
        String sectionname=section.getSectionname();
        String sectioncreated=section.getSectioncreated();
        int barid=section.getBarid();

        if (sectionname==null){
            sectionname="";

        }

        if (sectioncreated==null){

            sectioncreated="";

        }

        holder.section_add.setText(sectionname);
        holder.section_updates.setText(sectioncreated);

        //   holder.bottle_pic.setBackgroundResource(Bottleimages[position]);

        holder.add_section.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.kyros.technologies.bar.SharedPreferences.PreferenceManager store= com.kyros.technologies.bar.SharedPreferences.PreferenceManager.getInstance(mContext);
                store.putSectionId(String.valueOf(id));
                Intent i=new Intent(mContext,SectionBottlesActivity.class);
                mContext.startActivity(i);
            }
        });

        holder.add_section.setOnTouchListener(new View.OnTouchListener() {
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
        return sectionArrayList.size();
    }


}
