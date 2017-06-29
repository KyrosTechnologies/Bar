package com.kyros.technologies.bar.Common.activity.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kyros.technologies.bar.R;
import com.kyros.technologies.bar.ServiceHandler.SessionManager;

/**
 * Created by Rohin on 29-06-2017.
 */

public class TileActivity extends AppCompatActivity {
    private LinearLayout dotsLayout;
    private int[] layouts;
    private SessionManager prefManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefManager = new SessionManager(this);
        if (!prefManager.isFirstTimeLaunch()) {

            launchHomeScreen();
            finish();
        }

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_tile);

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager_tile);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots_tile);
        Button getstarted = (Button) findViewById(R.id.get_started);
        layouts = new int[]{
                R.layout.tile_1,
                R.layout.tile_2,
                R.layout.tile_3};
        addBottomDots(0);
        changeStatusBarColor();

        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);


        getstarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    launchHomeScreen();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });



    }
    private final ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);


        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }
    private void addBottomDots(int currentPage) {
        TextView[] dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_tile_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_tile_inactive);
        try{
            dotsLayout.removeAllViews();
        }catch (Exception e){
            e.printStackTrace();
        }
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dots[i].setPadding(0,0,2,0);
            try{
                dotsLayout.addView(dots[i]);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private void launchHomeScreen() {

            prefManager.setIsFirstTimeLaunch(false);
            Intent m=new Intent(TileActivity.this, SplashActivity.class);
            m.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(m);
            finish();

    }
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}
