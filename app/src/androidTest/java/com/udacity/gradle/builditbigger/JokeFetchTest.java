package com.udacity.gradle.builditbigger;

import android.test.AndroidTestCase;
import android.text.TextUtils;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Tim on 1/1/2016.
 *
 * Adapted from http://marksunghunpark.blogspot.com/2015/05/how-to-test-asynctask-in-android.html
 */
public class JokeFetchTest extends AndroidTestCase {
    CountDownLatch mSignal;
    String mJoke;
    Exception mException;

    @Override
    protected void tearDown() throws Exception {
        if(mSignal != null)
        {
            mSignal.countDown();
        }
    }

    @Override
    protected void setUp() throws Exception {
        mSignal = new CountDownLatch(1);
    }

    public void testFetchJoke() throws InterruptedException {
        JokeFetchTask jokeFetchTask = new JokeFetchTask();
        JokeFetchTask.JokeCallback callback = new JokeFetchTask.JokeCallback() {
            @Override
            public void onJokeLoaded(String joke, Exception error) {
                mJoke = joke;
                mException = error;
                mSignal.countDown();
            }
        };
        jokeFetchTask.execute(callback);
        mSignal.await();

        assertNull(mException);
        assertFalse(TextUtils.isEmpty(mJoke));
    }
}
