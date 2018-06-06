package com.udacity.gradle.builditbigger;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        Button btnTellJoke=(Button)root.findViewById(R.id.tel_joke_button);
        btnTellJoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTask<Context, Void, String> execute = new JokeEndpointsAsyncTask().execute(getContext());
            }
        });
        return root;
    }

    public void tellJoke(View view) {
        AsyncTask<Context, Void, String> execute = new JokeEndpointsAsyncTask().execute(getContext());
    }
}
