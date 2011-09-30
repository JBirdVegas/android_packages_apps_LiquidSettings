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

import android.content.Intent;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.provider.Settings;

import com.liquid.settings.R;
import com.liquid.settings.utilities.ShortcutPickHelper;

public class LockCustomAppActivity extends PreferenceActivity
                    implements ShortcutPickHelper.OnPickListener {

    private static final String PREF_LOCK_CUSTOM1_APP_TOGGLE = "pref_lock_custom1_app_toggle";
    private static final String PREF_LOCK_CUSTOM1_APP_ACTIVITY = "pref_lock_custom1_app_activity";
    private static final String PREF_LOCK_CUSTOM2_APP_TOGGLE = "pref_lock_custom2_app_toggle";
    private static final String PREF_LOCK_CUSTOM2_APP_ACTIVITY = "pref_lock_custom2_app_activity";
    private static final String PREF_LOCK_CUSTOM3_APP_TOGGLE = "pref_lock_custom3_app_toggle";
    private static final String PREF_LOCK_CUSTOM3_APP_ACTIVITY = "pref_lock_custom3_app_activity";
    private static final String PREF_LOCK_CUSTOM4_APP_TOGGLE = "pref_lock_custom4_app_toggle";
    private static final String PREF_LOCK_CUSTOM4_APP_ACTIVITY = "pref_lock_custom4_app_activity";
    private static final String PREF_LOCK_CUSTOM5_APP_TOGGLE = "pref_lock_custom5_app_toggle";
    private static final String PREF_LOCK_CUSTOM5_APP_ACTIVITY = "pref_lock_custom5_app_activity";
    private static final String PREF_LOCK_CUSTOM6_APP_TOGGLE = "pref_lock_custom6_app_toggle";
    private static final String PREF_LOCK_CUSTOM6_APP_ACTIVITY = "pref_lock_custom6_app_activity";

    private CheckBoxPreference mLockCustom1AppTogglePref;
    private CheckBoxPreference mLockCustom2AppTogglePref;
    private CheckBoxPreference mLockCustom3AppTogglePref;
    private CheckBoxPreference mLockCustom4AppTogglePref;
    private CheckBoxPreference mLockCustom5AppTogglePref;
    private CheckBoxPreference mLockCustom6AppTogglePref;

    private Preference mLockCustom1AppActivityPref;
    private Preference mLockCustom2AppActivityPref;
    private Preference mLockCustom3AppActivityPref;
    private Preference mLockCustom4AppActivityPref;
    private Preference mLockCustom5AppActivityPref;
    private Preference mLockCustom6AppActivityPref;
    private ShortcutPickHelper mPicker;
    private int mKeyNumber = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.lock_custom_app_title);
        addPreferencesFromResource(R.xml.lock_custom_app_settings);
        PreferenceScreen prefSet = getPreferenceScreen();

        mLockCustom1AppTogglePref = (CheckBoxPreference) prefSet
                .findPreference(PREF_LOCK_CUSTOM1_APP_TOGGLE);
        mLockCustom1AppTogglePref.setChecked(Settings.System.getInt(getContentResolver(),
                Settings.System.LOCK_CUSTOM1_APP_TOGGLE, 0) == 1);
        mLockCustom1AppActivityPref = (Preference) prefSet
                .findPreference(PREF_LOCK_CUSTOM1_APP_ACTIVITY);

        mLockCustom2AppTogglePref = (CheckBoxPreference) prefSet
                .findPreference(PREF_LOCK_CUSTOM2_APP_TOGGLE);
        mLockCustom2AppTogglePref.setChecked(Settings.System.getInt(getContentResolver(),
                Settings.System.LOCK_CUSTOM2_APP_TOGGLE, 0) == 1);
        mLockCustom2AppActivityPref = (Preference) prefSet
                .findPreference(PREF_LOCK_CUSTOM2_APP_ACTIVITY);

        mLockCustom3AppTogglePref = (CheckBoxPreference) prefSet
                .findPreference(PREF_LOCK_CUSTOM3_APP_TOGGLE);
        mLockCustom3AppTogglePref.setChecked(Settings.System.getInt(getContentResolver(),
                Settings.System.LOCK_CUSTOM3_APP_TOGGLE, 0) == 1);
        mLockCustom3AppActivityPref = (Preference) prefSet
                .findPreference(PREF_LOCK_CUSTOM3_APP_ACTIVITY);

        mLockCustom4AppTogglePref = (CheckBoxPreference) prefSet
                .findPreference(PREF_LOCK_CUSTOM4_APP_TOGGLE);
        mLockCustom4AppTogglePref.setChecked(Settings.System.getInt(getContentResolver(),
                Settings.System.LOCK_CUSTOM4_APP_TOGGLE, 0) == 1);
        mLockCustom4AppActivityPref = (Preference) prefSet
                .findPreference(PREF_LOCK_CUSTOM4_APP_ACTIVITY);

        mLockCustom5AppTogglePref = (CheckBoxPreference) prefSet
                .findPreference(PREF_LOCK_CUSTOM5_APP_TOGGLE);
        mLockCustom5AppTogglePref.setChecked(Settings.System.getInt(getContentResolver(),
                Settings.System.LOCK_CUSTOM5_APP_TOGGLE, 0) == 1);
        mLockCustom5AppActivityPref = (Preference) prefSet
                .findPreference(PREF_LOCK_CUSTOM5_APP_ACTIVITY);

        mLockCustom6AppTogglePref = (CheckBoxPreference) prefSet
                .findPreference(PREF_LOCK_CUSTOM6_APP_TOGGLE);
        mLockCustom6AppTogglePref.setChecked(Settings.System.getInt(getContentResolver(),
                Settings.System.LOCK_CUSTOM6_APP_TOGGLE, 0) == 1);
        mLockCustom6AppActivityPref = (Preference) prefSet
                .findPreference(PREF_LOCK_CUSTOM6_APP_ACTIVITY);
        mPicker = new ShortcutPickHelper(this, this);
    }

    @Override
    public void onResume() {
        super.onResume();

        setAppSummary(mLockCustom1AppActivityPref, Settings.System.LOCK_CUSTOM1_APP_ACTIVITY);
        setAppSummary(mLockCustom2AppActivityPref, Settings.System.LOCK_CUSTOM2_APP_ACTIVITY);
        setAppSummary(mLockCustom3AppActivityPref, Settings.System.LOCK_CUSTOM3_APP_ACTIVITY);
        setAppSummary(mLockCustom4AppActivityPref, Settings.System.LOCK_CUSTOM4_APP_ACTIVITY);
        setAppSummary(mLockCustom5AppActivityPref, Settings.System.LOCK_CUSTOM5_APP_ACTIVITY);
        setAppSummary(mLockCustom6AppActivityPref, Settings.System.LOCK_CUSTOM6_APP_ACTIVITY);
    }

    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        boolean value;
        if (preference == mLockCustom1AppTogglePref) {
            value = mLockCustom1AppTogglePref.isChecked();
            Settings.System.putInt(getContentResolver(),
                    Settings.System.LOCK_CUSTOM1_APP_TOGGLE, value ? 1 : 0);
            return true;
        } else if (preference == mLockCustom2AppTogglePref) {
            value = mLockCustom2AppTogglePref.isChecked();
            Settings.System.putInt(getContentResolver(),
                    Settings.System.LOCK_CUSTOM2_APP_TOGGLE, value ? 1 : 0);
            return true;
        } else if (preference == mLockCustom3AppTogglePref) {
            value = mLockCustom3AppTogglePref.isChecked();
            Settings.System.putInt(getContentResolver(),
                    Settings.System.LOCK_CUSTOM3_APP_TOGGLE, value ? 1 : 0);
            return true;
        } else if (preference == mLockCustom4AppTogglePref) {
            value = mLockCustom4AppTogglePref.isChecked();
            Settings.System.putInt(getContentResolver(),
                    Settings.System.LOCK_CUSTOM4_APP_TOGGLE, value ? 1 : 0);
            return true;
        } else if (preference == mLockCustom5AppTogglePref) {
            value = mLockCustom5AppTogglePref.isChecked();
            Settings.System.putInt(getContentResolver(),
                    Settings.System.LOCK_CUSTOM5_APP_TOGGLE, value ? 1 : 0);
            return true;
        } else if (preference == mLockCustom6AppTogglePref) {
            value = mLockCustom6AppTogglePref.isChecked();
            Settings.System.putInt(getContentResolver(),
                    Settings.System.LOCK_CUSTOM6_APP_TOGGLE, value ? 1 : 0);
            return true;
        } else if (preference == mLockCustom1AppActivityPref) {
            mKeyNumber = 1;
            mPicker.pickShortcut();
            return true;
        } else if (preference == mLockCustom2AppActivityPref) {
            mKeyNumber = 2;
            mPicker.pickShortcut();
            return true;
        } else if (preference == mLockCustom3AppActivityPref) {
            mKeyNumber = 3;
            mPicker.pickShortcut();
            return true;
        } else if (preference == mLockCustom4AppActivityPref) {
            mKeyNumber = 4;
            mPicker.pickShortcut();
            return true;
        } else if (preference == mLockCustom5AppActivityPref) {
            mKeyNumber = 5;
            mPicker.pickShortcut();
            return true;
        } else if (preference == mLockCustom6AppActivityPref) {
            mKeyNumber = 6;
            mPicker.pickShortcut();
            return true;
        }
        return false;
    }

    private void setAppSummary(Preference pref, String key) {
        String value = Settings.System.getString(getContentResolver(), key);
        pref.setSummary(mPicker.getFriendlyNameForUri(value));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPicker.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void shortcutPicked(String uri, String friendlyName, boolean isApplication) {
        String key;
        Preference pref;
        
        switch (mKeyNumber) {
            case 1:
                key = Settings.System.LOCK_CUSTOM1_APP_ACTIVITY;
                pref = mLockCustom1AppActivityPref;
                break;
            case 2:
                key = Settings.System.LOCK_CUSTOM2_APP_ACTIVITY;
                pref = mLockCustom2AppActivityPref;
                break;
            case 3:
                key = Settings.System.LOCK_CUSTOM3_APP_ACTIVITY;
                pref = mLockCustom3AppActivityPref;
                break;
            case 4:
                key = Settings.System.LOCK_CUSTOM4_APP_ACTIVITY;
                pref = mLockCustom4AppActivityPref;
                break;
            case 5:
                key = Settings.System.LOCK_CUSTOM5_APP_ACTIVITY;
                pref = mLockCustom5AppActivityPref;
                break;
            case 6:
                key = Settings.System.LOCK_CUSTOM6_APP_ACTIVITY;
                pref = mLockCustom6AppActivityPref;
                break;
            default:
                return;
        }
        
        if (Settings.System.putString(getContentResolver(), key, uri)) {
            pref.setSummary(friendlyName);
        }
    }
}
