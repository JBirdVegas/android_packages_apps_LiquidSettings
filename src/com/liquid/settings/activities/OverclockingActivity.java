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

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;

import android.util.Log;
import android.os.Bundle;
import android.os.SystemProperties;
import android.preference.Preference;
import android.preference.ListPreference;
import android.preference.PreferenceScreen;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceActivity;

import com.liquid.settings.R;

public class OverclockingActivity extends PreferenceActivity
                implements Preference.OnPreferenceChangeListener {
        
    private static final String TAG = "OverclockingActivity";
    public static final String SOB_PREF = "pref_set_on_boot";
    public static final String SOB_PROP = "persist.sys.liquid.cpufreq.on";
    public static final String GOV_PREF = "pref_cpu_governor";
    public static final String GOV_PROP = "persist.sys.liquid.cpufreq.gov";
    public static final String MIN_FREQ_PREF = "pref_freq_min";
    public static final String MIN_FREQ_PROP = "persist.sys.liquid.cpufreq.min";
    public static final String MAX_FREQ_PREF = "pref_freq_max";
    public static final String MAX_FREQ_PROP = "persist.sys.liquid.cpufreq.max";

    public static final String GOVERNOR = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_governor";
    public static final String FREQ_MIN_FILE = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_min_freq";
    public static final String FREQ_MAX_FILE = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_max_freq";
    public static final String FREQ_LIST_FILE = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_available_frequencies";
    public static final String GOVERNORS_LIST_FILE = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_available_governors";
    
    private String mGovernorFormat;
    private String mMinFrequencyFormat;
    private String mMaxFrequencyFormat;

    private ListPreference mGovernorPref;
    private ListPreference mMinFrequencyPref;
    private ListPreference mMaxFrequencyPref;
    private CheckBoxPreference mFreqEnablePref;
    
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGovernorFormat = getString(R.string.governors_summary);
        mMinFrequencyFormat = getString(R.string.min_freq_summary);
        mMaxFrequencyFormat = getString(R.string.max_freq_summary);
        String[] availableGovernors = readOneLine(GOVERNORS_LIST_FILE).split(" ");
        String[] availableFrequencies = new String[0];
        String availableFrequenciesLine = readOneLine(FREQ_LIST_FILE);

        if (availableFrequenciesLine != null)
             availableFrequencies = availableFrequenciesLine.split(" ");
        String[] frequencies;
        String temp = "";

        frequencies = new String[availableFrequencies.length];
        for (int i = 0; i < frequencies.length; i++) {
            frequencies[i] = toMHz(availableFrequencies[i]);
        }

        setTitle(R.string.overclocking_title);
        addPreferencesFromResource(R.xml.overclocking_settings);
        PreferenceScreen PrefScreen = getPreferenceScreen();
		mFreqEnablePref = (CheckBoxPreference) PrefScreen.findPreference(SOB_PREF);
        
        temp = readOneLine(GOVERNOR);
        mGovernorPref = (ListPreference) PrefScreen.findPreference(GOV_PREF);
        mGovernorPref.setEntryValues(availableGovernors);
        mGovernorPref.setEntries(availableGovernors);
        mGovernorPref.setValue(temp);
        mGovernorPref.setSummary(String.format(mGovernorFormat, temp));
        mGovernorPref.setOnPreferenceChangeListener(this);

        temp = readOneLine(FREQ_MIN_FILE);
        mMinFrequencyPref = (ListPreference) PrefScreen.findPreference(MIN_FREQ_PREF);
        mMinFrequencyPref.setEntryValues(availableFrequencies);
        mMinFrequencyPref.setEntries(frequencies);
        mMinFrequencyPref.setValue(temp);
        mMinFrequencyPref.setSummary(String.format(mMinFrequencyFormat, toMHz(temp)));
        mMinFrequencyPref.setOnPreferenceChangeListener(this);

        temp = readOneLine(FREQ_MAX_FILE);
        mMaxFrequencyPref = (ListPreference) PrefScreen.findPreference(MAX_FREQ_PREF);
        mMaxFrequencyPref.setEntryValues(availableFrequencies);
        mMaxFrequencyPref.setEntries(frequencies);
        mMaxFrequencyPref.setValue(temp);
        mMaxFrequencyPref.setSummary(String.format(mMaxFrequencyFormat, toMHz(temp)));
        mMaxFrequencyPref.setOnPreferenceChangeListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        updateDisplay();
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
		if (preference == mFreqEnablePref) {
			SystemProperties.set(SOB_PROP, 
                    mFreqEnablePref.isChecked() ? "1" : "0");
			return true;
		}

        return false;
    }

    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String fname = "";
        if (newValue != null) {
            if (preference == mGovernorPref) {
                fname = GOVERNOR;
            } else if (preference == mMinFrequencyPref) {
                fname = FREQ_MIN_FILE;
            } else if (preference == mMaxFrequencyPref) {
                fname = FREQ_MAX_FILE;
            }
            if (writeOneLine(fname, (String) newValue)) {
                if (preference == mGovernorPref) {
                    SystemProperties.set(GOV_PROP, (String) newValue);
                    mGovernorPref.setSummary(String.format(mGovernorFormat, (String) newValue));
                } else if (preference == mMinFrequencyPref) {
                    SystemProperties.set(MIN_FREQ_PROP, (String) newValue);
                    mMinFrequencyPref.setSummary(String.format(mMinFrequencyFormat,
                            toMHz((String) newValue)));
                } else if (preference == mMaxFrequencyPref) {
                    SystemProperties.set(MAX_FREQ_PROP, (String) newValue);
                    mMaxFrequencyPref.setSummary(String.format(mMaxFrequencyFormat,
                            toMHz((String) newValue)));
                }
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

    public static String readOneLine(String fname) {
        BufferedReader br;
        String line = null;

        try {
            br = new BufferedReader(new FileReader(fname), 512);
            try {
                line = br.readLine();
            } finally {
                br.close();
            }
        } catch (Exception e) {
            Log.e(TAG, "IO Exception when reading /sys/ file", e);
        }

        return line;
    }

    public static boolean writeOneLine(String fname, String value) {
        try {
            FileWriter fw = new FileWriter(fname);
            try {
                fw.write(value);
            } finally {
                fw.close();
            }
        } catch (IOException e) {
            String Error = "Error writing to " + fname + ". Exception: ";
            Log.e(TAG, Error, e);
            return false;
        }

        return true;
    }

    private void updateDisplay() {
        String temp = "";
        temp = readOneLine(GOVERNOR);
        mGovernorPref.setValue(temp);
        SystemProperties.set(GOV_PROP, temp);
        mGovernorPref.setSummary(String.format(mGovernorFormat, temp));

        temp = readOneLine(FREQ_MIN_FILE);
        mMinFrequencyPref.setValue(temp);
        SystemProperties.set(MIN_FREQ_PROP, temp);
        mMinFrequencyPref.setSummary(String.format(mMinFrequencyFormat, toMHz(temp)));

        temp = readOneLine(FREQ_MAX_FILE);
        mMaxFrequencyPref.setValue(temp);
        SystemProperties.set(MAX_FREQ_PROP, temp);
        mMaxFrequencyPref.setSummary(String.format(mMaxFrequencyFormat, toMHz(temp)));
    }

    private String toMHz(String mhzString) {
        return new StringBuilder().append(Integer.valueOf(mhzString) / 1000).append(" MHz").toString();
    }
}
