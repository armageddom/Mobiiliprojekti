package a.mainview;

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

    String imageurl, description, drink_name;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        TextView topText = new TextView(this);
        topText.setText("Suosituimmat5556665");
        topText.setGravity(Gravity.CENTER_HORIZONTAL);

        JsonTask asyncTask = (JsonTask) new JsonTask(new JsonTask.AsyncResponse() {
            @Override
            public void processFinish(String output) {

                try {
                    JSONObject json = new JSONObject(output);
                    imageurl = json.getString("IMG_URL");
                    description = json.getString("description");
                    drink_name = json.getString("food_name");
                    id = json.getInt("id");



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).execute("https://shrouded-oasis-85914.herokuapp.com/drinks");

        topText.setText(drink_name);

        int indeksi = 0;
        final ImageView[] imageArray = new ImageView[100];
        TextView[] textArray = new TextView[100];

        String[] ruoat = {"kaalilaatikko","meksikonpata", "silakat", "herkku", "torttu"};
        int numberoffoods = ruoat.length;

        while (indeksi < numberoffoods) {
            ImageView foodimage = new ImageView(this);
            foodimage.setImageResource(R.drawable.maksalaatikko);
            imageArray[indeksi] = foodimage;

            TextView foodname = new TextView(this);
            foodname.setText(ruoat[indeksi]);
            textArray[indeksi] = foodname;

            indeksi++;
        }
        indeksi = 0;

        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.scrollingLayout);
        LinearLayout.LayoutParams layoutParamsImage = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 700);
        layoutParamsImage.topMargin = 100;

        LinearLayout.LayoutParams layoutParamsText = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200);

        mainLayout.addView(topText);

        while (indeksi < numberoffoods) {
            imageArray[indeksi].setLayoutParams(layoutParamsImage);
            mainLayout.addView(imageArray[indeksi]);

            textArray[indeksi].setLayoutParams(layoutParamsText);
            textArray[indeksi].setGravity(Gravity.CENTER_HORIZONTAL);
            mainLayout.addView(textArray[indeksi]);

            final int ido = indeksi+1;
            imageArray[indeksi].setTag(ruoat[indeksi]);

            imageArray[indeksi].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Toast.makeText(getBaseContext(), "You have selected " + imageArray[ido-1].getTag(), Toast.LENGTH_SHORT).show();
                }
            });

            textArray[indeksi].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Toast.makeText(getBaseContext(), "You have selected " + imageArray[ido-1].getTag(), Toast.LENGTH_SHORT).show();
                }
            });

            indeksi++;
        }
        indeksi = 0;

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
}



