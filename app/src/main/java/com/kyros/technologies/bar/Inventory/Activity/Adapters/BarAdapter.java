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

import com.kyros.technologies.bar.Inventory.Activity.Activity.AddSectionActivity;
import com.kyros.technologies.bar.Inventory.Activity.interfacesmodel.ItemTouchHelperViewHolder;
import com.kyros.technologies.bar.Inventory.Activity.interfacesmodel.OnBarListChangedListner;
import com.kyros.technologies.bar.Inventory.Activity.interfacesmodel.OnStartDragListener;
import com.kyros.technologies.bar.R;
import com.kyros.technologies.bar.SharedPreferences.PreferenceManager;
import com.kyros.technologies.bar.utils.MyBar;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Rohin on 17-05-2017.
 */

public class BarAdapter extends RecyclerView.Adapter<BarAdapter.MyViewHolderEleven> implements ItemTouchHelperAdapter{
    private Context mContext;
//    String[]BarName=new String[]{"Front Bar","Side Bar","Main Bar"};
//    String[]Updates=new String[]{"Updated 3 days ago","Updated 2 days ago","Updated 4 days ago"};
    private ArrayList<MyBar>barArrayList;
    private PreferenceManager store;

    private OnStartDragListener mDragStartListener;
    private OnBarListChangedListner mListChangedListener;


    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(barArrayList, fromPosition, toPosition);
        mListChangedListener.onBarListChanged(barArrayList);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        barArrayList.remove(position);
        notifyItemRemoved(position);
    }


    public class MyViewHolderEleven extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
        public LinearLayout front_bar;
        public TextView first_bar,updates;

        public MyViewHolderEleven(View itemView) {
            super(itemView);
            front_bar=(LinearLayout) itemView.findViewById(R.id.front_bar);
            first_bar=(TextView)itemView.findViewById(R.id.first_bar);
            updates=(TextView)itemView.findViewById(R.id.updates);
            store= PreferenceManager.getInstance(mContext.getApplicationContext());

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
    public BarAdapter(Context mContext, ArrayList<MyBar>barArrayList,OnStartDragListener dragLlistener,
                      OnBarListChangedListner listChangedListener){
        this.mContext=mContext;
        this.barArrayList=barArrayList;
        this.mListChangedListener=listChangedListener;
        this.mDragStartListener=dragLlistener;

    }
//    public BarAdapter(Context mContext,OnStartDragListener dragStartListener){
//        this.mContext=mContext;
//        mDragStartListener = dragStartListener;
//
//    }
    @Override
    public BarAdapter.MyViewHolderEleven onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_bar,parent,false);

        return new BarAdapter.MyViewHolderEleven(view);
    }

    @Override
    public void onBindViewHolder(final BarAdapter.MyViewHolderEleven holder, final int position) {
        MyBar bar=barArrayList.get(position);
        final int id=bar.getid();
        String name=bar.getBarname();
        String datecreated=bar.getDatecreated();
        store= PreferenceManager.getInstance(mContext.getApplicationContext());


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
//        holder.front_bar.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                try {
//
//                    if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
//                        if (holder!=null){
//                            mDragStartListener.onStartDrag(holder);
//                        }
//                    }
//
//                }catch (Exception e){
//
//                    e.printStackTrace();
//                }
////                notifyDataSetChanged();
////                Toast.makeText(mContext.getApplicationContext(),"Save",Toast.LENGTH_SHORT).show();
//
//                try{
//                    Gson gson=new Gson();
//                    String barlist=gson.toJson(barArrayList);
//                    store.putBar(barlist);
//
//                }catch (Exception e){
//                    Log.d("exception_conve_gson",e.getMessage());
//                }
//
//                return false;
//            }
//        });
        holder.front_bar.setOnTouchListener(new View.OnTouchListener() {
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
        return barArrayList.size();
    }
    
}
