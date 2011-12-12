
package com.liquid.settings.activities;

import com.liquid.settings.R;
 
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View;
import android.view.MotionEvent;
 
public class MainActivity extends Activity {
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private GestureDetector gestureDetector;

    /* handles for our menu hard key press */
    private final int MENU_REBOOT = 1;
    private final int MENU_RECOVERY = 2;
    private final int MODE_RECOVERY = 1;
    private final String REBOOT = "Reboot";
    private final String RECOVERY = "Recovery";
 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.liquid_settings);
 
        gestureDetector = new GestureDetector(new MyGestureDetector());
        View mainview = (View) findViewById(R.id.mainView);
 
        // Set the touch listener for the main view to be our custom gesture listener
        mainview.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (gestureDetector.onTouchEvent(event)) {
                    return true;
                }
                return false;
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        boolean result = super.onCreateOptionsMenu(menu);
        menu.add(0, MENU_REBOOT, 0, REBOOT).setIcon(R.drawable.menu_reboot);
        menu.add(0, MENU_RECOVERY, 0, RECOVERY).setIcon(R.drawable.menu_recovery);
        return result;
    }
 
    /* Handle the menu selection */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case MENU_REBOOT:
            return RootHelper.runRootCommand("reboot");
        case MENU_RECOVERY:
            return RootHelper.recovery();
        }
        return false;
    }
 
    class MyGestureDetector extends SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

 
            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH) {
                return false;
            }
 
            // right to left swipe next activity for now GoodiesActivity
            if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                Intent intent = new Intent(MainActivity.this, GoodiesActivity.class);
    		startActivity(intent);
    		MainActivity.this.overridePendingTransition(
			R.anim.slide_in_right,
			R.anim.slide_out_left
    		);
            finish();

    	    // left to right swipe previous activity
            // TODO how do we want to handle this jump back to last or bump and stay here?
            }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                Intent intent = new Intent(MainActivity.this, ThirdActivity.class);
    		startActivity(intent);
    		MainActivity.this.overridePendingTransition(
			R.anim.slide_in_left, 
			R.anim.slide_out_right
    		);
            finish();

            }
 
            return false;
        }
 
        // It is necessary to return true from onDown for the onFling event to register
        @Override
        public boolean onDown(MotionEvent e) {
	        	return true;
        }
    }
}
