package br.com.alexandrenavarro.ilovegames;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.alexandrenavarro.ilovegames.model.TopGames;
import br.com.alexandrenavarro.ilovegames.util.ChooseBestURLImageUtil;

/**
 * Created by alexandrenavarro on 8/9/15.
 */
public class TopGamesAdapter extends RecyclerView.Adapter<TopGamesAdapter.ViewHolder> {

    private final List<TopGames> topGames;
    private Context context;
    public static final int TYPE_LIST = 0;
    public static final int TYPE_GRID = 1;
    private boolean isShowingListMode;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public ImageView mImageView;

        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.tv_name);
            mImageView = (ImageView) v.findViewById(R.id.img_game);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TopGamesAdapter(Context context, List<TopGames> topGames, boolean isShowingListMode) {
        this.isShowingListMode = isShowingListMode;
        this.topGames = topGames;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TopGamesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(viewType == TYPE_LIST ? R.layout.list_row : R.layout.grid_row, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public int getItemViewType(int position) {
        return isShowingListMode ? TYPE_LIST : TYPE_GRID;
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        TopGames topGames = this.topGames.get(position);
        holder.mTextView.setText(topGames.getGame().getName());
        Picasso.with(context).load(ChooseBestURLImageUtil.choose(context, topGames.getGame().getLogo())).into(holder.mImageView);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return topGames.size();
    }


    public void addAll(List<TopGames> topGames) {
        this.topGames.addAll(topGames);
        notifyDataSetChanged();
    }

    public void changeModeView(boolean isShowingListMode) {
        this.isShowingListMode = isShowingListMode;
        notifyDataSetChanged();
    }
}
