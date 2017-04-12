package pine.app;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class SinglePost extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_single_post);
        Bundle extras = getIntent().getExtras();

        LinearLayout singlePostLayout = (LinearLayout) findViewById(R.id.singlePostLayout);

        LinearLayout.LayoutParams layoutParamsImage = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 700);
        layoutParamsImage.topMargin = 100;
        LinearLayout.LayoutParams layoutParamsText = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200);

        String name = "error", description = "error", imageurl = "error";

        if (extras != null) {
            name = extras.getString("name");
            description = extras.getString("description");
            imageurl = extras.getString("imageurl");
        }

        ImageView foodimage = new ImageView(SinglePost.this);
        Picasso.with(SinglePost.this).load(imageurl).into(foodimage);
        foodimage.setLayoutParams(layoutParamsImage);
        singlePostLayout.addView(foodimage);

        TextView descriptioni = new TextView(SinglePost.this);
        descriptioni.setText(description);
        descriptioni.setLayoutParams(layoutParamsText);
        singlePostLayout.addView(descriptioni);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(name);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
