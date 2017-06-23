package com.kyros.technologies.bar.Inventory.Activity.interfacesmodel;

/**
 * Created by Thirunavukkarasu on 23-06-2017.
 */

public interface ItemTouchHelperAdapter {
    void onItemMove(int fromPosition , int toPosition);
    void onItemDismiss(int position);
}
