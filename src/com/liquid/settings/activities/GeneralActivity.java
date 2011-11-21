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

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.provider.Settings;

import com.liquid.settings.R;

public class GeneralActivity extends PreferenceActivity
                    implements OnPreferenceChangeListener {
        
    private static final String BACKLIGHT_SETTINGS = "backlight_settings";
    private static final String GENERAL_CATEGORY = "general_category";
    private static final String ELECTRON_BEAM_ANIMATION_ON = "electron_beam_animation_on";
    private static final String ELECTRON_BEAM_ANIMATION_OFF = "electron_beam_animation_off";
    private static final String HAPTIC_FEEDBACK_PREF = "haptic_feedback";
    private static final String NOTIF_ADB = "display_adb_usb_debugging_notif";
    private static final String KILL_APP = "kill_app_longpress_back";
    
    private static final String ROTATION_0_PREF = "pref_rotation_0";
    private static final String ROTATION_90_PREF = "pref_rotation_90";
    private static final String ROTATION_180_PREF = "pref_rotation_180";
    private static final String ROTATION_270_PREF = "pref_rotation_270";

    private static final int ROTATION_0_MODE = 8;
    private static final int ROTATION_90_MODE = 1;
    private static final int ROTATION_180_MODE = 2;
    private static final int ROTATION_270_MODE = 4;
    
    private CheckBoxPreference mRotation0Pref;
    private CheckBoxPreference mRotation90Pref;
    private CheckBoxPreference mRotation180Pref;
    private CheckBoxPreference mRotation270Pref;
    
    private PreferenceScreen mBacklightScreen;
    private CheckBoxPreference mElectronBeamAnimationOn;
    private CheckBoxPreference mElectronBeamAnimationOff;
    private CheckBoxPreference mHapticFeedbackPref;
    private CheckBoxPreference mKillAppPref;
    private CheckBoxPreference mNotifADBPref;
    
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        setTitle(R.string.general_title);
        addPreferencesFromResource(R.xml.general_settings);
        final PreferenceScreen prefSet = getPreferenceScreen();
        mBacklightScreen = (PreferenceScreen) prefSet.findPreference(BACKLIGHT_SETTINGS);

        if (((SensorManager) getSystemService(SENSOR_SERVICE)).getDefaultSensor(Sensor.TYPE_LIGHT) == null) {
            ((PreferenceCategory) prefSet.findPreference(GENERAL_CATEGORY))
                    .removePreference(mBacklightScreen);
        }

        /* Electron Beam control */
        mElectronBeamAnimationOn = (CheckBoxPreference)prefSet.findPreference(ELECTRON_BEAM_ANIMATION_ON);
        mElectronBeamAnimationOff = (CheckBoxPreference)prefSet.findPreference(ELECTRON_BEAM_ANIMATION_OFF);
        if (getResources().getBoolean(com.android.internal.R.bool.config_enableScreenAnimation)) {
            mElectronBeamAnimationOn.setChecked(Settings.System.getInt(getContentResolver(),
                    Settings.System.ELECTRON_BEAM_ANIMATION_ON,
                    getResources().getBoolean(com.android.internal.R.bool.config_enableScreenOnAnimation) ? 1 : 0) == 1);
            mElectronBeamAnimationOff.setChecked(Settings.System.getInt(getContentResolver(),
                    Settings.System.ELECTRON_BEAM_ANIMATION_OFF,
                    getResources().getBoolean(com.android.internal.R.bool.config_enableScreenOffAnimation) ? 1 : 0) == 1);
        } else {
            /* Hide Electron Beam controls if disabled */
            ((PreferenceCategory) prefSet.findPreference(GENERAL_CATEGORY))
                .removePreference(mElectronBeamAnimationOn);
            ((PreferenceCategory) prefSet.findPreference(GENERAL_CATEGORY))
                .removePreference(mElectronBeamAnimationOff);
        }

        mRotation0Pref = (CheckBoxPreference) prefSet.findPreference(ROTATION_0_PREF);
        mRotation90Pref = (CheckBoxPreference) prefSet.findPreference(ROTATION_90_PREF);
        mRotation180Pref = (CheckBoxPreference) prefSet.findPreference(ROTATION_180_PREF);
        mRotation270Pref = (CheckBoxPreference) prefSet.findPreference(ROTATION_270_PREF);
        
        int mode = Settings.System.getInt(getContentResolver(),
                        Settings.System.ACCELEROMETER_ROTATION_MODE,
                        ROTATION_0_MODE|ROTATION_90_MODE|ROTATION_270_MODE);
                        
        mRotation0Pref.setChecked((mode & ROTATION_0_MODE) != 0);
        mRotation90Pref.setChecked((mode & ROTATION_90_MODE) != 0);
        mRotation180Pref.setChecked((mode & ROTATION_180_MODE) != 0);
        mRotation270Pref.setChecked((mode & ROTATION_270_MODE) != 0);
        
        mHapticFeedbackPref = (CheckBoxPreference) prefSet.findPreference(HAPTIC_FEEDBACK_PREF);
        mHapticFeedbackPref.setChecked(Settings.System.getInt(
                getContentResolver(), 
                Settings.System.HAPTIC_FEEDBACK_ENABLED, 0) != 0);

        mNotifADBPref = (CheckBoxPreference)prefSet.findPreference(NOTIF_ADB);
        mNotifADBPref.setChecked(Settings.Secure.getInt(
                getContentResolver(),
                Settings.Secure.ADB_NOTIFY, 1) != 0);

        mKillAppPref = (CheckBoxPreference)prefSet.findPreference(KILL_APP);
        mKillAppPref.setChecked(Settings.Secure.getInt(
                getContentResolver(),
                Settings.Secure.KILL_APP_LONGPRESS_BACK, 0) != 0);
    }
    
    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        if (preference == mBacklightScreen) {
            startActivity(mBacklightScreen.getIntent());
            return true;
        } else if (preference == mElectronBeamAnimationOn) {
            Settings.System.putInt(getContentResolver(),
                    Settings.System.ELECTRON_BEAM_ANIMATION_ON,
                    mElectronBeamAnimationOn.isChecked() ? 1 : 0);
            return true;
        } else if (preference == mElectronBeamAnimationOff) {
            Settings.System.putInt(getContentResolver(),
                    Settings.System.ELECTRON_BEAM_ANIMATION_OFF,
                    mElectronBeamAnimationOff.isChecked() ? 1 : 0);
            return true;
        }
    	if (preference == mNotifADBPref) {
            Settings.Secure.putInt(getContentResolver(),
                    Settings.Secure.ADB_NOTIFY,
                    mNotifADBPref.isChecked() ? 1 : 0);
            return true;
        } else if (preference == mHapticFeedbackPref) {
            Settings.Secure.putInt(getContentResolver(),
                    Settings.System.HAPTIC_FEEDBACK_ENABLED,
                    mHapticFeedbackPref.isChecked() ? 1 : 0);
            return true;
        } else if (preference == mKillAppPref) {
            Settings.Secure.putInt(getContentResolver(),
                    Settings.Secure.KILL_APP_LONGPRESS_BACK,
                    mKillAppPref.isChecked() ? 1 : 0);
            return true;
        }
        if (preference == mRotation0Pref ||
            preference == mRotation90Pref ||
            preference == mRotation180Pref ||
            preference == mRotation270Pref) {
            int mode = 0;
            if (mRotation0Pref.isChecked()) mode |= ROTATION_0_MODE;
            if (mRotation90Pref.isChecked()) mode |= ROTATION_90_MODE;
            if (mRotation180Pref.isChecked()) mode |= ROTATION_180_MODE;
            if (mRotation270Pref.isChecked()) mode |= ROTATION_270_MODE;
            if (mode == 0) {
                mode |= ROTATION_0_MODE;
                mRotation0Pref.setChecked(true);
            }
            Settings.System.putInt(getContentResolver(),
                     Settings.System.ACCELEROMETER_ROTATION_MODE, mode);
            return true;
        }
        return false;
    }

    public boolean onPreferenceChange(Preference preference, Object objValue) {
        return false;
    }
}
