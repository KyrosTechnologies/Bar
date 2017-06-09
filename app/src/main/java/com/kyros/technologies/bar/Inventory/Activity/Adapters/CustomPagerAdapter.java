package com.kyros.technologies.bar.Inventory.Activity.Adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
    private TextView edit_bottle,shots_count,done,liquor_names;
    private EditText bottle_quan;
    private ImageView plus,minus,liquor_bottle_image,back;
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
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.image_slide, container, false);
        int lastposition=positionvalue+position;
        if(lastposition>=bottleslist.size()){
            lastposition=lastposition-bottleslist.size();
        }
        UtilSectionBar utilSectionBar =bottleslist.get(lastposition);
        final int  sectionid= utilSectionBar.getSectionid();
        final int barid= utilSectionBar.getBarid();
        String liquorname=utilSectionBar.getLiquorname();
        final int userprofileid=utilSectionBar.getUserprofileid();
        String liquorcapacity=utilSectionBar.getLiquorcapacity();
        String shots= utilSectionBar.getShots();
        String category=utilSectionBar.getCategory();
        String subcategory=utilSectionBar.getSubcategory();
        String parlevel=utilSectionBar.getParlevel();
        String disname=utilSectionBar.getDistributorname();
        String price=utilSectionBar.getPriceunit();
        final String binnumber=utilSectionBar.getBinnumber();
        String productcode=utilSectionBar.getProductcode();
        String createdon=utilSectionBar.getCreatedon();
        String pictureurl= utilSectionBar.getPictureurl();
        double minval=utilSectionBar.getMinvalue();
        double maxval=utilSectionBar.getMaxvalue();
        String totalbottles=utilSectionBar.getTotalbottles();
         valuemin=minval*1000000;
         valuemax=maxval*1000000;
        Log.d("valueminmultiplied",""+valuemin);
//        if(totalbottles!=null &&!totalbottles.isEmpty()){
//            totalcount=Float.parseFloat(totalbottles);
//        }
        store= PreferenceManager.getInstance(mContext.getApplicationContext());
        Userprofileid=store.getUserProfileId();
        Barid=store.getBarId();
        Sectionid=store.getSectionId();
        if(shots==null){
            shots="";
        }
        if (totalbottles==null){
            totalbottles="";
        }

        if (liquorname==null){
            liquorname="";
        }

        edit_bottle=(TextView)itemView.findViewById(R.id.edit_bottle);
        bottle_quan=(EditText)itemView.findViewById(R.id.bottle_quan);
        minus=(ImageView)itemView.findViewById(R.id.minus);
        plus=(ImageView)itemView.findViewById(R.id.plus);
        liquor_bottle_image=(ImageView)itemView.findViewById(R.id.liquor_bottle_image);
        shots_count=(TextView)itemView.findViewById(R.id.shots_count);
        mySeekBar=(SeekBar)itemView.findViewById(R.id.mySeekBar);
        done=(TextView)itemView.findViewById(R.id.done);
        liquor_names=(TextView)itemView.findViewById(R.id.liquor_names);
        back=(ImageView)itemView.findViewById(R.id.back);
        frame_fill_background=(FrameLayout)itemView.findViewById(R.id.frame_fill_background);

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
            String value=totalbottles;
            bottle_quan.setText(value);
        }catch (Exception e){
            e.printStackTrace();
        }
        edit_bottle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(mContext,BottleDescriptionActivity.class);
                mContext.startActivity(i);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(mContext,SectionBottlesActivity.class);
                mContext.startActivity(i);
            }
        });

        final String finalLiquorname = liquorname;
        done.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LiquorSliderapi(String.valueOf(userprofileid),fintotalcount,String.valueOf(barid),String.valueOf(sectionid), finalLiquorname);

        }
    });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalcount++;
                String add=String.valueOf(totalcount);
                bottle_quan.setText(add);
                Toast.makeText(mContext.getApplicationContext(),"Plus Clicked"+String.valueOf(totalcount),Toast.LENGTH_SHORT).show();

            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalcount!=0){
                    totalcount--;
                    bottle_quan.setText(String.valueOf(totalcount));
                    Toast.makeText(mContext.getApplicationContext(),"Minus Clicked",Toast.LENGTH_SHORT).show();
                }
            }
        });
        mySeekBar.setProgress((int) valuemin);
//        mySeekBar.setMax((int)valuemax);

        mySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float progress1= (float)progress/100;
//                Toast.makeText(mContext.getApplicationContext(),"range is : "+progress+" / converted : "+progress1+" / total count: "+totalcount,Toast.LENGTH_SHORT).show();
                if((int)valuemin>progress){
                    mySeekBar.setProgress((int) valuemin);
                }
                if((int)valuemax<progress){
                    mySeekBar.setProgress((int)valuemax);
                }
                int rcount=100-(int)valuemax;
                float rscount=rcount/(float)100;

//                totalcount=totalcount+progress1;
                fintotalcount=totalcount+progress1+rscount;
                Log.d("final rscount",""+rscount+" /rcount "+rcount);
             if(fintotalcount<=1.0){
                 bottle_quan.setText(String.valueOf(fintotalcount));
             }
                try{
                    int finalprogress=100-progress;
                    int finalvalue=8*finalprogress;
                    Log.d("final value ", "final  value of y: "+finalvalue);
                    RelativeLayout.LayoutParams parms = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,finalvalue);
                    parms.setMargins(25,0,25,0);
                    parms.setLayoutDirection(Gravity.BOTTOM);
                    frame_fill_background.setLayoutParams(parms);
                    frame_fill_background.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                }catch (Exception e){
                    e.printStackTrace();
                }
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
            inputLogin.put("userprofileid",userprofileid);
            inputLogin.put("totalbottles",String.valueOf(totalbottle));
            inputLogin.put("barid",barid);
            inputLogin.put("sectionid",sectionid);
            inputLogin.put("liquorname",liquorname);


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
                    String message=obj.getString("message");
                    boolean success=obj.getBoolean("issuccess");
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