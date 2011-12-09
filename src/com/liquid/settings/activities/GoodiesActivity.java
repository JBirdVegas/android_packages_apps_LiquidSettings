
package com.liquid.settings.activities;

import com.liquid.settings.R;
 
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View;
import android.view.MotionEvent;
 
public class GoodiesActivity extends Activity {
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private GestureDetector gestureDetector;
 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_layout);
 
        gestureDetector = new GestureDetector(new MyGestureDetector());
        View mainview = (View) findViewById(R.id.mainView2);
 
        // Set the touch listener for the main view to be our custom gesture listener
        mainview.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (gestureDetector.onTouchEvent(event)) {
                    return true;
                }
                return false;
            }
        });

    setupListeners();

    }




/* --------------> CODE HERE <----------------- */

    private void setupListeners() {

        ImageButton does_gingy_button = (ImageButton) findViewById("does_gingy_command");
        does_gingy_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                droidDoes("gingy");
        });

        ImageButton does_green_button = (ImageButton) findViewById("does_green_command");
        does_green_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                droidDoes("green");
        });

        ImageButton does_light_blue_button = (ImageButton) findViewById("does_light_blue_command");
        does_light_blue_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                droidDoes("light_blue");
        });

        ImageButton does_med_blue_button = (ImageButton) findViewById("does_green_command");
        does_med_blue_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                droidDoes("med_blue");
        });

        ImageButton does_orange_button = (ImageButton) findViewById("does_orange_command");
        does_orange_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                droidDoes("orange");
        });

        ImageButton does_pink_button = (ImageButton) findViewById("does_pink_command");
        does_pink_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                droidDoes("pink");
        });

        ImageButton does_purple_button = (ImageButton) findViewById("does_purple_command");
        does_purple_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                droidDoes("purple");
        });

        ImageButton does_red_button = (ImageButton) findViewById("does_red_command");
        does_red_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                droidDoes("red");
        });

        ImageButton does_smoked_button = (ImageButton) findViewById("does_smoked_command");
        does_smoked_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                droidDoes("smoked");
        });

        ImageButton does_two_blue_button = (ImageButton) findViewById("does_two_blue_command");
        does_two_blue_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                droidDoes("two_blue");
        });

        ImageButton does_white_button = (ImageButton) findViewById("does_white_command");
        does_white_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                droidDoes("white");
        });

        ImageButton does_yellow_button = (ImageButton) findViewById("does_yellow_command");
        does_yellow_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                droidDoes("yellow");
        });

    }

    private droidDoes(String color) {

        String SPACE = " ; ";
        String WHAT = new String();
        StringBuilder cmds = new StringBuilder();

        if (color.equals("gingy")) {
            WHAT = "doesginger.zip";
        } else if (color.equals("green") {
            WHAT = "doesgreen.zip";
        } else if (color.equals("light_blue")) {
            WHAT = "doeslightblue.zip";
        } else if (color.equals("med_blue")) {
            WHAT = "doesmedblue.zip";
        } else if (color.equals("orange")) {
            WHAT = "doesorange.zip";
        } else if (color.equals("pink")) {
            WHAT = "doespink.zip";
        } else if (color.equals("purple")) {
            WHAT = "doespurple.zip";
        } else if (color.equals("red")) {
            WHAT = "doesred.zip";
        } else if (color.equals("smoked")) {
            WHAT = "doessmoked.zip";
        } else if (color.equals("two_blue")) {
            WHAT = "doestwoblue.zip";
        } else if (color.equals("white")) {
            WHAT = "doeswhite.zip";
        } else if (color.equals("yellow")) {
            WHAT = "doesyellow.zip";
        } else {
            Log.wtf(TAG, "shit is all fucked up");
            finish();
        }

        cmds.append("busybox mount -o rw,remount -t yaffs2 /dev/block/mtdblock1 /system");
        cmds.append(SPACE);
        cmds.append("busybox mv /sdcard/doesgingy.zip /sdcard/bootanimation.zip");
        cmds.append(SPACE);
        cmds.append("busybox cp -R /sdcard/bootanimation.zip /data/local/");
        cmds.append(SPACE);
        cmds.append("busybox rm /sdcard/bootanimation.zip");
        cmds.append(SPACE);
        cmds.append("busybox mount -o ro,remount -t yaffs2 /dev/block/mtdblock1 /system");
        cmds.append(SPACE);
        cmds.append("sync");
        cmds.append("exit");
//TODO FINISH!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    }

    private void RunSuCommand(final String CommandStr, String YesNoString){
        //TODO this method need updating we changed everything about it
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(YesNoString)
            .setCancelable(false)
            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    //TODO add in SuServer embeddedd class
                    // embedded in the SuServer class we need to use java for file download
                    // bb is cool for moving
                    // see example http://www.helloandroid.com/tutorials/how-download-fileimage-url-your-device
                    new SuServer().execute(CommandStr);
                }
            })
            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    }
            });

            AlertDialog alertDialog = builder.create();
            builder.show();
    }

    /* this class must be embedded in the Activity to register the MotionEvents */
    class MyGestureDetector extends SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
 
            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH) {
                return false;
            }
 
            // right to left swipe next Activity
            if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
    		startActivity(intent);
    		SecondActivity.this.overridePendingTransition(
			R.anim.slide_in_right,
			R.anim.slide_out_left
    		);
            finish();
    	    // left to right swipe previous Activity
            }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                Intent intent = new Intent(SecondActivity.this, MainActivity.class);
    		startActivity(intent);
    		SecondActivity.this.overridePendingTransition(
			R.anim.slide_in_left, 
			R.anim.slide_out_right
    		);
            }
            finish(); 

            return false;
        }
 
        // It is necessary to return true from onDown for the onFling event to register
        @Override
        public boolean onDown(MotionEvent e) {
	        	return true;
        }
    }
}
