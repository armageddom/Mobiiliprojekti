package pine.app;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



public class ScrollingActivity extends AppCompatActivity {

    int indeksi, id, foodamount, drinkamount;
    String name,description,imageurl,tagi;
    ImageView[] imageArray = new ImageView[100];
    TextView[] textArray = new TextView[100];
    String[] descriptionArray = new String[100];
    String[] imgurlArray = new String[100];
    int[] idArray = new int[100];
    Button buttonfood;

    LinearLayout.LayoutParams layoutParamsImage = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 700);
    LinearLayout.LayoutParams layoutParamsText = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        getBoth(buttonfood);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void addDrinksToFoods() {
        JsonTask asyncTask = (JsonTask) new JsonTask(new JsonTask.AsyncResponse() {
            @Override
            public void processFinish(String output) {

                try {
                    JSONArray json = new JSONArray(output);
                    layoutParamsImage.topMargin = 100;
                    drinkamount = json.length();

                    for(int i = 0; i < drinkamount; i++) {
                        JSONObject jsonobj = json.getJSONObject(i);

                        imageurl = jsonobj.getString("IMG_URL");
                        description = jsonobj.getString("description");
                        name = jsonobj.getString("drink_name");
                        id = jsonobj.getInt("id");

                        indeksi = i+foodamount;
                        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.scrollingLayout);

                        ImageView foodimage = new ImageView(ScrollingActivity.this);
                        imageArray[indeksi] = foodimage;

                        TextView foodtext = new TextView(ScrollingActivity.this);
                        foodtext.setText(name);
                        textArray[indeksi] = foodtext;

                        imageArray[indeksi].setLayoutParams(layoutParamsImage);
                        mainLayout.addView(imageArray[indeksi]);

                        textArray[indeksi].setLayoutParams(layoutParamsText);
                        textArray[indeksi].setGravity(Gravity.CENTER_HORIZONTAL);
                        mainLayout.addView(textArray[indeksi]);

                        textArray[indeksi].setText(name);
                        textArray[indeksi].setTag(name);
                        imageArray[indeksi].setTag(name);
                        textArray[indeksi].setId(id);

                        idArray[indeksi] = id;
                        descriptionArray[indeksi] = description;
                        imgurlArray[indeksi] = (imageurl);

                        Picasso.with(ScrollingActivity.this).load(imgurlArray[indeksi]).into(imageArray[indeksi]);
                        setOnClickListeners();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).execute("https://shrouded-oasis-85914.herokuapp.com/drinks");
    }


    public void getBoth(View v) {
        getFoods(buttonfood);
        addDrinksToFoods();
    }






    public void getDrinks(View v) {

        emptyViews();

        JsonTask asyncTask = (JsonTask) new JsonTask(new JsonTask.AsyncResponse() {
            @Override
            public void processFinish(String output) {

                try {
                    JSONArray json = new JSONArray(output);
                    layoutParamsImage.topMargin = 100;
                    drinkamount = json.length();

                    for(int i = 0; i < foodamount; i++) {
                        JSONObject jsonobj = json.getJSONObject(i);

                        imageurl = jsonobj.getString("IMG_URL");
                        description = jsonobj.getString("description");
                        name = jsonobj.getString("drink_name");
                        id = jsonobj.getInt("id");

                        indeksi = i;
                        setTextsAndImages();
                        setOnClickListeners();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).execute("https://shrouded-oasis-85914.herokuapp.com/drinks");
    }






    public void getFoods(View v) {

        emptyViews();

        JsonTask asyncTask = (JsonTask) new JsonTask(new JsonTask.AsyncResponse() {
            @Override
            public void processFinish(String output) {

                try {
                    JSONArray json = new JSONArray(output);
                    layoutParamsImage.topMargin = 100;
                    foodamount = json.length();

                    for(int i = 0; i < foodamount; i++) {
                        JSONObject jsonobj = json.getJSONObject(i);

                        imageurl = jsonobj.getString("IMG_URL");
                        description = jsonobj.getString("description");
                        name = jsonobj.getString("food_name");
                        id = jsonobj.getInt("id");

                        indeksi = i;
                        setTextsAndImages();
                        setOnClickListeners();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).execute("https://shrouded-oasis-85914.herokuapp.com/foods");
    }






    public void setTextsAndImages() {
        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.scrollingLayout);

        ImageView foodimage = new ImageView(ScrollingActivity.this);
        imageArray[indeksi] = foodimage;

        TextView foodtext = new TextView(ScrollingActivity.this);
        foodtext.setText(name);
        textArray[indeksi] = foodtext;

        imageArray[indeksi].setLayoutParams(layoutParamsImage);
        mainLayout.addView(imageArray[indeksi]);

        textArray[indeksi].setLayoutParams(layoutParamsText);
        textArray[indeksi].setGravity(Gravity.CENTER_HORIZONTAL);
        mainLayout.addView(textArray[indeksi]);

        textArray[indeksi].setText(name);
        textArray[indeksi].setTag(name);
        imageArray[indeksi].setTag(name);
        textArray[indeksi].setId(id);

        idArray[indeksi] = id;
        descriptionArray[indeksi] = description;
        imgurlArray[indeksi] = (imageurl);

        Picasso.with(ScrollingActivity.this).load(imgurlArray[indeksi]).into(imageArray[indeksi]);
    }







    public void setOnClickListeners() {

        imageArray[indeksi].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                tagi = v.getTag().toString();
                clickityclick();
            }
        });

        textArray[indeksi].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                tagi = v.getTag().toString();
                clickityclick();
            }
        });
    }







    public void clickityclick() {
        int etsitäänid = 0;

        while(tagi != textArray[etsitäänid].getTag())
        {
            etsitäänid++;
        }

        Toast.makeText(getBaseContext(), "You have selected " + imageArray[etsitäänid].getTag() + System.lineSeparator() +
                "Description: " + descriptionArray[etsitäänid] + System.lineSeparator() +
                "ID: " + idArray[etsitäänid], Toast.LENGTH_SHORT).show();
    }




    public void emptyViews() {
        for(int i = 0; i < foodamount+drinkamount; i++) {
            LinearLayout mainLayout = (LinearLayout) findViewById(R.id.scrollingLayout);
            mainLayout.removeView(textArray[i]);
            mainLayout.removeView(imageArray[i]);
        }

        imageArray = new ImageView[100];
        textArray = new TextView[100];
        descriptionArray = new String[100];
        imgurlArray = new String[100];
        idArray = new int[100];
    }



}



