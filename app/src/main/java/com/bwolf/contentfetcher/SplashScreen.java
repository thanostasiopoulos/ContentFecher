package com.bwolf.contentfetcher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by bwolf on 14/11/2015.
 */
public class SplashScreen extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); setContentView(R.layout.activity_splash);
        Thread timer = new Thread()
        {
            public void run()
            {
                try
            {
                sleep(500);
            }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    Intent next = new Intent("com.bwolf.contentfetcher.MAINACTIVITY");
                    startActivity(next);
                    finish();
                }
            }
        };
        timer.start(); }


}
