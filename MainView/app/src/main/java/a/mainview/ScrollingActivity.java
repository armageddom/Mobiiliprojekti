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

    int indeksi, id;
    String food_name,description,imageurl;
    ImageView[] imageArray = new ImageView[100];
    TextView[] textArray = new TextView[100];

    LinearLayout.LayoutParams layoutParamsImage = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 700);
    LinearLayout.LayoutParams layoutParamsText = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        JsonTask asyncTask = (JsonTask) new JsonTask(new JsonTask.AsyncResponse() {
            @Override
            public void processFinish(String output) {

                try {
                    JSONArray json = new JSONArray(output);
                    /*imageurl = json.getString("IMG_URL");
                    description = json.getString("description");
                    food_name = json.getString("food_name");
                    id = json.getInt("id");*/

                    LinearLayout mainLayout = (LinearLayout) findViewById(R.id.scrollingLayout);
                    layoutParamsImage.topMargin = 100;

                    for(int i = 0; i < json.length(); i++) {
                        JSONObject jsonobj = json.getJSONObject(i);

                        /*System.out.println("imageurl : " + i + " = " + jsonobj.getString("IMG_URL"));
                        System.out.println("description : " + i + " = " + jsonobj.getString("description"));
                        System.out.println("food_name : " + i + " = " + jsonobj.getString("food_name"));
                        System.out.println("id : " + i + " = " + jsonobj.getInt("id"));*/

                        imageurl = jsonobj.getString("IMG_URL");
                        description = jsonobj.getString("description");
                        food_name = jsonobj.getString("food_name");
                        id = jsonobj.getInt("id");

                        ImageView foodimage = new ImageView(ScrollingActivity.this);
                        foodimage.setImageResource(R.drawable.maksalaatikko);
                        imageArray[i] = foodimage;

                        TextView foodtext = new TextView(ScrollingActivity.this);
                        foodtext.setText(food_name);
                        textArray[i] = foodtext;

                        imageArray[i].setLayoutParams(layoutParamsImage);
                        mainLayout.addView(imageArray[i]);

                        textArray[i].setLayoutParams(layoutParamsText);
                        textArray[i].setGravity(Gravity.CENTER_HORIZONTAL);
                        mainLayout.addView(textArray[i]);

                        textArray[i].setText(food_name);
                        textArray[i].setTag(food_name);
                        imageArray[i].setTag(food_name);

                        indeksi = i;

                        imageArray[i].setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                Toast.makeText(getBaseContext(), "You have selected " + imageArray[indeksi].getTag(), Toast.LENGTH_SHORT).show();
                            }
                        });

                        textArray[i].setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                Toast.makeText(getBaseContext(), "You have selected " + textArray[indeksi].getTag() + System.lineSeparator() +
                                        "Description: " + description, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).execute("https://shrouded-oasis-85914.herokuapp.com/foods");

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
        Log.d("haudi", "8");
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



