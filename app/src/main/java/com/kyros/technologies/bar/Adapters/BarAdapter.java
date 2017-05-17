package com.kyros.technologies.bar.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kyros.technologies.bar.Activity.AddSectionActivity;
import com.kyros.technologies.bar.R;

/**
 * Created by Rohin on 17-05-2017.
 */

public class BarAdapter extends RecyclerView.Adapter<BarAdapter.MyViewHolderEleven>{
    private Context mContext;
    String[]BarName=new String[]{"Front Bar","Side Bar","Main Bar"};
    String[]Updates=new String[]{"Updated 3 days ago","Updated 2 days ago","Updated 4 days ago"};



    public class MyViewHolderEleven extends RecyclerView.ViewHolder{
        public LinearLayout front_bar;
        public TextView first_bar,updates;

        public MyViewHolderEleven(View itemView) {
            super(itemView);
            front_bar=(LinearLayout) itemView.findViewById(R.id.front_bar);
            first_bar=(TextView)itemView.findViewById(R.id.first_bar);
            updates=(TextView)itemView.findViewById(R.id.updates);

        }
    }
    public BarAdapter(Context mContext){
        this.mContext=mContext;


    }
    @Override
    public BarAdapter.MyViewHolderEleven onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_bar,parent,false);

        return new BarAdapter.MyViewHolderEleven(view);
    }

    @Override
    public void onBindViewHolder(BarAdapter.MyViewHolderEleven holder, final int position) {

        holder.first_bar.setText(BarName[position]);
        holder.updates.setText(Updates[position]);
        //   holder.bottle_pic.setBackgroundResource(Bottleimages[position]);

        holder.front_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(mContext,AddSectionActivity.class);
                mContext.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return BarName.length;
    }


}
