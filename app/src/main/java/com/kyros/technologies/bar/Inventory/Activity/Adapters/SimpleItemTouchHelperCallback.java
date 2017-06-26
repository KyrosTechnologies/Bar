package com.kyros.technologies.bar.Inventory.Activity.Adapters;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

/**
 * Created by Thirunavukkarasu on 23-06-2017.
 */

public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {
  private final ItemTouchHelperAdapter mAdapter;
    public static final int RIGHT=1000;
    public static final int LEFT=1001;
    public static final int UP=1100;
    public static final int DOWN=1101;
    public SimpleItemTouchHelperCallback(ItemTouchHelperAdapter adapter){
        mAdapter=adapter;
    }
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);

    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        mAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        switch (direction){
            case LEFT:
                Log.d("value :","LEFT");
                break;
            case RIGHT:
                Log.d("value :","RIGHT");
                break;
            case UP:
                Log.d("value :","UP");
                break;
            case DOWN:
                Log.d("value :","DOWN");
                break;

        }
        if(direction==LEFT){
            mAdapter.swipeToDelete(viewHolder.getAdapterPosition());

        }else{
            mAdapter.onItemDismiss(viewHolder.getAdapterPosition());

        }
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            BarAdapter.MyViewHolderEleven itemViewHolder = (BarAdapter.MyViewHolderEleven) viewHolder;
            itemViewHolder.onItemSelected();
        }

        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);

        BarAdapter.MyViewHolderEleven itemViewHolder = (BarAdapter.MyViewHolderEleven) viewHolder;
        itemViewHolder.onItemClear();
    }
}
