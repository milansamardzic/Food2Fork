package com.milansamardzic.food2fork;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Random;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener{

    private ListView lvMovies;
    private ReceptiAdapter adapterMovies;
    private Fork2FoodClient client;
    public static final String MOVIE_DETAIL_KEY = "recipes";
    String link="";

    private ActionBarDrawerToggle toggle;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private ListView leftDrawerList;
    private ArrayAdapter<String> navigationDrawerAdapter;
    private String[] leftSliderData = {"Rating", "Tranding", "Saved", "I have ...", "Contact Me",  "About"};



    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment selected;
        selected = new Rating();
        fragmentManager.beginTransaction().replace(R.id.mainContent, selected).commit();

        nitView();
        initDrawer();

    }

    int pos=0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if ((drawerLayout.isDrawerOpen(Gravity.LEFT)) && pos!= (-1) ) {
                drawerLayout.closeDrawer(Gravity.LEFT); return true;
            }else if (pos != 0) {
                android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                Fragment selected = new ContactMe();
                fragmentManager.beginTransaction().replace(R.id.mainContent, selected).commit();
                pos = 0; return true;
            } else if(pos == 0){
                finish();
            }
            return super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final android.support.v7.widget.SearchView searchView =
                (android.support.v7.widget.SearchView) menu.findItem(R.id.search111).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);

        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

               if (s.length() > 0) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    Fragment newFragment = new SearchFragment();

                    android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                    Fragment selected = new SearchFragment();

                    Bundle args = new Bundle();
                    args.putString("query_string", s);
                    selected.setArguments(args);

                    fragmentManager.beginTransaction().replace(R.id.mainContent, selected).commit();

                 //  link = s;
                //   fetchBoxOfficeMovies(link);


                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }


        });


        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                link = "";
                return true;
            }
        });

        return true;
    }

    int helpper=0;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.search111:
              onSearchRequested();
                return true;

            default:
                return false;
        }

    }


    private void nitView() {
        leftDrawerList = (ListView) findViewById(R.id.left_drawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationDrawerAdapter=new ArrayAdapter<String>( MainActivity.this, R.layout.simple_list_item_1, leftSliderData);
        leftDrawerList.setAdapter(navigationDrawerAdapter);
        leftDrawerList.setOnItemClickListener(this);
    }

    private void initDrawer() {

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        selectItem(position);

        for (int i = 0; i < parent.getChildCount(); i++){
            parent.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);}

        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        view.setBackgroundColor(color);
        drawerLayout.closeDrawers();

    }

    public void selectItem(int position) {
        leftDrawerList.setItemChecked(position, true);
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment selected;

        int pos = position;
        switch(position) {
            case 0:
                selected = new Rating();
                fragmentManager.beginTransaction().replace(R.id.mainContent, selected).commit();
                break;
            case 1:
                selected = new SortByTrand();
                fragmentManager.beginTransaction().replace(R.id.mainContent, selected).commit();
                break;
            case 2:
                selected = new SavedRec();
                fragmentManager.beginTransaction().replace(R.id.mainContent, selected).commit();
                break;
            case 3:
                selected = new Have();
                fragmentManager.beginTransaction().replace(R.id.mainContent, selected).commit();
                break;
            case 4:
                selected = new ContactMe();
                fragmentManager.beginTransaction().replace(R.id.mainContent, selected).commit();
                break;
            case 5:
                selected = new About();
                fragmentManager.beginTransaction().replace(R.id.mainContent, selected).commit();
                break;
        }
    }

}
