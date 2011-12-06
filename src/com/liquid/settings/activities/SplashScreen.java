
package com.liquid.settings.activities;

import com.liquid.settings.R;
 
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
 
public class SplashScreen extends Activity {

    private static boolean SPLASH_ALREADY;
    private static final int SPLASH_DISPLAY_TIME = 2 * 1000;  /* 2 seconds */
    private static final String TAG = "LiquidSettings";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Splash screen displayed for: " + SPLASH_DISPLAY_TIME);

        if (savedInstanceState == null) {
            SPLASH_ALREADY = false;
            Log.d(TAG, "savedInstanceState == null setting ALREADY_SPLASH to false");
        } else if (savedInstanceState != null) {
            SPLASH_ALREADY = true;
            Log.d(TAG, "savedInstanceState != null setting ALREADY_SPLASH to true");
        } else {
            Log.wtf(TAG, "Something went wrong shit is all fucked up");
        }

        /* Create a new handler with which to start the main activity
           and close this splash activity after SPLASH_DISPLAY_TIME has
           elapsed. */
        if (!SPLASH_ALREADY) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    /* Create an intent that will start the main activity. */
                    Intent main_activity = new Intent(SplashScreen.this,   MainActivity.class);
                    SplashScreen.this.startActivity(main_activity);

                    /* Finish splash activity so user cant go back to it. */
                    SplashScreen.this.finish();

                    /* Apply our splash exit (fade out) and main entry (fade in) animation transitions. */
                    overridePendingTransition(R.anim.fade_main_in, R.anim.fade_splash_out);
                }
            }, SPLASH_DISPLAY_TIME);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onStart() {
        super.onStart();
        setContentView(R.layout.splash);

    }
}
