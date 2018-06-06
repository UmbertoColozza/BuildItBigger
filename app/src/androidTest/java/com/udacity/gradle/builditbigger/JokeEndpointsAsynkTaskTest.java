package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.RenamingDelegatingContext;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class JokeEndpointsAsynkTaskTest {
    private Context mContext;
    private String result;
    private static MyApi myApiService = null;

    @Before
    public void setup() {
        //Get context
        mContext = InstrumentationRegistry.getContext();
        assertNotNull(mContext);
        Log.d("Test","Context "+(mContext!=null));
    }

    @Test
    public void JokeAsyncTask() throws Exception {
        //waits for all threads to be completed
        final CountDownLatch signal = new CountDownLatch(1);

        final AsyncTask<Context, Void, String> asyncTask = new AsyncTask<Context, Void, String>() {
            @Override
            protected String doInBackground(Context... params) {
                if (myApiService == null) {  // Only do this once
                    MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                            new AndroidJsonFactory(), null)
                            // options for running against local devappserver
                            // - 10.0.2.2 is localhost's IP address in Android emulator
                            // - turn off compression when running against local devappserver
                            .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                            .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                                @Override
                                public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                    abstractGoogleClientRequest.setDisableGZipContent(true);
                                }
                            });
                    // end options for devappserver

                    myApiService = builder.build();
                }

                try {
                    return myApiService.getJoke().execute().getData();
                } catch (IOException e) {
                    Log.e("Test","Error "+e.toString());
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                result = s;
                //Log.d("Test", "result post "+result);
                signal.countDown();
            }
        };

        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                asyncTask.execute(mContext);
            }
        });

        /* The testing thread will wait here until the UI thread releases it
         * or 30 seconds passes.
         */
        signal.await(60, TimeUnit.SECONDS);
        Log.d("Test","result "+result);
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

}
