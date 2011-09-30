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

import android.net.Uri;
import android.os.Bundle;
import android.content.Intent;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;

import com.liquid.settings.R;

public class CreditsActivity extends PreferenceActivity
                implements OnPreferenceChangeListener {

    private static final String TEAM_LIQUID_PREF = "pref_team_liquid";
    private static final String LIQUID_PREF = "pref_liquid";
    private static final String LIQUIDZGRL_PREF = "pref_liquidzgrl";
    private static final String JBIRDVEGAS_PREF = "pref_jbirdvegas";
    private static final String REVNUMBERS_PREF = "pref_revnumbers";
    private static final String JDKORECLIPSE_PREF = "pref_jdkoreclipse";
    private static final String JRUMMY16_PREF = "pref_jrummy16";
    
    private ListPreference mTeamLiquid;
    private ListPreference mLiquid;
    private ListPreference mLiquidzgrl;
    private ListPreference mJbirdvegas;
    private ListPreference mRevNumbers;
    private ListPreference mJdkoreclipse;
    private ListPreference mJrummy16;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

        setTitle(R.string.credits_title);
        addPreferencesFromResource(R.xml.credits_settings);
        PreferenceScreen prefSet = getPreferenceScreen();

        mTeamLiquid = (ListPreference) prefSet.findPreference(TEAM_LIQUID_PREF);
        mTeamLiquid.setOnPreferenceChangeListener(this);

        mLiquid = (ListPreference) prefSet.findPreference(LIQUID_PREF);
        mLiquid.setOnPreferenceChangeListener(this);

        mLiquidzgrl = (ListPreference) prefSet.findPreference(LIQUIDZGRL_PREF);
        mLiquidzgrl.setOnPreferenceChangeListener(this);

        mJbirdvegas = (ListPreference) prefSet.findPreference(JBIRDVEGAS_PREF);
        mJbirdvegas.setOnPreferenceChangeListener(this);

        mRevNumbers = (ListPreference) prefSet.findPreference(REVNUMBERS_PREF);
        mRevNumbers.setOnPreferenceChangeListener(this);

        mJdkoreclipse = (ListPreference) prefSet.findPreference(JDKORECLIPSE_PREF);
        mJdkoreclipse.setOnPreferenceChangeListener(this);

        mJrummy16 = (ListPreference) prefSet.findPreference(JRUMMY16_PREF);
        mJrummy16.setOnPreferenceChangeListener(this);
    }

    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (newValue != null) {
            if (preference != null) {
                return launchBrowser(newValue.toString());
            }
        }

        return false;
    }

    private boolean launchBrowser(String urlValue) {
        if (urlValue != null) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlValue));
            startActivity(browserIntent);
			return true;
        }

        return false;
    }
}
