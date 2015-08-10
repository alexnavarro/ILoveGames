package br.com.alexandrenavarro.ilovegames.util;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import br.com.alexandrenavarro.ilovegames.R;

/**
 * Created by alexandrenavarro on 8/9/15.
 */
public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {
    public static String TAG = EndlessRecyclerOnScrollListener.class.getSimpleName();

    private int previousTotal = 0; // The total number of items in the dataset after the last load
    private boolean loading = true; // True if we are still waiting for the last set of data to load.
    private int visibleThreshold = 5; // The minimum amount of items to have below your current scroll position before loading more.
    int firstVisibleItem, visibleItemCount, totalItemCount;

    private int current_page = 1;

    private Context context;
    private RecyclerView.LayoutManager mLayoutManaer;

    public EndlessRecyclerOnScrollListener(Context context, RecyclerView.LayoutManager layoutManager) {
        this.context = context;
        this.mLayoutManaer = layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        if (mLayoutManaer instanceof LinearLayoutManager) {
            visibleItemCount = recyclerView.getChildCount();
            totalItemCount = mLayoutManaer.getItemCount();
            firstVisibleItem = ((LinearLayoutManager) mLayoutManaer).findFirstVisibleItemPosition();


            if (loading) {
                if (totalItemCount > previousTotal) {
                    loading = false;
                    previousTotal = totalItemCount;
                }
            }
            if (!loading && (totalItemCount - visibleItemCount)
                    <= (firstVisibleItem + visibleThreshold)) {
                // End has been reached

                // Do something
                current_page++;

                onLoadMore(current_page);

                loading = true;
            }

        } else {
            int grid_column_count = context.getResources().getInteger(R.integer.grid_columm);
            StaggeredGridLayoutManager mLayoutManager = (StaggeredGridLayoutManager) mLayoutManaer;
            int visibleItemCount = recyclerView.getChildCount();
            int totalItemCount = mLayoutManager.getItemCount();
            int firstVisibleItemsGrid[] = new int[grid_column_count];
            int firstVisibleItem = mLayoutManager.findFirstVisibleItemPositions(firstVisibleItemsGrid)[0];

            if ((visibleItemCount + firstVisibleItem) >= totalItemCount
                    && totalItemCount != 0) {
                onLoadMore(current_page);
            }
        }
    }

    public abstract void onLoadMore(int current_page);
}
