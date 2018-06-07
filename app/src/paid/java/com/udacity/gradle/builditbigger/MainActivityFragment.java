package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import com.umberto.androidjoke.JokeActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivityFragment extends Fragment implements JokeEndpointsAsyncTask.OnTaskCompleted {
    private static final String JOKE_KEY="joke_key";
    @BindView(R.id.tel_joke_button) Button btnTellJoke;
    @BindView(R.id.progress_bar) ProgressBar pBar;
    @BindView(R.id.joke_relative_layout) RelativeLayout jokeLayout;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, root);

        btnTellJoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showJoke();
            }
        });
        return root;
    }

    private void showJoke(){
        //Show progress bar
        showProgress(true);
        AsyncTask<JokeEndpointsAsyncTask.OnTaskCompleted, Void, String> execute = new JokeEndpointsAsyncTask().execute(this);
    }

    //Show or hide progress bar
    private void showProgress(boolean show){
        if(show){
            jokeLayout.setVisibility(View.GONE);
            pBar.setVisibility(View.VISIBLE);
        }else {
            jokeLayout.setVisibility(View.VISIBLE);
            pBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDownloadJokeTaskCompleted(String joke) {
        Bundle extra=new Bundle();
        extra.putString(JOKE_KEY, joke);
        Intent intent=new Intent(getContext(),JokeActivity.class);
        intent.putExtras(extra);

        startActivity(intent);

        //Hide progress bar
        showProgress(false);
    }
}
