package com.kyros.technologies.bar.Common.activity.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kyros.technologies.bar.Common.activity.Activity.UserDetailsActivity;
import com.kyros.technologies.bar.Common.activity.model.BarAccess;
import com.kyros.technologies.bar.Common.activity.model.TempStore;
import com.kyros.technologies.bar.Common.activity.model.UserManagementModel;
import com.kyros.technologies.bar.R;

import java.util.ArrayList;

/**
 * Created by Thirunavukkarasu on 19-06-2017.
 */

public class UserManagementAdapter extends RecyclerView.Adapter<UserManagementAdapter.MyViewHolder>{
    public ArrayList<UserManagementModel>userlist=new ArrayList<UserManagementModel>();
    public Context mContext;
    public UserManagementAdapter(Context mContext, ArrayList<UserManagementModel>userlist){
        this.mContext=mContext;
        this.userlist=userlist;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout user_item_management;
        public TextView user_management_role,user_management_name;
        public MyViewHolder(View itemView) {
            super(itemView);
            user_item_management= (LinearLayout) itemView.findViewById(R.id.user_item_management);
            user_management_role=(TextView)itemView.findViewById(R.id.user_management_role);
            user_management_name=(TextView)itemView.findViewById(R.id.user_management_name);


        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_management_list_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        UserManagementModel model=userlist.get(position);
        String name=model.getUserName();
        if(name==null){
            name="";
        }
        String email=model.getUserEmail();
        if(email==null){
            email="";
        }
        String userrole=model.getUserRole();
        if(userrole==null){
            userrole="";
        }
        int id=model.getId();
        final ArrayList<BarAccess> barAccesses=model.getBarAccess();
        holder.user_management_name.setText(name);
        String value="Role : "+userrole;
        holder.user_management_role.setText(value);
        final String finalName = name;
        final String finalEmail = email;
        final String finalUserrole = userrole;
        holder.user_item_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,UserDetailsActivity.class);
                String adapterbaraccessdata=null;
                try{
                    Gson gson=new Gson();
                     adapterbaraccessdata=gson.toJson(barAccesses);
                    System.out.println("barslist data : "+adapterbaraccessdata);
                }catch (Exception e){
                }
                    intent.putExtra("username", finalName);
                    intent.putExtra("useremail", finalEmail);
                    intent.putExtra("userrole", finalUserrole);
                    intent.putExtra("baraccess", adapterbaraccessdata);
                if(barAccesses.size()==0){
                    TempStore.getHolder().setBarAccess(null);

                }else{
                    TempStore.getHolder().setBarAccess(barAccesses);

                }
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(userlist.size()==0&& userlist==null){
            return 0;
        }
        return userlist.size();
    }
}
