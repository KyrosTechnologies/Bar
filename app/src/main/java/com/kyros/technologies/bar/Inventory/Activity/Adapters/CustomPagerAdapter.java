package com.kyros.technologies.bar.Inventory.Activity.Adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kyros.technologies.bar.Inventory.Activity.Activity.BottleDescriptionActivity;
import com.kyros.technologies.bar.Inventory.Activity.Activity.SectionBottlesActivity;
import com.kyros.technologies.bar.Inventory.Activity.PercentDrawable;
import com.kyros.technologies.bar.R;
import com.kyros.technologies.bar.ServiceHandler.ServiceHandler;
import com.kyros.technologies.bar.SharedPreferences.PreferenceManager;
import com.kyros.technologies.bar.utils.EndURL;
import com.kyros.technologies.bar.utils.UtilSectionBar;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Rohin on 07-06-2017.
 */

public class CustomPagerAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    private ArrayList<UtilSectionBar> bottleslist;
    private TextView edit_bottle,shots_count,liquor_names;
    private LinearLayout done;
    private EditText bottle_quan;
    private ImageView liquor_bottle_image,back;
    private LinearLayout plus,minus;
    private String Userprofileid;
    private float totalcount=0;
    private String Barid;
    private String Sectionid;
    private String liquorname;
    private String shots;
    private String capacity;
    private String pictureurl;
    private double minval;
    private double maxval;
    private String totalbottles;
    private SeekBar mySeekBar;
    private   float fintotalcount;
    private PreferenceManager store;
    private FrameLayout frame_fill_background;
    private int positionvalue=0;
    private double valuemin=0;
    private double valuemax=0;
    public LinearLayout linear_child_view;


    public CustomPagerAdapter(Context context,ArrayList<UtilSectionBar> bottleslist,int position) {
        mContext = context;
        this.bottleslist=bottleslist;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.positionvalue=position;
    }



    @Override
    public int getCount() {
        return bottleslist.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        final View itemView = mLayoutInflater.inflate(R.layout.image_slide, container, false);
        int lastposition=positionvalue+position;
        if(lastposition>=bottleslist.size()){
            lastposition=lastposition-bottleslist.size();
        }
        Log.d("Values  position : "," "+position);

        UtilSectionBar utilSectionBar =bottleslist.get(position);
        final int  sectionid= utilSectionBar.getSectionid();
        final int barid= utilSectionBar.getBarid();
        String liquorname=utilSectionBar.getLiquorname();
        final int userprofileid=utilSectionBar.getUserprofileid();
        final String liquorcapacity=utilSectionBar.getLiquorcapacity();
        String shots= utilSectionBar.getShots();
        final String category=utilSectionBar.getCategory();
        final String subcategory=utilSectionBar.getSubcategory();
        final String parlevel=utilSectionBar.getParlevel();
        final String disname=utilSectionBar.getDistributorname();
        final String price=utilSectionBar.getPriceunit();
        final String binnumber=utilSectionBar.getBinnumber();
        final String productcode=utilSectionBar.getProductcode();
        String createdon=utilSectionBar.getCreatedon();
        final String pictureurl= utilSectionBar.getPictureurl();
        final double minval=utilSectionBar.getMinvalue();
        final double maxval=utilSectionBar.getMaxvalue();
      final  String totalbottles=utilSectionBar.getTotalbottles();
        final int BottleId=utilSectionBar.getBottleId();
        final String type=utilSectionBar.getType();
         valuemin=minval*100;
         valuemax=maxval*100;
        Log.d("valueminmultiplied",""+valuemin);

        store= PreferenceManager.getInstance(mContext.getApplicationContext());
        Userprofileid=store.getUserProfileId();
        Barid=store.getBarId();
        Sectionid=store.getSectionId();
        if(shots==null){
            shots="";
        }
//        if (totalbottles==null){
//            totalbottles="";
//        }

        if (liquorname==null){
            liquorname="";
        }

        edit_bottle=(TextView)itemView.findViewById(R.id.edit_bottle);
        bottle_quan=(EditText)itemView.findViewById(R.id.bottle_quan);
        bottle_quan.setTag(utilSectionBar);
        minus=(LinearLayout) itemView.findViewById(R.id.minus);
        minus.setTag(utilSectionBar);
        plus=(LinearLayout) itemView.findViewById(R.id.plus);
        plus.setTag(utilSectionBar);
        liquor_bottle_image=(ImageView)itemView.findViewById(R.id.liquor_bottle_image);

        shots_count=(TextView)itemView.findViewById(R.id.shots_count);
        mySeekBar=(SeekBar)itemView.findViewById(R.id.mySeekBar);
        linear_child_view=(LinearLayout)itemView.findViewById(R.id.linear_child_view);
        mySeekBar.setThumbOffset(20);
        mySeekBar.setTag(utilSectionBar);
        done=(LinearLayout)itemView.findViewById(R.id.done);
        liquor_names=(TextView)itemView.findViewById(R.id.liquor_names);
        back=(ImageView)itemView.findViewById(R.id.back);
        frame_fill_background=(FrameLayout)itemView.findViewById(R.id.frame_fill_background);
        frame_fill_background.setTag(utilSectionBar);

        try {
            liquor_names.setText(liquorname);
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            Picasso.with(mContext)
                    .load(pictureurl)
                    .resize(279, 320)
                    .into(liquor_bottle_image);
        }catch (Exception eq){
            eq.printStackTrace();
        }
        try {
            shots_count.setText(shots);

        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            Log.d("bottles",totalbottles);
            String value=bottleslist.get(position).getTotalbottles();
            Log.d("Values  original : "," "+value);

            if(value!=null){
                if(value.equals("")){
                    value="0.0";
                }
                ((EditText) itemView.findViewById(R.id.bottle_quan)).setText(value);
                String numberD = String.valueOf(value);
                numberD = numberD.substring ( numberD.indexOf ( "." ) );
                String valuedummy=numberD;
                Log.d("Values  valuedummy : "," "+valuedummy);

                float progressfloat=Float.valueOf(valuedummy);
                progressfloat=100*progressfloat;
                int valueint=(int)progressfloat;
                Log.d("Values integer : "," "+valueint);

//                mySeekBar.setThumbOffset(valueint);
//
//                ((FrameLayout) itemView.findViewById(R.id.frame_fill_background)).setBackground(new PercentDrawable(valueint,mContext.getResources().getColor(R.color.colorPrimaryDark)));



            }else{
                ((EditText) itemView.findViewById(R.id.bottle_quan)).setText("0.0");

            }


            Log.d("value input edit text",value);
        }catch (Exception e){
            e.printStackTrace();
        }
        final String finalLiquorname1 = liquorname;
        final String finalShots = shots;
        final String finalTotalbottles = totalbottles;
        edit_bottle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (type){
                    case "bottle":
                        Intent i=new Intent(mContext,BottleDescriptionActivity.class);
                        i.putExtra("sectionid",sectionid);
                        i.putExtra("barid",barid);
                        i.putExtra("bottleid",BottleId);
                        i.putExtra("liquorname", finalLiquorname1);
                        i.putExtra("liquorcapacity",liquorcapacity);
                        i.putExtra("shots", finalShots);
                        i.putExtra("category",category);
                        i.putExtra("subcategory",subcategory);
                        i.putExtra("parvalue",parlevel);
                        i.putExtra("distributorname",disname);
                        i.putExtra("price",price);
                        i.putExtra("binnumber",binnumber);
                        i.putExtra("productcode",productcode);
                        i.putExtra("minvalue",String.valueOf(minval));
                        i.putExtra("maxvalue",String.valueOf(maxval));
                        i.putExtra("totalbottles", finalTotalbottles);
                        i.putExtra("whichtype","update");
                        i.putExtra("image",pictureurl);
                        mContext.startActivity(i);
                        break;
                    case "keg":
                        break;
                }

            }
        });

        final String finalLiquorname = liquorname;
        done.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LiquorSliderapi(String.valueOf(userprofileid),Float.parseFloat( ((EditText) itemView.findViewById(R.id.bottle_quan)).getText().toString()),String.valueOf(barid),String.valueOf(sectionid), finalLiquorname);

        }
    });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalcount++;
               int child_count= plus.getChildCount();

                  //  bottle_quan.setText(""+add);
                String vlu=  ((EditText) itemView.findViewById(R.id.bottle_quan)).getText().toString();
                if(vlu!=null &&!vlu.isEmpty()){
                    float vl=Float.parseFloat(vlu);
                    totalcount=vl+1;
                    String add=Float.toString(totalcount);
                    ((EditText) itemView.findViewById(R.id.bottle_quan)).setText(add);

                }else{
                    String add=Float.toString(totalcount);

                    ((EditText) itemView.findViewById(R.id.bottle_quan)).setText(add);

                }

                CustomPagerAdapter.super.notifyDataSetChanged();

            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalcount!=0){
                    totalcount--;
                    String vlu=  ((EditText) itemView.findViewById(R.id.bottle_quan)).getText().toString();

                    if(vlu!=null &&!vlu.isEmpty()){
                        float vl=Float.parseFloat(vlu);
                        totalcount=vl-1;
                        String add=Float.toString(totalcount);
                        ((EditText) itemView.findViewById(R.id.bottle_quan)).setText(add);

                    }else{
                        String add=Float.toString(totalcount);

                        ((EditText) itemView.findViewById(R.id.bottle_quan)).setText(add);

                    }



                }
            }
        });
        ((SeekBar) itemView.findViewById(R.id.mySeekBar)).setProgress((int) valuemin);
        ((SeekBar) itemView.findViewById(R.id.mySeekBar)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float progress1= (float)progress/100;
                if((int)valuemin>progress){

                    ((SeekBar) itemView.findViewById(R.id.mySeekBar)).setProgress((int) valuemin);
                }
                if((int)valuemax<progress){
                    ((SeekBar) itemView.findViewById(R.id.mySeekBar)).setProgress((int)valuemax);
                }
                ((FrameLayout) itemView.findViewById(R.id.frame_fill_background)).setBackground(new PercentDrawable(progress,mContext.getResources().getColor(R.color.colorPrimaryDark)));

                int hariavg=(int)valuemax-(int)valuemin;
                int hariminusvalue=progress-(int)valuemin;
                float haridivalue=(float)hariminusvalue/(float)hariavg;
                float harihundredpercentage=haridivalue*100;
                Log.d("harivalue: ","hariavg"+hariavg+" / "+"hariminusvalue : "+hariminusvalue+" / "+"haridivalue ;"+haridivalue+" / "+"harihundredpercentagge ; "+harihundredpercentage);
              if(harihundredpercentage>=100){
                  ((EditText) itemView.findViewById(R.id.bottle_quan)).setText(Float.toString(1.0f));

              }else if(harihundredpercentage<1){
                  ((EditText) itemView.findViewById(R.id.bottle_quan)).setText(Float.toString(0.0f));


              }else{
                  String vlu=  ((EditText) itemView.findViewById(R.id.bottle_quan)).getText().toString();
                    if(vlu!=null &&!vlu.isEmpty()){
                        float vl=Float.parseFloat(vlu);
                        vl=vl+haridivalue;
                        ((EditText) itemView.findViewById(R.id.bottle_quan)).setText(Float.toString(haridivalue));

                    }

              }



                fintotalcount=(totalcount+progress1)-(int)valuemin;


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
    private void LiquorSliderapi(final String userprofileid, float totalbottle, String barid, String sectionid,  String liquorname) {
        String tag_json_obj = "json_obj_req";
        String url = EndURL.URL+"InsertUserTotalBottle";
        //  String url = "http://192.168.0.109:8080/Bar/rest/getLiquorList";
        Log.d("waggonurl", url);
        JSONObject inputLogin=new JSONObject();
        try{
            inputLogin.put("UserProfileId",userprofileid);
            inputLogin.put("TotalBottles",String.valueOf(totalbottle));
            inputLogin.put("BarId",barid);
            inputLogin.put("SectionId",sectionid);
            inputLogin.put("LiquorName",liquorname);


        }catch (Exception e){
            e.printStackTrace();
        }
        Log.d("inputJsonuser",inputLogin.toString());
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.PUT, url, inputLogin, new Response.Listener<JSONObject>() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onResponse(JSONObject response) {
                Log.d("List Response",response.toString());
                try {

                    JSONObject obj=new JSONObject(response.toString());
                    String message=obj.getString("Message");
                    boolean success=obj.getBoolean("IsSuccess");
                    if (success){

                        Toast.makeText(mContext.getApplicationContext(),"Successfully Updated",Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(mContext,SectionBottlesActivity.class);
                        mContext.startActivity(i);

                    }else {
                        Toast.makeText(mContext.getApplicationContext(),message,Toast.LENGTH_SHORT).show();

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(mContext.getApplicationContext(),"Not Working",Toast.LENGTH_SHORT).show();
                if(error!=null){
                    Toast.makeText(mContext.getApplicationContext(),"Error: "+error.toString(),Toast.LENGTH_SHORT).show();
                }
//                texts.setText(error.toString());
            }
        }) {

        };
        objectRequest.setRetryPolicy(new DefaultRetryPolicy(
                20*1000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ServiceHandler.getInstance().addToRequestQueue(objectRequest, tag_json_obj);

    }


}