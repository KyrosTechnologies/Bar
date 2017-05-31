package com.kyros.technologies.bar.Common.activity.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kyros.technologies.bar.Common.activity.Activity.AddEmailActivity;
import com.kyros.technologies.bar.R;
import com.kyros.technologies.bar.utils.EmailManagement;

import java.util.ArrayList;

/**
 * Created by Rohin on 20-05-2017.
 */

public class EmailManagementAdapter extends RecyclerView.Adapter<EmailManagementAdapter.MyViewHolderEleven>{

    private Context mContext;
    private ArrayList<EmailManagement> emailManagementArrayList;

    public class MyViewHolderEleven extends RecyclerView.ViewHolder{
        public LinearLayout management;
        public TextView email_manage_get;

        public MyViewHolderEleven(View itemView) {
            super(itemView);
            management=(LinearLayout)itemView.findViewById(R.id.management);
            email_manage_get=(TextView)itemView.findViewById(R.id.email_manage_get);
        }
    }
    public EmailManagementAdapter(Context mContext,ArrayList<EmailManagement>emailManagementArrayList){
        this.mContext=mContext;
        this.emailManagementArrayList=emailManagementArrayList;


    }
    @Override
    public EmailManagementAdapter.MyViewHolderEleven onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_email,parent,false);

        return new EmailManagementAdapter.MyViewHolderEleven(view);
    }

    @Override
    public void onBindViewHolder(EmailManagementAdapter.MyViewHolderEleven holder, final int position) {

        EmailManagement emailManagement=emailManagementArrayList.get(position);
        final int id=emailManagement.getId();
        final int userprofile=emailManagement.getUserprofile();
        String useremail=emailManagement.getUseremail();
        String createdon=emailManagement.getCreatedon();

        if (useremail==null){
            useremail="";
        }


        try {
            holder.email_manage_get.setText(useremail);

        }catch (Exception e){
            e.printStackTrace();
        }

        //   holder.bottle_pic.setBackgroundResource(Bottleimages[position]);
        final String finalName = useremail;

        holder.management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.kyros.technologies.bar.SharedPreferences.PreferenceManager store= com.kyros.technologies.bar.SharedPreferences.PreferenceManager.getInstance(mContext);
                Intent i=new Intent(mContext,AddEmailActivity.class);
                i.putExtra("name", finalName);
                mContext.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return emailManagementArrayList.size();
    }

}
