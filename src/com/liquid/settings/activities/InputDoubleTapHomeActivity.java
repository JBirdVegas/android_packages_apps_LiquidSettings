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

public class InputDoubleTapHomeActivity extends PreferenceActivity
                         implements ShortcutPickHelper.OnPickListener {
                        
    private static final String INPUT_DOUBLE_TAP_HOME_APP_TOGGLE = "pref_input_double_tap_home_app_toggle";
    private static final String INPUT_DOUBLE_TAP_HOME_APP_ACTIVITY = "pref_input_double_tap_home_app_activity";

    private CheckBoxPreference mCustomDoubleTapHomeAppTogglePref;
    private Preference mCustomDoubleTapHomeAppActivityPref;
    private ShortcutPickHelper mPicker;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.input_double_tap_home_key_title);
        addPreferencesFromResource(R.xml.input_double_tap_home_settings);
        PreferenceScreen prefSet = getPreferenceScreen();

        mCustomDoubleTapHomeAppTogglePref = (CheckBoxPreference) prefSet
                .findPreference(INPUT_DOUBLE_TAP_HOME_APP_TOGGLE);
        mCustomDoubleTapHomeAppTogglePref.setChecked(Settings.System.getInt(getContentResolver(),
                Settings.System.USE_CUSTOM_DOUBLE_TAP_KEY_TOGGLE, 0) == 1);
        mCustomDoubleTapHomeAppActivityPref = (Preference) prefSet
                .findPreference(INPUT_DOUBLE_TAP_HOME_APP_ACTIVITY);
        mPicker = new ShortcutPickHelper(this, this);        
    }

    @Override
    public void onResume() {
        super.onResume();
        
        String value = Settings.System.getString(getContentResolver(),
                Settings.System.USE_CUSTOM_DOUBLE_TAP_ACTIVITY);
        mCustomDoubleTapHomeAppActivityPref.setSummary(mPicker.getFriendlyNameForUri(value));
    }

    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        boolean value;
        if (preference == mCustomDoubleTapHomeAppTogglePref) {
            value = mCustomDoubleTapHomeAppTogglePref.isChecked();
            Settings.System.putInt(getContentResolver(),
                    Settings.System.USE_CUSTOM_DOUBLE_TAP_KEY_TOGGLE, value ? 1 : 0);
            return true;
        } else if (preference == mCustomDoubleTapHomeAppActivityPref) {
            mPicker.pickShortcut();
            return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPicker.onActivityResult(requestCode, resultCode, data);
    }
    
    @Override
    public void shortcutPicked(String uri, String friendlyName, boolean isApplication) {
        if (Settings.System.putString(getContentResolver(), 
                Settings.System.USE_CUSTOM_DOUBLE_TAP_ACTIVITY, uri)) {
            mCustomDoubleTapHomeAppActivityPref.setSummary(friendlyName);
        }
    }
}
