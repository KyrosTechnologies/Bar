package com.kyros.technologies.bar.Inventory.Activity;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * Created by kyros on 26-06-2017.
 */

public class PercentDrawable extends Drawable {

    private final int percent;
    private final Paint paint;
    private final int Color;
    public PercentDrawable(int percent, int Color) {
        super();

        this.percent = percent;
        this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.Color=Color;
        this.paint.setColor(Color);
//        this.paint.setColor(0xffff0000);

    }

    @Override
    public void draw(Canvas canvas) {
        int left=0;
        int top=0;
        int right=canvas.getWidth();
        int bottom=(percent*canvas.getHeight()/100);
        Log.d("canvas_spec : ","left: "+left+", top : "+top+", right : "+right+", bottom : "+bottom);



        canvas.drawRect(left,top,right,bottom, paint);
    }

    @Override
    public void setAlpha(int i) {

    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }
}
