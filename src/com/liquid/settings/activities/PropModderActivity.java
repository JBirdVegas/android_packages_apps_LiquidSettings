/*
** Copyright (C) 2011 The Liquid Settings Project
**
** Licensed under the Apache License, Version 2.0 (the "License"); 
** you may not use this file except in compliance with the License. 
** You may obtain a copy of the License at 
**
**     http://www.apache.org/licenses/LICENSE-2.0 
**
** Unless required by applicable law or agreed to in writing, software 
** distributed under the License is distributed on an "AS IS" BASIS, 
** WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
** See the License for the specific language governing permissions and 
** limitations under the License.
*/

package com.liquid.settings.activities;

import java.io.File;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemProperties;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.util.Log;

import com.liquid.settings.R;
import com.liquid.settings.utilities.RootHelper;

public class PropModderActivity extends PreferenceActivity 
        implements Preference.OnPreferenceChangeListener {

    private static final String TAG = "*LGB*DEBUG";
    private static final String APPEND_CMD = "echo \"%s=%s\" >> /system/build.prop";
    private static final String KILL_PROP_CMD = "busybox sed -i \"/%s/D\" /system/build.prop";
    private static final String REPLACE_CMD = "busybox sed -i \"/%s/ c %<s=%s\" /system/build.prop";
    private static final String SHOWBUILD_PATH = "/system/tmp/showbuild";
    private static final String DISABLE = "disable";
    private static final String SD_SPEED_CMD = "busybox sed -i \"/179:0/ c echo %s > /sys/devices/virtual/bdi/179:0/read_ahead_kb\" /system/etc/init.d/90liquid";
    private static final String SD_SPEED_ONtheFLY = "echo %s > /sys/devices/virtual/bdi/179:0/read_ahead_kb";
    private static final String LOGCAT_CMD = "busybox sed -i \"/rm -f /dev/log/main c %s\" /system/etc/init.d/90liquid";

    private static final String VM_HEAPSIZE_PREF = "pref_vm_heapsize";
    private static final String VM_HEAPSIZE_PROP = "dalvik.vm.heapsize";
    private static final String VM_HEAPSIZE_PERSIST_PROP = "persist.vm_heapsize";
    private static final String VM_HEAPSIZE_DEFAULT = System.getProperty(VM_HEAPSIZE_PROP);

    private static final String LCD_DENSITY_PREF = "pref_lcd_density";
    private static final String LCD_DENSITY_PROP = "ro.sf.lcd_density";
    private static final String LCD_DENSITY_PERSIST_PROP = "persist.lcd_density";
    private static final String LCD_DENSITY_DEFAULT = System.getProperty(LCD_DENSITY_PROP);

    private static final String MAX_EVENTS_PREF = "pref_max_events";
    private static final String MAX_EVENTS_PROP = "windowsmgr.max_events_per_sec";
    private static final String MAX_EVENTS_PERSIST_PROP = "persist.max_events";
    private static final String MAX_EVENTS_DEFAULT = System.getProperty(MAX_EVENTS_PROP);

    private static final String RING_DELAY_PREF = "pref_ring_delay";
    private static final String RING_DELAY_PROP = "ro.telephony.call_ring.delay";
    private static final String RING_DELAY_PERSIST_PROP = "persist.call_ring.delay";
    private static final String RING_DELAY_DEFAULT = System.getProperty(RING_DELAY_PROP);

    private static final String FAST_UP_PREF = "pref_fast_upload";
    private static final String FAST_UP_PROP = "ro.ril.hsxpa";
    private static final String FAST_UP_PERSIST_PROP = "persist.fast_up";
    private static final String FAST_UP_DEFAULT = System.getProperty(FAST_UP_PROP);

    private static final String PROX_DELAY_PREF = "pref_prox_delay";
    private static final String PROX_DELAY_PROP = "mot.proximity.delay";
    private static final String PROX_DELAY_PERSIST_PROP = "persist.prox.delay";
    private static final String PROX_DELAY_DEFAULT = System.getProperty(PROX_DELAY_PROP);

    private static final String LOGCAT_PREF = "pref_rem_logcat";
    private static final String LOGCAT_ON = "rm -f /dev/log/main ";
    private static final String LOGCAT_OFF = "#rm -f /dev/log/main";

    private static final String WIFI_SCAN_PREF = "pref_wifi_interval";
    private static final String WIFI_SCAN_PROP = "wifi.supplicant_scan_interval";
    private static final String WIFI_SCAN_PERSIST_PROP = "persist.wifi_scan_interval";
    private static final String WIFI_SCAN_DEFAULT = System.getProperty(WIFI_SCAN_PROP);

    private static final String SLEEP_PREF = "pref_sleep";
    private static final String SLEEP_PROP = "pm.sleep_mode";
    private static final String SLEEP_PERSIST_PROP = "persist.sleep";
    private static final String SLEEP_DEFAULT = System.getProperty(SLEEP_PROP);

    private static final String TCP_STACK_PREF = "pref_tcp_stack";
    private static final String TCP_STACK_PERSIST_PROP = "persist_tcp_stack";
    private static final String TCP_STACK_PROP_0 = "net.tcp.buffersize.default";
    private static final String TCP_STACK_PROP_1 = "net.tcp.buffersize.wifi";
    private static final String TCP_STACK_PROP_2 = "net.tcp.buffersize.umts";
    private static final String TCP_STACK_PROP_3 = "net.tcp.buffersize.gprs";
    private static final String TCP_STACK_PROP_4 = "net.tcp.buffersize.edge";
    private static final String TCP_STACK_BUFFER = "4096,87380,256960,4096,16384,256960";

    private static final String CHECK_IN_PREF = "pref_check_in";
    private static final String CHECK_IN_PERSIST_PROP = "persist_check_in";
    private static final String CHECK_IN_PROP = "ro.config.nocheckin";
    private static final String CHECK_IN_PROP_HTC = "ro.config.htc.nocheckin";

    private static final String SD_SPEED_PREF = "pref_sd_speed";

    public static final String THREE_G_PREF = "pref_3g_speed";
    public static final String THREE_G_PERSIST_PROP = "persist_3g_speed";
    public static final String THREE_G_PROP_0 = "ro.ril.enable.3g.prefix";
    public static final String THREE_G_PROP_1 = "ro.ril.hep";
    public static final String THREE_G_PROP_2 = FAST_UP_PROP;
    public static final String THREE_G_PROP_3 = "ro.ril.enable.dtm";
    public static final String THREE_G_PROP_4 = "ro.ril.gprsclass";
    public static final String THREE_G_PROP_5 = "ro.ril.hsdpa.category";
    public static final String THREE_G_PROP_6 = "ro.ril.enable.a53";
    public static final String THREE_G_PROP_7 = "ro.ril.hsupa.category";

    public static final String GPU_PREF = "pref_gpu";
    public static final String GPU_PERSIST_PROP = "persist_gpu";
    public static final String GPU_PROP = "debug.sf.hw";

    private ListPreference mHeapsizePref;
    private ListPreference mLcdDensityPref;
    private ListPreference mMaxEventsPref;
    private ListPreference mRingDelayPref;
    private ListPreference mFastUpPref;
    private ListPreference mProxDelayPref;
    private CheckBoxPreference mLogcatPref;
    private ListPreference mWifiScanPref;
    private ListPreference mSleepPref;
    private CheckBoxPreference mTcpStackPref;
    private CheckBoxPreference mCheckInPref;
    private ListPreference mSdSpeedPref;
    private CheckBoxPreference m3gSpeedPref;
    private CheckBoxPreference mGpuPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.propmodder_title);
        addPreferencesFromResource(R.xml.propmodder_settings);
        PreferenceScreen prefSet = getPreferenceScreen();

        mHeapsizePref = (ListPreference) prefSet.findPreference(VM_HEAPSIZE_PREF);
        mHeapsizePref.setValue(SystemProperties.get(VM_HEAPSIZE_PERSIST_PROP,
                SystemProperties.get(VM_HEAPSIZE_PROP, VM_HEAPSIZE_DEFAULT)));
        mHeapsizePref.setOnPreferenceChangeListener(this);

        mLcdDensityPref = (ListPreference) prefSet.findPreference(LCD_DENSITY_PREF);
        mLcdDensityPref.setValue(SystemProperties.get(LCD_DENSITY_PERSIST_PROP,
                SystemProperties.get(LCD_DENSITY_PROP, LCD_DENSITY_DEFAULT)));
        mLcdDensityPref.setOnPreferenceChangeListener(this);

        mMaxEventsPref = (ListPreference) prefSet.findPreference(MAX_EVENTS_PREF);
        mMaxEventsPref.setValue(SystemProperties.get(MAX_EVENTS_PERSIST_PROP,
                SystemProperties.get(MAX_EVENTS_PROP, MAX_EVENTS_DEFAULT)));
        mMaxEventsPref.setOnPreferenceChangeListener(this);

        mRingDelayPref = (ListPreference) prefSet.findPreference(RING_DELAY_PREF);
        mRingDelayPref.setValue(SystemProperties.get(RING_DELAY_PERSIST_PROP,
                SystemProperties.get(RING_DELAY_PROP, RING_DELAY_DEFAULT)));
        mRingDelayPref.setOnPreferenceChangeListener(this);

        mFastUpPref = (ListPreference) prefSet.findPreference(FAST_UP_PREF);
        mFastUpPref.setValue(SystemProperties.get(FAST_UP_PERSIST_PROP,
                SystemProperties.get(FAST_UP_PROP, FAST_UP_DEFAULT)));
        mFastUpPref.setOnPreferenceChangeListener(this);

        mProxDelayPref = (ListPreference) prefSet.findPreference(PROX_DELAY_PREF);
        mProxDelayPref.setValue(SystemProperties.get(PROX_DELAY_PERSIST_PROP,
                SystemProperties.get(PROX_DELAY_PROP, PROX_DELAY_DEFAULT)));
        mProxDelayPref.setOnPreferenceChangeListener(this);

        mLogcatPref = (CheckBoxPreference) prefSet.findPreference(LOGCAT_PREF);
        boolean log = false;
        log = RootHelper.runRootCommand("grep -q \"#rm -f /dev/log/main\" /system/etc/init.d/90liquid");
        mLogcatPref.setChecked(!log);

        mWifiScanPref = (ListPreference) prefSet.findPreference(WIFI_SCAN_PREF);
        mWifiScanPref.setValue(SystemProperties.get(WIFI_SCAN_PERSIST_PROP,
                SystemProperties.get(WIFI_SCAN_PROP, WIFI_SCAN_DEFAULT)));
        mWifiScanPref.setOnPreferenceChangeListener(this);

        mSleepPref = (ListPreference) prefSet.findPreference(SLEEP_PREF);
        mSleepPref.setValue(SystemProperties.get(SLEEP_PERSIST_PROP,
                SystemProperties.get(SLEEP_PROP, SLEEP_DEFAULT)));
        mSleepPref.setOnPreferenceChangeListener(this);

        mTcpStackPref = (CheckBoxPreference) prefSet.findPreference(TCP_STACK_PREF);
        boolean tcpstack0 = SystemProperties.getBoolean(TCP_STACK_PROP_0, true);
        boolean tcpstack1 = SystemProperties.getBoolean(TCP_STACK_PROP_1, true);
        boolean tcpstack2 = SystemProperties.getBoolean(TCP_STACK_PROP_2, true);
        boolean tcpstack3 = SystemProperties.getBoolean(TCP_STACK_PROP_3, true);
        boolean tcpstack4 = SystemProperties.getBoolean(TCP_STACK_PROP_4, true);
        mTcpStackPref.setChecked(SystemProperties.getBoolean(
                TCP_STACK_PERSIST_PROP, tcpstack0 && tcpstack1 && tcpstack2 && tcpstack3 && tcpstack4));

        mCheckInPref = (CheckBoxPreference) prefSet.findPreference(CHECK_IN_PREF);
        boolean checkin = SystemProperties.getBoolean(CHECK_IN_PROP, true);
        mCheckInPref.setChecked(SystemProperties.getBoolean(
                CHECK_IN_PERSIST_PROP, !checkin));

        mSdSpeedPref = (ListPreference) prefSet.findPreference(SD_SPEED_PREF);
        mSdSpeedPref.setOnPreferenceChangeListener(this);

        File tmpDir = new File("/system/tmp");
        boolean exists = tmpDir.exists();

        if (!exists) {
            try {
                Log.d(TAG, "We need to make /system/tmp dir");
                RootHelper.remountRW();
                RootHelper.runRootCommand("mkdir /system/tmp");
            } finally {
                RootHelper.remountRO();
            }
        }

        m3gSpeedPref = (CheckBoxPreference) prefSet.findPreference(THREE_G_PREF);
        boolean speed3g0 = SystemProperties.getBoolean(THREE_G_PROP_0, false);
        boolean speed3g1 = SystemProperties.getBoolean(THREE_G_PROP_1, false);
        boolean speed3g2 = SystemProperties.getBoolean(THREE_G_PROP_2, false);
        boolean speed3g3 = SystemProperties.getBoolean(THREE_G_PROP_3, false);
        boolean speed3g4 = SystemProperties.getBoolean(THREE_G_PROP_4, false);
        boolean speed3g5 = SystemProperties.getBoolean(THREE_G_PROP_5, false);
        boolean speed3g6 = SystemProperties.getBoolean(THREE_G_PROP_6, false);
        boolean speed3g7 = SystemProperties.getBoolean(THREE_G_PROP_7, false);
        m3gSpeedPref.setChecked(SystemProperties.getBoolean(THREE_G_PERSIST_PROP, speed3g0 && speed3g1 && speed3g2 && speed3g3 && speed3g4 && speed3g5 && speed3g6 && speed3g7));

        mGpuPref = (CheckBoxPreference) prefSet.findPreference(GPU_PREF);
        boolean gpu = SystemProperties.getBoolean(GPU_PROP, false);
        mGpuPref.setChecked(SystemProperties.getBoolean(GPU_PERSIST_PROP, gpu));
    }

    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (newValue != null) {
            if (preference == mHeapsizePref) {
                return doMod(VM_HEAPSIZE_PERSIST_PROP, VM_HEAPSIZE_PROP,
                        newValue.toString());
            } else if (preference == mLcdDensityPref) {
                return doMod(LCD_DENSITY_PERSIST_PROP, LCD_DENSITY_PROP,
                        newValue.toString());
            } else if (preference == mMaxEventsPref) {
                return doMod(MAX_EVENTS_PERSIST_PROP, MAX_EVENTS_PROP,
                        newValue.toString());
            } else if (preference == mRingDelayPref) {
                return doMod(RING_DELAY_PERSIST_PROP, RING_DELAY_PROP,
                        newValue.toString());
            } else if (preference == mFastUpPref) {
                return doMod(FAST_UP_PERSIST_PROP, FAST_UP_PROP,
                        newValue.toString());
            } else if (preference == mProxDelayPref) {
                 return doMod(PROX_DELAY_PERSIST_PROP, PROX_DELAY_PROP,
                        newValue.toString());
            } else if (preference == mWifiScanPref) {
                 return doMod(WIFI_SCAN_PERSIST_PROP, WIFI_SCAN_PROP,
                        newValue.toString());
            } else if (preference == mSleepPref) {
                 return doMod(SLEEP_PERSIST_PROP, SLEEP_PROP,
                        newValue.toString());
            } else if (preference == mSdSpeedPref) {
                 return doMod(null, "sdspeed", newValue.toString());
            }
        }

        return false;
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        boolean value;
        if (preference == mTcpStackPref) {
            Log.d(TAG, "mTcpStackPref.onPreferenceTreeClick()");
            value = mTcpStackPref.isChecked();
            return doMod(null, TCP_STACK_PROP_0, String.valueOf(value ? TCP_STACK_BUFFER : DISABLE))
                    && doMod(null, TCP_STACK_PROP_1, String.valueOf(value ? TCP_STACK_BUFFER : DISABLE))
                    && doMod(null, TCP_STACK_PROP_2, String.valueOf(value ? TCP_STACK_BUFFER : DISABLE))
                    && doMod(null, TCP_STACK_PROP_3, String.valueOf(value ? TCP_STACK_BUFFER : DISABLE))
                    && doMod(TCP_STACK_PERSIST_PROP, TCP_STACK_PROP_4, String.valueOf(value ? TCP_STACK_BUFFER : DISABLE));
        } else if (preference == mCheckInPref) {
            value = mCheckInPref.isChecked();
            doMod(null, CHECK_IN_PROP_HTC, String.valueOf(value ? 1 : DISABLE));
            return doMod(CHECK_IN_PERSIST_PROP, CHECK_IN_PROP, String.valueOf(value ? 1 : DISABLE));
        } else if (preference == mLogcatPref) {
            value = mLogcatPref.isChecked();
            return RootHelper.runRootCommand(String.format(LOGCAT_CMD, String.valueOf(value ? LOGCAT_ON : LOGCAT_OFF)));
        } else if (preference == m3gSpeedPref) {
            value = m3gSpeedPref.isChecked();
            return doMod(THREE_G_PERSIST_PROP, THREE_G_PROP_0, String.valueOf(value ? 1 : DISABLE))
            && doMod(null, THREE_G_PROP_1, String.valueOf(value ? 1 : DISABLE))
            && doMod(null, THREE_G_PROP_2, String.valueOf(value ? 2 : DISABLE))
            && doMod(null, THREE_G_PROP_3, String.valueOf(value ? 1 : DISABLE))
            && doMod(null, THREE_G_PROP_4, String.valueOf(value ? 12 : DISABLE))
            && doMod(null, THREE_G_PROP_5, String.valueOf(value ? 8 : DISABLE))
            && doMod(null, THREE_G_PROP_6, String.valueOf(value ? 1 : DISABLE))
            && doMod(null, THREE_G_PROP_7, String.valueOf(value ? 5 : DISABLE));
        } else if (preference == mGpuPref) {
            value = mGpuPref.isChecked();
            return doMod(GPU_PERSIST_PROP, GPU_PROP, String.valueOf(value ? 1 : DISABLE));
        }
    return false;
    }

    private boolean doMod(String persist, String key, String value) {
        if (persist != null) {
            SystemProperties.set(persist, value);
        }

        Log.d(TAG, String.format("Calling script with args '%s' and '%s", key, value));
        RootHelper.backupBuildProp();

        if (!RootHelper.remountRW()) {
            throw new RuntimeException("Could not remount /system rw");
        }

        boolean success = false;

        try {
            if (key.equals("sdspeed")) {
                Log.d(TAG, String.format("we are modding sdcard read ahead: %s", value));
                RootHelper.runRootCommand(String.format(SD_SPEED_ONtheFLY, value));
                success = RootHelper.runRootCommand(String.format(SD_SPEED_CMD, value));
            } else {
                if (RootHelper.propExists(key)) {
                    if (value.equals(DISABLE)) {
                        Log.d(TAG, String.format("value == %s", DISABLE));
                        success = RootHelper.killProp(String.format(KILL_PROP_CMD, key));
                    } else {
                        Log.d(TAG, String.format("value != %s", DISABLE));
                        success = RootHelper.runRootCommand(String.format(REPLACE_CMD, key, value));
                    }
                } else {
                Log.d(TAG, "append command starting");
                success = RootHelper.runRootCommand(String.format(APPEND_CMD, key, value));
                }
            }

            if (!success) {
                RootHelper.restoreBuildProp();
            }
        } finally {
            RootHelper.remountRO();
        }

        return success;
    }
}
