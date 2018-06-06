package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.umberto.javajoke.Joke;
import com.umberto.androidjoke.JokeActivity;


public class MainActivity extends AppCompatActivity implements JokeEndpointsAsyncTask.OnTaskCompleted {
    private static final String JOKE_KEY="joke_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    public void tellJoke(View view) {
        AsyncTask<Context, Void, String> execute = new JokeEndpointsAsyncTask().execute(this);
    }


    @Override
    public void onDownloadJokeTaskCompleted(String joke) {
        Bundle extra=new Bundle();
        extra.putString(JOKE_KEY, joke);
        Intent intent=new Intent(this,JokeActivity.class);
        intent.putExtras(extra);

        startActivity(intent);
    }
}
