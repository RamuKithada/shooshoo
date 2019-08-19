package com.android.shooshoo.utils;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class PaginationScrollListener extends RecyclerView.OnScrollListener {

    LinearLayoutManager layoutManager;

    public PaginationScrollListener(LinearLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

        if (!isLoading() && !isLastPage()) {
            if ((visibleItemCount + firstVisibleItemPosition) >=
                    totalItemCount && firstVisibleItemPosition >= 0) {
                loadMoreItems();
            }
        }
    }

    /**
     * Load more item here i.e, service call
     */
    protected abstract void loadMoreItems();

    /**
     *
     * @return total pages count 
      */
    public abstract int getTotalPageCount();

    /** Set the item is last one or not
     *
     * @return true if it is last item
     */
    public abstract boolean isLastPage();

    /**  used to check service is started or not for next page
     *
     * @return true if data is loading for next item
     */
    public abstract boolean isLoading();
}