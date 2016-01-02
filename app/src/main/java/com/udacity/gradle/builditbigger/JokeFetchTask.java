package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.testinprod.jokedirectory.backend.myApi.MyApi;

import java.io.IOException;

/**
 * Created by Tim on 1/1/2016.
 */
public class JokeFetchTask extends AsyncTask<JokeFetchTask.JokeCallback, Void, String> {
    private static MyApi myApiService = null;
    private Exception mError;
    private JokeCallback mCallback;

    @Override
    protected String doInBackground(JokeCallback... params) {
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://moonlit-creek-117505.appspot.com/_ah/api/");
            // end options for devappserver

            myApiService = builder.build();
        }

        mCallback = params[0];

        try {
            return myApiService.tellMeAJoke().execute().getData();
        } catch (IOException e) {
            mError = e;
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if(mCallback != null)
        {
            mCallback.onJokeLoaded(result, mError);
        }
    }

    public interface JokeCallback{
        void onJokeLoaded(String joke, Exception error);
    }
}
