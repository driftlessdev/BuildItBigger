package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.testinprod.jokedirectory.backend.myApi.MyApi;

import java.io.IOException;

/**
 * Created by Tim on 1/1/2016.
 */
public class JokeFetchTask extends AsyncTask<Context, Void, String> {
    private static MyApi myApiService = null;
    private Context context;

    @Override
    protected String doInBackground(Context... params) {
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://moonlit-creek-117505.appspot.com/_ah/api/");
            // end options for devappserver

            myApiService = builder.build();
        }

        context = params[0];

        try {
            return myApiService.tellMeAJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
    }
}
