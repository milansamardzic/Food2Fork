package com.milansamardzic.food2fork;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.manuelpeinado.fadingactionbar.view.ObservableScrollable;
import com.manuelpeinado.fadingactionbar.view.OnScrollChangedCallback;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by ms on 1/18/15.
 */
public class DetailAct extends ActionBarActivity implements OnScrollChangedCallback {

    private Toolbar mToolbar;
    private Drawable mActionBarBackgroundDrawable;
    private View mHeader;
    private int mLastDampedScroll;
    private int mInitialStatusBarColor;
    private int mFinalStatusBarColor;
    private SystemBarTintManager mStatusBarManager;

    public TextView tvTitle;
    public String title;
    public String rId;
    public String imageUrl;
    public String f2fUrl=null;
    public String publisherUrl = null;
    public String ingredientsString = "";

    Button btnPublisher;
    TextView tvIngredients;
    Button btnSocialRank;
    ImageView ivHeader;
    Button btnSeeOnWeb;
    Button btnCalendar;
    ListView lvIngredients;
    ImageButton fabB;

    public String checkString=null;

    public String saveJson=null;
    public ArrayList<String> ars = new ArrayList<>();

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        fabB = (ImageButton) findViewById(R.id.fabbuttonFav);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        btnPublisher = (Button) findViewById(R.id.btnPublisher);
        tvIngredients = (TextView) findViewById(R.id.tvIngredients);
        btnSocialRank = (Button) findViewById(R.id.btnSocialRank);
        ivHeader = (ImageView) findViewById(R.id.header);
        btnSeeOnWeb = (Button) findViewById(R.id.btnSeeOnWeb);
        btnCalendar = (Button) findViewById(R.id.btnAddToCal);
        lvIngredients = (ListView) findViewById(R.id.lvIngredients);

        Bundle bundle = getIntent().getExtras();

        if (bundle.getString("POSITION_RID") != null) {
            rId = bundle.getString("POSITION_RID");
        }


        final String fullLink = "http://food2fork.com/api/get?key=385e030f0db665f1aa2969efe478d451&rId=" + rId;
        fetchBoxOfficeMovies(fullLink);


        btnPublisher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(DetailAct.this, Webview.class);
                i.putExtra("WEB_LINK", publisherUrl);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);

            }
        });

        btnSeeOnWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(DetailAct.this, Webview.class);
                i.putExtra("WEB_LINK", f2fUrl);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);

            }
        });

        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 14) {

                    Intent intent = new Intent(Intent.ACTION_INSERT)
                            .setData(CalendarContract.Events.CONTENT_URI)
                            .putExtra(CalendarContract.Events.TITLE, title)
                            .putExtra(CalendarContract.Events.DESCRIPTION, ingredientsString)
                            .putExtra(CalendarContract.Events.EVENT_LOCATION, "Home")
                            .putExtra(Intent.EXTRA_EMAIL, "rowan@example.com,trevor@example.com");
                    startActivity(intent);
                } else {
                    Calendar cal = Calendar.getInstance();
                    Intent intent = new Intent(Intent.ACTION_EDIT);
                    intent.setType("vnd.android.cursor.item/event");
                    intent.putExtra("beginTime", cal.getTimeInMillis());
                    intent.putExtra("allDay", false);
                    intent.putExtra("rrule", "FREQ=YEARLY");
                    intent.putExtra("endTime", cal.getTimeInMillis() + 60 * 60 * 1000);
                    intent.putExtra("title", title);
                    intent.putExtra("description", ingredientsString);
                    startActivity(intent);
                }

            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // toolbar.setNavigationIcon(R.drawable.ab_background_light);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setBackgroundColor(getResources().getColor(R.color.primary));
        mActionBarBackgroundDrawable = mToolbar.getBackground();
        setSupportActionBar(mToolbar);
        mStatusBarManager = new SystemBarTintManager(this);
        mStatusBarManager.setStatusBarTintEnabled(true);
        mInitialStatusBarColor = Color.TRANSPARENT;
        mFinalStatusBarColor = getResources().getColor(R.color.primary_dark);

        mHeader = findViewById(R.id.header);

        ObservableScrollable scrollView = (ObservableScrollable) findViewById(R.id.scrollview);
        scrollView.setOnScrollChangedCallback(this);

        onScroll(-1, 0);

        Outline mOutlineCircle;
        int shapeSize = getResources().getDimensionPixelSize(R.dimen.button_elevation);
        mOutlineCircle = new Outline();
        mOutlineCircle.setRoundRect(0, 0, shapeSize, shapeSize, shapeSize / 2);

        fabB.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                int size = getResources().getDimensionPixelSize(R.dimen.fab_size);
                outline.setOval(0, 0, size, size);
            }
        });
        findViewById(R.id.fabbuttonFav).setClipToOutline(true);

        fabB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

    }


    private void save() {
        TinyDB tinydb = new TinyDB(getApplicationContext());
        ArrayList check = (tinydb.getList("MySaved"));
        Boolean isDeleted=false;

        ars = check;
        for(int i=0; i<ars.size(); i++) {
            if (ars.get(i).contains(rid)) {
                ars.remove(i);
                fabB.setImageResource(R.drawable.fork);
                Toast.makeText(getApplicationContext(), "Removed", Toast.LENGTH_SHORT).show();
                tinydb.putList("MySaved", ars);
                isDeleted = true;
            }else{ isDeleted=false;}
        }

        if (isDeleted==false) {

            if (check.isEmpty() == true) {
                ars.add(saveJson);
                fabB.setImageResource(R.drawable.fork_fav);
                tinydb.putList("MySaved", ars);
            } else {

                ars.add(saveJson);
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                fabB.setImageResource(R.drawable.fork_fav);
                tinydb.putList("MySaved", ars);

            }
        }


    }

    String rid;
    public void fetchBoxOfficeMovies(String link) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(link, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject body) {
                JSONObject items = null;
                try {
                    items = body.getJSONObject("recipe");
                    saveJson = String.valueOf(body);
                    Log.d("rid1", saveJson);
                    Detalji det = Detalji.fetchDetalje(items);

                    title = det.getTitle();
                    f2fUrl = det.getF2fUrl();
                    rid = det.getrId();
                    publisherUrl = det.getPublisherUrl();
                    imageUrl = det.getImage();
                    tvTitle.setText(det.getTitle());
                    tvIngredients.setText("");

                    ArrayList list= det.getIngredientsArrayList();
                    int pom=0;
                    TextView tvI = (TextView) findViewById(R.id.tvI);
                    tvI.setText("Ingredients (" + (list.size()) + ")");
                    for (int i=0; i<list.size(); i++)
                    {

                        //String no = "<b>" + String.valueOf(i+1) + "</b>";
                        String part = "\n" + (i+1) + ". " + Html.fromHtml("&#187") + " " + list.get(i).toString()  + "\n";
                        tvIngredients.append( part);
                        ingredientsString = ingredientsString + part;

                    }

                    btnPublisher.setText(det.getPublisher());
                    btnSocialRank.setText(String.valueOf(det.getSocialRank()) + " %");
                    Picasso.with(getBaseContext()).load(det.getImage()).into(ivHeader);



                    TinyDB tinydb = new TinyDB(getApplicationContext());
                    ArrayList check = (tinydb.getList("MySaved"));
                   for (int i=0; i<check.size();i++)
                       if(check.get(i).toString().contains(det.getrId()))
                       {fabB.setImageResource(R.drawable.fork_fav);}

                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        });
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

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detail, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
            case R.id.share:{
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, title + " by " + f2fUrl + " via [" + R.string.app_name + "] " + imageUrl);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }


}
