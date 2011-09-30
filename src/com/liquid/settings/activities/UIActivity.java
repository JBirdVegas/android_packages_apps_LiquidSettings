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

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.provider.Settings;

import com.liquid.settings.R;

public class UIActivity extends PreferenceActivity 
                implements OnPreferenceChangeListener {

    private static final String NOTIFICATION_TRACKBALL = "notification_colors";
    private static final String GENERAL_CATEGORY = "general_category";
    private static final String PINCH_REFLOW_PREF = "pref_pinch_reflow";
    private static final String RENDER_EFFECT_PREF = "pref_render_effect";
    private static final String POWER_PROMPT_PREF = "power_dialog_prompt";
    private static final String OVERSCROLL_PREF = "pref_overscroll_effect";
    private static final String OVERSCROLL_COLOR = "pref_overscroll_color";
    private static final String OVERSCROLL_WEIGHT_PREF = "pref_overscroll_weight";

    private PreferenceScreen mTrackballScreen;
    private CheckBoxPreference mPinchReflowPref;
    private CheckBoxPreference mPowerPromptPref;
    private ListPreference mRenderEffectPref;
    private ListPreference mOverscrollPref;
    private ListPreference mOverscrollColor;
    private ListPreference mOverscrollWeightPref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.ui_title);
        addPreferencesFromResource(R.xml.ui_settings);
        PreferenceScreen prefSet = getPreferenceScreen();
        mTrackballScreen = (PreferenceScreen) prefSet.findPreference(NOTIFICATION_TRACKBALL);
        boolean hasDualLed = (getResources().getBoolean(R.bool.has_dual_notification_led)
                || getResources().getBoolean(R.bool.has_mixable_dual_notification_led));
                
        if (!getResources().getBoolean(R.bool.has_rgb_notification_led) && !hasDualLed) {
            ((PreferenceCategory) prefSet.findPreference(GENERAL_CATEGORY))
                    .removePreference(mTrackballScreen);
        }
                           
        mPinchReflowPref = (CheckBoxPreference) prefSet.findPreference(PINCH_REFLOW_PREF);
        mPinchReflowPref.setChecked(Settings.System.getInt(getContentResolver(),
                Settings.System.WEB_VIEW_PINCH_REFLOW, 0) == 1);

        mPowerPromptPref = (CheckBoxPreference) prefSet.findPreference(POWER_PROMPT_PREF);
        mPowerPromptPref.setChecked(Settings.System.getInt(getContentResolver(),
                Settings.System.POWER_DIALOG_PROMPT, 1) == 1);   
        
        mRenderEffectPref = (ListPreference) prefSet.findPreference(RENDER_EFFECT_PREF);
        mRenderEffectPref.setOnPreferenceChangeListener(this);
        updateFlingerOptions();

        mOverscrollPref = (ListPreference) prefSet.findPreference(OVERSCROLL_PREF);
        int overscrollEffect = Settings.System.getInt(getContentResolver(),
                Settings.System.OVERSCROLL_EFFECT, 1);
        mOverscrollPref.setValue(String.valueOf(overscrollEffect));
        mOverscrollPref.setOnPreferenceChangeListener(this);

        mOverscrollColor = (ListPreference) prefSet.findPreference(OVERSCROLL_COLOR);
        mOverscrollColor.setOnPreferenceChangeListener(this);
        int overscrollColor = Settings.System.getInt(getContentResolver(),
                Settings.System.OVERSCROLL_COLOR, 0);
        mOverscrollColor.setValue(String.valueOf(overscrollColor == 0 ? 0 : 1));

        mOverscrollWeightPref = (ListPreference) prefSet.findPreference(OVERSCROLL_WEIGHT_PREF);
        int overscrollWeight = Settings.System.getInt(getContentResolver(),
                Settings.System.OVERSCROLL_WEIGHT, 5);
        mOverscrollWeightPref.setValue(String.valueOf(overscrollWeight));
        mOverscrollWeightPref.setOnPreferenceChangeListener(this);
    }

    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        boolean value;
        if (preference == mTrackballScreen) {
            startActivity(mTrackballScreen.getIntent());
            return true;
        } else if (preference == mPinchReflowPref) {
            value = mPinchReflowPref.isChecked();
            Settings.System.putInt(getContentResolver(), Settings.System.WEB_VIEW_PINCH_REFLOW,
                    value ? 1 : 0);
            return true;
	    } else if (preference == mPowerPromptPref) {
            value = mPowerPromptPref.isChecked();
            Settings.System.putInt(getContentResolver(), Settings.System.POWER_DIALOG_PROMPT,
                    value ? 1 : 0);
            return true;
        }
        return false;
    }

    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String val = newValue.toString();
        if (preference == mRenderEffectPref) {
            writeRenderEffect(Integer.valueOf((String) newValue));
            return true;
        } else if (preference == mOverscrollPref) {
            int overscrollEffect = Integer.valueOf((String) newValue);
            Settings.System.putInt(getContentResolver(), Settings.System.OVERSCROLL_EFFECT,
                    overscrollEffect);
            return true;
        } else if (preference == mOverscrollWeightPref) {
            int overscrollWeight = Integer.valueOf((String) newValue);
            Settings.System.putInt(getContentResolver(), Settings.System.OVERSCROLL_WEIGHT,
                    overscrollWeight);
            return true;
        } else if (preference == mOverscrollColor) {
            if (mOverscrollColor.findIndexOfValue(val)==0) {
                Settings.System.putInt(getContentResolver(), Settings.System.OVERSCROLL_COLOR, 0);
            } else {
                int overscrollColor = Settings.System.getInt(getContentResolver(),
                        Settings.System.OVERSCROLL_COLOR, 0);
                ColorPickerDialog cp = new ColorPickerDialog(this, mPackageColorListener,
                        overscrollColor);
                cp.show();
            }
            return true;
        }
        return false;
    }

    ColorPickerDialog.OnColorChangedListener mPackageColorListener = new
    		ColorPickerDialog.OnColorChangedListener() {
        public void colorChanged(int color) {
            Settings.System.putInt(getContentResolver(),
                    Settings.System.OVERSCROLL_COLOR, color);
        }
        @Override
        public void colorUpdate(int color) {
        }
    };

    ColorPickerDialog.OnColorChangedListener mWidgetColorListener = new 
    		ColorPickerDialog.OnColorChangedListener() {
        public void colorChanged(int color) {
            Settings.System.putInt(getContentResolver(),
                    Settings.System.EXPANDED_VIEW_WIDGET_COLOR, color);
        }
        @Override
        public void colorUpdate(int color) {
        }
    };

    private void updateFlingerOptions() {
        try {
            IBinder flinger = ServiceManager.getService("SurfaceFlinger");
            if (flinger != null) {
                Parcel data = Parcel.obtain();
                Parcel reply = Parcel.obtain();
                data.writeInterfaceToken("android.ui.ISurfaceComposer");
                flinger.transact(1010, data, reply, 0);
                int v;
                v = reply.readInt();
                v = reply.readInt();
                v = reply.readInt();
                v = reply.readInt();
                v = reply.readInt();
                mRenderEffectPref.setValue(String.valueOf(v));
                reply.recycle();
                data.recycle();
            }
        } catch (RemoteException ex) {
        }
    }

    private void writeRenderEffect(int id) {
        try {
            IBinder flinger = ServiceManager.getService("SurfaceFlinger");
            if (flinger != null) {
                Parcel data = Parcel.obtain();
                data.writeInterfaceToken("android.ui.ISurfaceComposer");
                data.writeInt(id);
                flinger.transact(1014, data, null, 0);
                data.recycle();
            }
        } catch (RemoteException ex) {
        }
    }
}
