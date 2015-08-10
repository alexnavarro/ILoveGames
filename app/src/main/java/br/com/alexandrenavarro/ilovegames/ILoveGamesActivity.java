package br.com.alexandrenavarro.ilovegames;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;

import br.com.alexandrenavarro.ilovegames.model.TopGames;
import br.com.alexandrenavarro.ilovegames.model.TopGamesResult;
import br.com.alexandrenavarro.ilovegames.network.HttpRequest;
import br.com.alexandrenavarro.ilovegames.util.EndlessRecyclerOnScrollListener;

public class ILoveGamesActivity extends AppCompatActivity {
    private static final String ACTION_MODE = "ACTION_MODE";
    private static final String RESULT = "RESULT";
    private static final String TOP_GAMES = "TOP_GAMES";
    private static final String TAG = "ILoveGamesActivity";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private StaggeredGridLayoutManager mLayoutManager;
    private boolean isShowingListMode = true;
    private TopGamesResult mTopGamesResult;
    private ArrayList<TopGames> mTopGames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ilove_games);


        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        if (savedInstanceState != null) {
            isShowingListMode = savedInstanceState.getBoolean(ACTION_MODE, true);
            mTopGamesResult = (TopGamesResult) savedInstanceState.getSerializable(RESULT);
            mTopGames.clear();
            mTopGames.addAll((ArrayList<TopGames>) savedInstanceState.getSerializable(TOP_GAMES));
        }

        defineLayoutManagerMode();
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new TopGamesAdapter(this, mTopGames, isShowingListMode);
        mRecyclerView.setAdapter(mAdapter);
        configScrollListener();


        String url = "https://api.twitch.tv/kraken/games/top";

        if (savedInstanceState == null) {
            new HttpRequest<TopGamesResult>().get(this, url, TopGamesResult.class, new Response.Listener<TopGamesResult>() {
                @Override
                public void onResponse(TopGamesResult result) {
                    mTopGamesResult = result;
                    if (mAdapter != null) {
                        ((TopGamesAdapter) mAdapter).addAll(result.getTop());
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i(TAG, "That didn't work!");
                }
            });
        }

    }

    private void configScrollListener() {
        mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(this, mRecyclerView.getLayoutManager()) {

            @Override
            public void onLoadMore(int current_page) {
                if(mTopGames.size() >=50){
                    return;
                }
                Log.i("loadMore", "url" + mTopGamesResult.get_links().getNext());
                new HttpRequest<TopGamesResult>().get(ILoveGamesActivity.this, mTopGamesResult.get_links().getNext(), TopGamesResult.class, new Response.Listener<TopGamesResult>() {
                    @Override
                    public void onResponse(TopGamesResult result) {
                        mTopGamesResult = result;
                        if (mAdapter != null) {
                            ((TopGamesAdapter) mAdapter).addAll(result.getTop());
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i(TAG, "That didn't work!");
                    }
                });
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(ACTION_MODE, isShowingListMode);
        outState.putSerializable(RESULT, mTopGamesResult);
        outState.putSerializable(TOP_GAMES, mTopGames);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ilove_games, menu);
        MenuItem item = menu.getItem(0);
        item.setTitle(isShowingListMode ? "Grid" : "List");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.change_view_mode) {
            isShowingListMode = !isShowingListMode;
            ((TopGamesAdapter) mAdapter).changeModeView(isShowingListMode);
            defineLayoutManagerMode();
            item.setTitle(isShowingListMode ? "Grid" : "List");
            configScrollListener();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void defineLayoutManagerMode() {
        if (isShowingListMode) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(getResources().getInteger(R.integer.grid_columm), StaggeredGridLayoutManager.VERTICAL));
        }

    }

}