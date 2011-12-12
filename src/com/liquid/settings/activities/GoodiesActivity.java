
package com.liquid.settings.activities;

import com.liquid.settings.R;
 
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View;
import android.view.MotionEvent;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
 
public class GoodiesActivity extends Activity {

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private String SU_CMDS = null;
    private GestureDetector gestureDetector;
 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goodies_settings);
 
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

        /* Droid eyes */
        ImageButton eyes_green_button = (ImageButton) findViewById("eyes_green_command");
        eyes_green_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                droidEyes("green");
        });

        ImageButton eyes_light_blue_button = (ImageButton) findViewById("eyes_light_blue_command");
        eyes_light_blue_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                droidEyes("light_blue");
        });

        ImageButton eyes_blue_green_button = (ImageButton) findViewById("eyes_blue_green_command");
        eyes_blue_green_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                droidEyes("blue_green");
        });

        ImageButton eyes_cyan_button = (ImageButton) findViewById("eyes_cyan_command");
        eyes_cyan_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                droidEyes("cyan");
        });


        ImageButton eyes_orange_button = (ImageButton) findViewById("eyes_orange_command");
        eyes_orange_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                droidDoes("orange");
        });

        ImageButton eyes_pink_button = (ImageButton) findViewById("eyes_pink_command");
        eyes_pink_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                droidDoes("pink");
        });

        ImageButton eyes_purple_button = (ImageButton) findViewById("eyes_purple_command");
        eyes_purple_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                droidDoes("purple");
        });

        ImageButton eyes_red_button = (ImageButton) findViewById("eyes_red_command");
        eyes_red_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                droidDoes("red");
        });

        ImageButton eyes_yellow_button = (ImageButton) findViewById("eyes_yellow_command");
        eyes_red_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                droidDoes("yellow");
        });

        ImageButton gingy_faster_button = (ImageButton) findViewById("gingy_faster_command");
        eyes_red_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                specialBranding("faster");
        });

        ImageButton gingy_slower_button = (ImageButton) findViewById("gingy_slower_command");
        eyes_red_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                specialBranding("slower");
        });



  }

    private boolean buildMoveScript() {

        String SPACE = " ; ";
        StringBuilder cmds = new StringBuilder();
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
        cmds.append(SPACE);
        cmds.append("exit");

        SU_CMDS = cmds.toString();

    }

    private void droidDoes(String color) {

        String WHAT = new String();
        String PROMPT = "Install droid does boot animation?";

        switch (color.equals()) {
            case "gingy":
                WHAT = "doesginger.zip";
                break;
            case "green":
                WHAT = "doesgreen.zip";
                break;
            case "light_blue":
                WHAT = "doeslightblue.zip";
                break;
            case "med_blue":
                WHAT = "doesmedblue.zip";
                break;
            case "orange":
                WHAT = "doesorange.zip";
                break;
            case "pink"
                WHAT = "doespink.zip";
                break;
            case "purple":
                WHAT = "doespurple.zip";
                break;
            case "red":
                WHAT = "doesred.zip";
                break;
            case "smoked":
                WHAT = "doessmoked.zip";
                break;
            case "two_blue":
                WHAT = "doestwoblue.zip";
                break;
            case "white":
                WHAT = "doeswhite.zip";
                break;
            case "yellow":
                WHAT = "doesyellow.zip";
                break;
        }

        buildMoveScript();
        String final_webpage = String.format("http://android.markjohnston.us/DL/LGB/BOOTS/DROID/DOES/%s", WHAT)

        //suServer signature changed now including the webaddress 
        runSuCommand(PROMPT, final_webpage);
    }

    private void droidEyes(String color) {

        String PROMPT = "Install droid eyes boot animation?";
        String WHAT = new String();

        switch (color.equals()) {
            case "green":
                WHAT = "eyesgreen.zip";
                break;
            case "light_blue":
                WHAT = "eyeslightblue.zip";
                break;
            case "blue_green":
                WHAT = "eyesbluegreen.zip";
                break;
            case "cyan":
                WHAT = "eyescyan.zip";
                break;
            case "orange":
                WHAT = "eyesorange.zip";
                break;
            case "pink"
                WHAT = "eyespink.zip";
                break;
            case "purple":
                WHAT = "eyespurple.zip";
                break;
            case "red":
                WHAT = "eyesred.zip";
                break;
            case "yellow":
                WHAT = "eyesyellow.zip";
                break;
        }

        buildMoveScript();
        String final_webpage = String.format("http://android.markjohnston.us/DL/LGB/BOOTS/DROID/EYES/%s", WHAT)

        //suServer signature changed now including the webaddress 
        runSuCommand(PROMPT, final_webpage);
    }

    private void specialBranding(String branding) {

        String WHAT = new String();
        String PROMPT = "Install a special boot animation?";

        switch (branding.equals()) {
            case "faster":
                WHAT = "GINGY/gingyfaster.zip";
                break;
            case "slower":
                WHAT = "GINGY/gingyslower.zip";
                break;
        }

        buildMoveScript();
        String final_webpage = String.format("http://android.markjohnston.us/DL/LGB/BOOTS/DROID/%s", WHAT)

        //suServer signature changed now including the webaddress 
        runSuCommand(PROMPT, final_webpage);
    }
    private void runSuCommand(final String message, String web){
        //we recieve the message to display && the website
        //now we need to check to see if 
        //SU_CMDS is still null; and we will reset it to null in the async
        if (SU_CMDS !=null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        new SuServer().execute(CommandStr);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

            AlertDialog alertDialog = builder.create();
            builder.show();
        } else {
            Toast.makeText(this, "shit is all fucked up over here", Toast.LENGTH_SHORT).show();
        }
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

    private class SuServer extends AsyncTask<String, String, Void> {
        private ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(GoodiesActivity.this, "Working", "Running Command...", true, false);
        }

        @Override
        protected void onProgressUpdate(String... values) {
        }

        @Override
        protected void onPostExecute(Void result) {
            pd.dismiss();

        }

        @Override
        protected Void doInBackground(String script, String path) {
            final Process p;

            try {
                File tmpDir = new File(Environment.getExternalStorageDirectory().toString() + "/.liquid");
                if (!tmpDir.exists()) {
                    tmpDir.mkdir();
                }

                //must be full path http:// and all
                URL url = new URL(path);
                String dl_path = Environment.getExternalStorageDirectory().toString() + "/.liquid/bootanimation.zip";

                File dl_file = new File(dl_path);

                //timer so we can moniter progress later
                long startTime = System.currentTimeMillis();

                //open connection and read inputStreams
                URLConnection connecter = url.openConnection();
                InputStream incomming_data = connecter.getInputStream();
                BufferedInputStream input_buffer = new BufferedInputStream(incomming_data);

                //TODO is 2048 better than 1024?
                ByteArrayBuffer ba_buffer = new ByteArrayBuffer(2048);
                int current = 0;
                while ((current = input_buffer.read()) != -1) {
                    ba_buffer.append((byte) currnet);
                }

                FileOptputStream file_out = new FileOpenStream(dl_file);
                file_out.write(ba_buffer.toByteArray());
                file_out.close();
                Log.d
            } catch (IOException ioe) {
                Log.d(TAG, "Error: " + ioe);
                ioe.printStackTrace();
                //TODO:
                //not sure what to do here dl has failed we can't proceed
                //maybe we throw a runtimeExeption?
                //for now we will just finish
                finish();
            }

            //now we have our file local
            //lets move it around to apply
            try {
                String EXIT = ";\nexit\n";
                p = Runtime.getRuntime().exec("su -c sh");
                BufferedReader stdInput = new BufferedReader( new InputStreamReader(p.getInputStream()));
                //TODO:
                //I'm commenting this for now but consider it marked for
                //execution if we don't add in some usage of this reader
                //BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
                BufferedWriter stdOutput = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));

                stdOutput.write(script);
                stdOutput.write(EXIT);
                stdOutput.flush();
                Thread t = new Thread() {
                    public void run() {
                        try {
                            p.waitFor();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };

                t.start();

                while (t.isAlive()) {
                    String status = stdInput.readLine();
                    if (status != null) {
                        publishProgress(status);
                    }
                    //TODO Liquid why do we sleep here?
                    Thread.sleep(20);
                }

                stdInput.close();
                stdError.close();
                stdOutput.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            } catch (NullPointerException npe) {
                npe.printStackTrace();
            }
            return null;
        }
    }
}
