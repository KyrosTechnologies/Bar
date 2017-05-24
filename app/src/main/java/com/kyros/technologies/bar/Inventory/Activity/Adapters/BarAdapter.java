package com.kyros.technologies.bar.Inventory.Activity.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kyros.technologies.bar.Inventory.Activity.Activity.AddSectionActivity;
import com.kyros.technologies.bar.R;
import com.kyros.technologies.bar.utils.MyBar;

import java.util.ArrayList;

/**
 * Created by Rohin on 17-05-2017.
 */

public class BarAdapter extends RecyclerView.Adapter<BarAdapter.MyViewHolderEleven>{
    private Context mContext;
//    String[]BarName=new String[]{"Front Bar","Side Bar","Main Bar"};
//    String[]Updates=new String[]{"Updated 3 days ago","Updated 2 days ago","Updated 4 days ago"};
    private ArrayList<MyBar>barArrayList;


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
    public BarAdapter(Context mContext, ArrayList<MyBar>barArrayList){
        this.mContext=mContext;
        this.barArrayList=barArrayList;

    }
    @Override
    public BarAdapter.MyViewHolderEleven onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_bar,parent,false);

        return new BarAdapter.MyViewHolderEleven(view);
    }

    @Override
    public void onBindViewHolder(BarAdapter.MyViewHolderEleven holder, final int position) {
        MyBar bar=barArrayList.get(position);
        final int id=bar.getid();
        String name=bar.getBarname();
        String datecreated=bar.getDatecreated();

        if (name==null){
            name="";

        }

        if (datecreated==null){

            datecreated="";

        }
        holder.first_bar.setText(name);
        holder.updates.setText("Updated on "+datecreated);

        //   holder.bottle_pic.setBackgroundResource(Bottleimages[position]);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date strDate = sdf.parse(valid_until);
//        if (new Date().after(strDate)) {
//            catalog_outdated = 1;
//        }
        holder.front_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.kyros.technologies.bar.SharedPreferences.PreferenceManager store= com.kyros.technologies.bar.SharedPreferences.PreferenceManager.getInstance(mContext);
                store.putBarId(String.valueOf(id));
                Intent i=new Intent(mContext,AddSectionActivity.class);
                mContext.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return barArrayList.size();
    }


}
