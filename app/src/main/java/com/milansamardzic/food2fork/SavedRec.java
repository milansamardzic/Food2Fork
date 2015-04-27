package com.milansamardzic.food2fork;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ms on 1/22/15.
 */
public class SavedRec extends android.support.v4.app.Fragment {

    private ListView lvMovies;
    private AdapterSaved mySavedAdapter;
    private Fork2FoodClient client;
    public static final String MOVIE_DETAIL_KEY = "recipes";
    public String readSaved;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.tranding, container, false);

        //----- listView
        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getActivity().getApplicationContext(), "Refreshing...", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadSaved();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }

        });

        mSwipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);

        //SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("SAVE_R", 0);
        //SharedPreferences.Editor editor = pref.edit();

        ArrayList<Detalji> aMovies = new ArrayList<Detalji>();
        mySavedAdapter = new AdapterSaved(getActivity(), aMovies);

        GridView gridview = (GridView) rootView.findViewById(R.id.gridview);

        loadSaved();

        gridview.setAdapter(mySavedAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent i = new Intent(getActivity(), DetailAct.class);
                // i.putExtra(MOVIE_DETAIL_KEY, adapterMovies.getItem(position));
                i.putExtra("POSITION_RID", mySavedAdapter.getItem(position).getrId());
                i.putExtra("POSITION", position);
                startActivity(i);
            }
        });


        return rootView;
    }

    private void loadSaved() {
        TinyDB tinydb = new TinyDB(getActivity());
        ArrayList check = (tinydb.getList("MySaved"));
        mySavedAdapter.clear();
        JSONObject jsonObject = null;
        JSONObject items = null;
        for (int i=0; i<check.size(); i++)
        {
            try {
                jsonObject = new JSONObject(String.valueOf(check.get(i)));
                items = jsonObject.getJSONObject("recipe");
                Detalji det = Detalji.fetchDetalje(items);
                mySavedAdapter.addAll(det);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
