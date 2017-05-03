package com.kyros.technologies.bar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by kyros on 02-05-2017.
 */

public class DummyAdapter extends RecyclerView.Adapter<DummyAdapter.MyViewHolder> {
   private Context mContext;
    private List<String>list;
    public DummyAdapter(Context mContext, List<String>list){
       this.mContext=mContext;
       this.list=list;
   }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView name;

        public MyViewHolder(View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.name);
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dummy,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String  value=list.get(position);
        if(value!=null){
            holder.name.setText(value);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
