package com.kyros.technologies.bar.List;

/**
 * Created by Rohin on 04-05-2017.
 */

public class InventoryLists {
    private int image;
    private String name;

    public InventoryLists(){


    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static InventoryLists holder=new InventoryLists();
    public static InventoryLists getHolder(){
        return holder;
    }
}
