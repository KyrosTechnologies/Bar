package com.kyros.technologies.bar.Inventory.Activity.Adapters;

/**
 * Created by Thirunavukkarasu on 09-06-2017.
 */

public interface ItemTouchHelperAdapter {
    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);

}
