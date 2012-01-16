
package com.liquid.settings.switches;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.liquid.settings.externals.RootHelper;
import com.liquid.settings.Global;
import com.liquid.settings.R;
import com.liquid.settings.switches.SwitchWidget;

import java.lang.StringBuilder;

public class BootAnimSwitch extends SwitchWidget {
    private static final String TAG = "LiquidSettings :BootAnimSwitch";
    private static final String APPEND_CMD = "echo \"%s=%s\" >> /system/build.prop";
    private static final String KILL_PROP_CMD = "busybox sed -i \"/%s/D\" /system/build.prop";

    public BootAnimSwitch(Context context, Switch switch_) {
        mContext = context;
        mSwitch = switch_;
    }

    public void resume() {
        mSwitch.setOnCheckedChangeListener(this);
    }

    public void pause() {
        mSwitch.setOnCheckedChangeListener(null);
    }

    public void setState(Switch switch_) {
        Log.d(TAG, "setState has been called");
        int isEnabled = establishState();
	mSwitch.setChecked(isEnabled == 1 ? true : false);
        return;
    }

    public int establishState() {
        Log.d(TAG, "establishing state of boot animations");
        boolean anim_prop_1 = RootHelper.propExists("ro.kernel.android.bootanim=0");
        boolean anim_prop_1_isDisabled = RootHelper.propExists("#ro.kernel.android.bootanim");
        boolean anim_prop_2 = RootHelper.propExists("debug.sf.nobootanimation=1");
        boolean anim_prop_2_isDisabled = RootHelper.propExists("#debug.sf.nobootanimation");

        StringBuilder logging = new StringBuilder();
        // find if our bootanimation properties are set already
        if (anim_prop_1 && anim_prop_1_isDisabled) {
            logging.append("ro.kernel.android.bootanim was found disabled; ");
        } else if (anim_prop_1 && !anim_prop_1_isDisabled) {
            logging.append("ro.kernel.android.bootanim was found enabled; ");
        } else if (!anim_prop_1) {
            logging.append("ro.kernel.android.bootanim NOT FOUND; ");
        }
        if (anim_prop_2 && anim_prop_2_isDisabled) {
            logging.append("debug.sf.nobootanimation was found disabled; ");
        } else if (anim_prop_2 && !anim_prop_2_isDisabled) {
            logging.append("debug.sf.nobootanimation was found enabled; ");
        } else if (!anim_prop_2) {
            logging.append("debug.sf.nobootanimation NOT FOUND; ");
        }
        Log.d(TAG, logging.toString());

        if (anim_prop_1 && anim_prop_2 && !anim_prop_1 && !anim_prop_2) {
            //both properties are set and not disabled so we return on (1)
            Log.d(TAG, "returning 1");
            return 1;
        } else {
            return 0;
        }
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (mStateMachineEvent) {
            Log.d(TAG, "onCheckedChanged mStateMachineEvent is true returning");
            return;
        } else {
            Log.d(TAG, "onCheckedChanged mStateMachienEvent is false not returning");
        }

        boolean success_0 = false;
        boolean success_1 = false;

        if (!RootHelper.remountRW()) {
            throw new RuntimeException("we couldn't mount /system");
        }

        if (isChecked) {
            success_0 = RootHelper.runRootCommand(String.format(APPEND_CMD, "ro.kernel.android.bootanim", 0));
            success_1 = RootHelper.runRootCommand(String.format(APPEND_CMD, "debug.sf.nobootanimation", 1));
            Global.bootanimations_switch_on = true;
        } else if (!isChecked) {
            success_0 = RootHelper.killProp(String.format(KILL_PROP_CMD, "ro.kernel.android.bootanim"));
            success_1 = RootHelper.killProp(String.format(KILL_PROP_CMD, "debug.sf.nobootanimation"));
            Global.bootanimations_switch_on = false;
        }

        if (!success_0) {
            //TODO do something when we fail
            Log.d(TAG, "ro.kernel.android.bootanim failed");
        } else if (!success_1) {
            //TODO find a way to handle these failures gracefully
            Log.d(TAG, "debug.sf.nobootanimation failed");
        }

        if (!RootHelper.remountRO()) {
            Log.d(TAG, "Couldn't remount /system as read only");
            //TODO notify user of failure
        }
        return;
    }

    private void setSwitchChecked(boolean checked) {
        if (checked != mSwitch.isChecked()) {
            mStateMachineEvent = true;
            mSwitch.setChecked(checked);
            mStateMachineEvent = false;
        }
    }
}
