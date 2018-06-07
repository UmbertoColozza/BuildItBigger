package com.udacity.gradle.builditbigger;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ApplicationTestCase;
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

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

@RunWith(AndroidJUnit4.class)
public class JokeEndpointsAsynkTaskTest extends ApplicationTestCase<Application> implements JokeEndpointsAsyncTask.OnTaskCompleted {

    CountDownLatch signal;
    String joke;

    public JokeEndpointsAsynkTaskTest() {
        super(Application.class);
    }

    @Test
    public void JokeAsyncTask() throws Exception {
            signal = new CountDownLatch(1);
            final AsyncTask<JokeEndpointsAsyncTask.OnTaskCompleted, Void, String> jokeEndpointsAsyncTask = new JokeEndpointsAsyncTask().execute(this);
            signal.await(10, TimeUnit.SECONDS);
            assertNotNull("joke is null", joke);
            assertFalse("joke is empty", joke.isEmpty());
    }

    @Override
    public void onDownloadJokeTaskCompleted(String joke) {
        this.joke = joke;
        signal.countDown();
    }
}
