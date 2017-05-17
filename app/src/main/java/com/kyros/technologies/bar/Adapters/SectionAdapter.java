package com.kyros.technologies.bar.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kyros.technologies.bar.R;
import com.kyros.technologies.bar.Activity.SectionBottlesActivity;

/**
 * Created by Rohin on 17-05-2017.
 */

public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.MyViewHolderEleven>{
    private Context mContext;
    String[]SectionName=new String[]{"Front Section","Side Section","Main Section"};
    String[]SectionUpdates=new String[]{"Updated 3 days ago","Updated 2 days ago","Updated 4 days ago"};



    public class MyViewHolderEleven extends RecyclerView.ViewHolder{
        public LinearLayout add_section;
        public TextView section_add,section_updates;

        public MyViewHolderEleven(View itemView) {
            super(itemView);
            add_section=(LinearLayout) itemView.findViewById(R.id.add_section);
            section_add=(TextView)itemView.findViewById(R.id.section_add);
            section_updates=(TextView)itemView.findViewById(R.id.section_updates);

        }
    }
    public SectionAdapter(Context mContext){
        this.mContext=mContext;


    }
    @Override
    public SectionAdapter.MyViewHolderEleven onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_section,parent,false);

        return new SectionAdapter.MyViewHolderEleven(view);
    }

    @Override
    public void onBindViewHolder(SectionAdapter.MyViewHolderEleven holder, final int position) {

        holder.section_add.setText(SectionName[position]);
        holder.section_updates.setText(SectionUpdates[position]);
        //   holder.bottle_pic.setBackgroundResource(Bottleimages[position]);

        holder.add_section.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(mContext,SectionBottlesActivity.class);
                mContext.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return SectionName.length;
    }


}
