package com.milansamardzic.food2fork;

/**
 * Created by ms on 1/15/15.
 */

/*
public class ActivityM extends ActionBarActivity implements OnScrollChangedCallback {

    private Toolbar mToolbar;
    private Drawable mActionBarBackgroundDrawable;
    private View mHeader;
    private int mLastDampedScroll;
    private int mInitialStatusBarColor;
    private int mFinalStatusBarColor;
    private SystemBarTintManager mStatusBarManager;

    private ListView lvMovies;
    private ReceptiAdapter adapterMovies;
    private Fork2FoodClient client;
    public static final String MOVIE_DETAIL_KEY = "recipes";
    String link="";
    int scrolly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        lvMovies = (ListView) findViewById(R.id.lvRecepti);
        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(
                this, R.anim.list_layout_controller);

        ArrayList<Recept> aMovies = new ArrayList<Recept>();
        adapterMovies = new ReceptiAdapter(this, aMovies);
        lvMovies.setAdapter(adapterMovies);
        // Fetch the data remotely
        fetchBoxOfficeMovies(link);
        setupMovieSelectedListener();
        lvMovies.setLayoutAnimation(controller);
        final int[] aaa = new int[1];

        lvMovies.setOnScrollListener( new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                int index = lvMovies.getFirstVisiblePosition();
                View v = lvMovies.getChildAt(0);
                int top = (v == null) ? 0 : (v.getTop() - lvMovies.getPaddingTop());
                lvMovies.setSelectionFromTop(index, top);

                int l= -1; int scrollPosition = 0;
                int headerHeight = mHeader.getHeight() - mToolbar.getHeight();
                float ratio = 0;
                if (index > 0 && headerHeight > 0)
                    ratio = (float) Math.min(Math.max(index, 0), headerHeight) / headerHeight;

                updateActionBarTransparency(ratio);
                updateStatusBarColor(ratio);
                updateParallaxEffect(index);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {


                if (0 == firstVisibleItem){

                    mToolbar = (Toolbar) findViewById(R.id.toolbar);
                    mActionBarBackgroundDrawable = mToolbar.getBackground();
                    setSupportActionBar(mToolbar);
                    mInitialStatusBarColor = Color.BLACK;
                    mFinalStatusBarColor = getResources().getColor(R.color.primary_dark);

                    mHeader = findViewById(R.id.header);


                }



            }
        });

        //---


        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mActionBarBackgroundDrawable = mToolbar.getBackground();
        setSupportActionBar(mToolbar);
        mStatusBarManager = new SystemBarTintManager(this);
        mStatusBarManager.setStatusBarTintEnabled(true);
        mInitialStatusBarColor = Color.BLACK;
        mFinalStatusBarColor = getResources().getColor(R.color.primary_dark);

        mHeader = findViewById(R.id.header);

     //   ObservableScrollable scrollView = (ObservableScrollable) findViewById(R.id.scrollview);
       // scrollView.setOnScrollChangedCallback(this);

        // onScroll(-1, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onScroll(int l, int scrollPosition) {
        int headerHeight = mHeader.getHeight() - mToolbar.getHeight();
        float ratio = 0;
        if (scrollPosition > 0 && headerHeight > 0)
            ratio = (float) Math.min(Math.max(scrollPosition, 0), headerHeight) / headerHeight;

        updateActionBarTransparency(ratio);
        updateStatusBarColor(ratio);
        updateParallaxEffect(scrollPosition);
    }

    private void updateActionBarTransparency(float scrollRatio) {
        int newAlpha = (int) (scrollRatio * 255);
        mActionBarBackgroundDrawable.setAlpha(newAlpha);
        mToolbar.setBackground(mActionBarBackgroundDrawable);
    }

    private void updateStatusBarColor(float scrollRatio) {
        int r = interpolate(Color.red(mInitialStatusBarColor), Color.red(mFinalStatusBarColor), 1 - scrollRatio);
        int g = interpolate(Color.green(mInitialStatusBarColor), Color.green(mFinalStatusBarColor), 1 - scrollRatio);
        int b = interpolate(Color.blue(mInitialStatusBarColor), Color.blue(mFinalStatusBarColor), 1 - scrollRatio);
        mStatusBarManager.setTintColor(Color.rgb(r, g, b));
    }

    private void updateParallaxEffect(int scrollPosition) {
        float damping = 0.5f;
        int dampedScroll = (int) (scrollPosition * damping);
        int offset = mLastDampedScroll - dampedScroll;
        mHeader.offsetTopAndBottom(-offset);

        mLastDampedScroll = dampedScroll;
    }

    private int interpolate(int from, int to, float param) {
        return (int) (from * param + to * (1 - param));
    }

    private void fetchBoxOfficeMovies(String link) {
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

                Intent i = new Intent(ActivityM.this, DetailActivity.class);
                // i.putExtra(MOVIE_DETAIL_KEY, adapterMovies.getItem(position));
                i.putExtra("POSITION_RID", adapterMovies.getItem(position).getrId());
                i.putExtra("POSITION", position);
                startActivity(i);
            }
        });
    }


}*/