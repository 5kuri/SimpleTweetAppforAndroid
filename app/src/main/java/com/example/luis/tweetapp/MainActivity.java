package com.example.luis.tweetapp;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Handler handler = new Handler();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Twitter twitter = TwitterFactory.getSingleton();
        twitter.setOAuthConsumer("DbCWRQSubly89fuLqpt5knzOv",
                "GoGuHZcHWi1wC0b9OygQYMiNX6r71cyABRpkLdyL0dk6HWqjpk");
        AccessToken accessToken = new AccessToken("157883960-cV7SGGREjZugOUpsmIRgGlTIzmZuS7Apvel3Wyl6",
                "0EbWmA7KLyH69VS8QOKhsu7kYjNirhh3u9g2x0vXG1C0V");
        twitter.setOAuthAccessToken(accessToken);

        final EditText editText = findViewById(R.id.text);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable(){
                    @Override
                    public void run(){
                        try {
                            String tmpText = editText.getText().toString();
                            if (!tmpText.trim().isEmpty()){
                                twitter.updateStatus(editText.getText().toString() + " #tkbapk");
                            }
                        } catch (TwitterException e){
                            e.printStackTrace();
                        }
                    }
                }).start();
            }

        });

        final TextView counter = findViewById(R.id.counter);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                counter.setText(String.valueOf(editText.length()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        RecyclerView timeline = findViewById(R.id.timeline);
        timeline.setLayoutManager(new LinearLayoutManager(this));
        timeline.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        final TimelineAdapter adapter = new TimelineAdapter();
        timeline.setAdapter(adapter);

        new Thread(new Runnable() {
            @Override
            public void run() {
                ResponseList<Status> statuses = null;
                try {
                    statuses = twitter.getHomeTimeline();
                } catch (TwitterException e){
                    e.printStackTrace();
                }
                if (statuses == null){
                    return;
                }
                final ResponseList<Status> finalStatuses = statuses;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setStatuses(finalStatuses);
                    }
                });
            }
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
