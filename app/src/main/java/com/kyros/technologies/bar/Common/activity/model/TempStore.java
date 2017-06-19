package com.kyros.technologies.bar.Common.activity.model;

import java.util.ArrayList;

/**
 * Created by Thirunavukkarasu on 19-06-2017.
 */

public class TempStore {
    private ArrayList<BarAccess> barAccess=new ArrayList<BarAccess>();
    public TempStore(){

    }

    public ArrayList<BarAccess> getBarAccess() {
        return barAccess;
    }

    public void setBarAccess(ArrayList<BarAccess> barAccess) {
        this.barAccess = barAccess;
    }
    public static TempStore holder=new TempStore();
    public static TempStore getHolder(){
        return holder;
    }
}
