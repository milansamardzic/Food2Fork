package com.milansamardzic.ohmymeal;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ms on 1/22/15.
 */
public class SearchFragment extends Fragment {
    private ListView lvMovies;
    private ReceptiAdapter adapterMovies;
    private Fork2FoodClient client;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final String strtext = getArguments().getString("query_string");

        final View rootView = inflater.inflate(R.layout.activity_main, container, false);
        lvMovies = (ListView) rootView.findViewById(R.id.lvRecepti);

        View toolbar = rootView.findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);

        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.activity_main_swipe_refresh_layout);
        //---- animation

        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(
                getActivity(), R.anim.list_layout_controller);

        //----- listView

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getActivity(), "Refreshing...", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fetchBoxOfficeMovies(strtext);
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }

        });

  Log.d("query_string", "str " + strtext);


        ArrayList<Recept> aMovies = new ArrayList<Recept>();
        adapterMovies = new ReceptiAdapter(getActivity(), aMovies);
        lvMovies.setAdapter(adapterMovies);
        // Fetch the data remotely
        fetchBoxOfficeMovies(strtext);
        setupMovieSelectedListener();
        lvMovies.setLayoutAnimation(controller);
        return rootView;
    }

    public void fetchBoxOfficeMovies(String link) {

        adapterMovies.clear();
        client = new Fork2FoodClient();
        client.getRecept(new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject body) {
                JSONArray items = null;
                try {
                    // Get the movies json array
                    items = body.getJSONArray("recipes");
                    // Parse json array into array of model objects
                    ArrayList<Recept> movies = Recept.fromJson(items);
                    Log.d("sve", String.valueOf(movies));
                    // Load model objects into the adapter which displays them
                    adapterMovies.addAll(movies);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, link);

    }

    public void setupMovieSelectedListener() {

        lvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View item, int position, long rowId) {

                Intent i = new Intent(getActivity(), DetailAct.class);
                // i.putExtra(MOVIE_DETAIL_KEY, adapterMovies.getItem(position));
                i.putExtra("POSITION_RID", adapterMovies.getItem(position).getrId());
                i.putExtra("POSITION", position);
                startActivity(i);
            }
        });
    }


}
