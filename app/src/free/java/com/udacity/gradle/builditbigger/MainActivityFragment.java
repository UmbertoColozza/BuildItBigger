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

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.umberto.androidjoke.JokeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements JokeEndpointsAsyncTask.OnTaskCompleted {
    private static final String JOKE_KEY="joke_key";
    private InterstitialAd mInterstitialAd;
    @BindView(R.id.tel_joke_button) Button btnTellJoke;
    @BindView(R.id.adView) AdView mAdView;
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
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    showJoke();
                }
            }
        });


        return root;
    }

    private void showJoke(){
        //Show progress bar
        showProgress(true);
        AsyncTask<JokeEndpointsAsyncTask.OnTaskCompleted, Void, String> execute = new JokeEndpointsAsyncTask().execute(this);
    }

    private void setAdRequest(){
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        //set mInterstitialAd if is null
        if(mInterstitialAd==null) {
            mInterstitialAd = new InterstitialAd(getContext());
            mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
            mInterstitialAd.setAdListener(new AdListener(){
                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                    //On close ad show joke
                    showJoke();
                }
            });
        }
        //Reload every time
        mInterstitialAd.loadAd(adRequest);
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
    public void onStart() {
        super.onStart();
        //Set AD
        setAdRequest();
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
