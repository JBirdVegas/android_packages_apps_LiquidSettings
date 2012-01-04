/*
* Copyright (C) 2011 The Liquid Settings Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.liquid.settings.activities;

import com.liquid.settings.R;

import java.util.ArrayList;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.liquid.settings.widgets.TouchInterceptor;

public class NavButtons extends PreferenceActivity implements OnPreferenceChangeListener {

    private static final String DEFAULT_LAYOUT = "back|home|recent|search0";
    private static final String SHOW_SEARCH = "show_search";

    private CheckBoxPreference mShowSearch;
    private PreferenceScreen mButtonOrder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getPreferenceManager() != null) {
            addPreferencesFromResource(R.xml.ui_nav_controls);
            PreferenceScreen prefSet = getPreferenceScreen();
            mButtonOrder = (PreferenceScreen) prefSet.findPreference("button_order");
            mShowSearch = (CheckBoxPreference) prefSet.findPreference(SHOW_SEARCH);
            String storedNav = Settings.System.getString(getApplicationContext().getContentResolver(),
                    Settings.System.NAV_BUTTONS);

            if (storedNav == null) {
                storedNav = DEFAULT_LAYOUT;
            }
            mShowSearch.setChecked(storedNav.contains("search1"));
        }
    }

    public boolean onPreferenceChange(Preference preference, Object newValue) {
        return false;
    }

    public boolean startFragment(
            Fragment caller, String fragmentClass, int requestCode, Bundle extras) {
            startPreferencePanel (fragmentClass, extras, 0, null, null, requestCode);
            return true;
    }

    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        boolean value;
        if(preference == mButtonOrder) {
            startFragment(null, mButtonOrder.getFragment(), -1, null);
        }
        if (preference == mShowSearch) {
            value = mShowSearch.isChecked();
            String stored = Settings.System.getString(getApplicationContext().getContentResolver(), Settings.System.NAV_BUTTONS);
            if (stored == null) {
                stored = DEFAULT_LAYOUT;
            }
            if (value) {
                stored = stored.replace("search0", "search1");
            } else {
                stored = stored.replace("search1", "search0");
            }
            Settings.System.putString(getApplicationContext().getContentResolver(), Settings.System.NAV_BUTTONS, stored);
        }
        return true;
    }

    public static class NavButtonOrder extends ListFragment {

        private ListView mButtonList;
        private ButtonAdapter mButtonAdapter;
        View mContentView = null;
        Context mContext;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            mContentView = inflater.inflate(R.layout.order_power_widget_buttons_activity, null);
            return mContentView;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            mContext = getActivity().getApplicationContext();
            mButtonList = getListView();
            ((TouchInterceptor) mButtonList).setDropListener(mDropListener);
            mButtonAdapter = new ButtonAdapter(mContext);
            setListAdapter(mButtonAdapter);
        }

        @Override
        public void onDestroy() {
            ((TouchInterceptor) mButtonList).setDropListener(null);
            setListAdapter(null);
            super.onDestroy();
        }

        @Override
        public void onResume() {
            super.onResume();
            mButtonAdapter.reloadButtons();
            mButtonList.invalidateViews();
        }

        private TouchInterceptor.DropListener mDropListener = new TouchInterceptor.DropListener() {
                public void drop(int from, int to) {
                    String stored = Settings.System.getString(mContext.getContentResolver(), Settings.System.NAV_BUTTONS);
                    if (stored==null) {
                        stored = DEFAULT_LAYOUT;
                    }
                    ArrayList<String> buttons = new ArrayList<String>();
                    for(String button : stored.split("\\|")) {
                        buttons.add(button);
                    }
                    if(from < buttons.size()) {
                        String button = buttons.remove(from);
                        if(to <= buttons.size()) {
                            buttons.add(to, button);
                            String toStore = buttons.get(0);
                            for(int i = 1; i < buttons.size(); i++) {
                                toStore += "|" + buttons.get(i);
                            }
                            Settings.System.putString(mContext.getContentResolver(), Settings.System.NAV_BUTTONS, toStore);
                            mButtonAdapter.reloadButtons();
                            mButtonList.invalidateViews();
                        }
                    }
                }
            };

            public static class ButtonAdapter extends BaseAdapter {
                private Context mContext;
                private Resources mSystemUIResources = null;
                private LayoutInflater mInflater;
                private ArrayList<String> mButtons = new ArrayList<String>();

                public ButtonAdapter (Context c) {
                    mContext = c;
                    mInflater = LayoutInflater.from(mContext);
                    PackageManager pm = mContext.getPackageManager();
                    if(pm != null) {
                        try {
                            mSystemUIResources = pm.getResourcesForApplication("com.android.systemui");
                        } catch(Exception e) {
                            mSystemUIResources = null;
                        }
                    }
                    reloadButtons();
                }

                public void reloadButtons() {
                    String stored = Settings.System.getString(mContext.getContentResolver(), Settings.System.NAV_BUTTONS);
                    if (stored==null) {
                        stored = DEFAULT_LAYOUT;
                    }
                    mButtons.clear();
                    for(String button : stored.split("\\|")) {
                            mButtons.add(button);
                    }
                }

                public int getCount() {
                    return mButtons.size();
                }

                public Object getItem(int position) {
                    return mButtons.get(position);
                }

                public long getItemId(int position) {
                    return position;
                }

                public View getView(int position, View convertView, ViewGroup parent) {
                    final View v;
                    if(convertView == null) {
                        v = mInflater.inflate(R.layout.order_power_widget_button_list_item, null);
                    } else {
                        v = convertView;
                    }
                    final TextView name = (TextView)v.findViewById(R.id.name);
                    final ImageView icon = (ImageView)v.findViewById(R.id.icon);
                    String curText = mButtons.get(position);
                    if (curText.equals("search0")) {
                        curText = "search(Off)";
                    } else if (curText.equals("search1")){
                        curText = "search(On)";
                    }
                    name.setText(curText);
                    String resName = null;
                    if (mButtons.get(position).equals("home")) {
                        resName = "com.android.systemui:drawable/ic_sysbar_home";
                    }else if (mButtons.get(position).equals("recent")) {
                        resName = "com.android.systemui:drawable/ic_sysbar_recent";
                    }else if (mButtons.get(position).equals("back")) {
                        resName = "com.android.systemui:drawable/ic_sysbar_back";
                    }else {
                        resName = "com.android.systemui:drawable/ic_sysbar_search";
                    }
                    icon.setVisibility(View.GONE);
                    if(mSystemUIResources != null) {
                        int resId = mSystemUIResources.getIdentifier(resName, null, null);
                        if(resId > 0) {
                            try {
                                Drawable d = mSystemUIResources.getDrawable(resId);
                                icon.setVisibility(View.VISIBLE);
                                icon.setImageDrawable(d);
                            } catch(Exception e) {
                            }
                        }
                    }
                    return v;
                }
            }
    }
}
