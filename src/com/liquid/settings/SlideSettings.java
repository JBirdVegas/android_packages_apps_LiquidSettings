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

package com.liquid.settings;

import android.app.Activity;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Switch;
import android.widget.TextView;

import com.liquid.settings.lists.LiquidNomNomList;
import com.liquid.settings.lists.ExtrasList;
import com.liquid.settings.lists.LockscreenList;
import com.liquid.settings.lists.MasterLists;
import com.liquid.settings.Global;
import com.liquid.settings.R;
import com.liquid.settings.switches.PowerWidgetEnabler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;

public class SlideSettings extends Activity {

    public static final String TAG = "LiquidSettings :SlideSettings";

    SettingsAdapter mAdapter;
    ViewPager mPager;
    ActionBar mActionBar;
    static final int VIEWS = 2;
    public static Context mContext;

    private static final HashMap<Integer, MasterLists> listHead = new HashMap<Integer, MasterLists>();
    static {
        listHead.put(0, new LiquidNomNomList());
        listHead.put(1, new ExtrasList());
        listHead.put(6, new LockscreenList());
    }

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.settings_view);
        mContext = getApplicationContext();
        mAdapter = new SettingsAdapter(getFragmentManager());
        mActionBar = getActionBar();
        mPager = (ViewPager) findViewById(R.id.settings_pager);
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        mActionBar.setDisplayShowTitleEnabled(true);

        for (String entry : mAdapter.getTabs()) {
            ActionBar.Tab tab = mActionBar.newTab();
            tab.setTabListener(new TabListener() {
                @Override
                public void onTabReselected(Tab tab, FragmentTransaction ft) {
                    Log.d(TAG, "onTabReselected called...");
                }
                @Override
                public void onTabSelected(Tab tab, FragmentTransaction ft) {
                    Log.d(TAG, "onTabSelected called...");
                    mPager.setCurrentItem(tab.getPosition());
                }
                @Override
                public void onTabUnselected(Tab tab, FragmentTransaction ft) {
                    Log.d(TAG, "onTabUnselected called...");
                }
            });
            tab.setText(entry);
            mActionBar.addTab(tab);
        }

        mPager.setAdapter(mAdapter);
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int idx) {
                mActionBar.selectTab(mActionBar.getTabAt(idx));
            }
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }
            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }
    
    public static class SettingsAdapter extends FragmentPagerAdapter {
        private final String[] tabs = {"Nom-Nom", "Extras",  "Lockscreen"};

        public SettingsAdapter(FragmentManager fm) {
            super(fm);
        }

        public String[] getTabs() {
            return tabs;
        }

        @Override
        public int getCount() {
            return tabs.length;
        }

        @Override
        public Fragment getItem(int position) {
            return ArrayListFragment.newInstance(position);
        }
    }

    public static class ArrayListFragment extends ListFragment {
        int mNum;

        static ArrayListFragment newInstance(int num) {
            ArrayListFragment f = new ArrayListFragment();
            Bundle args = new Bundle();
            args.putInt("num", num);
            f.setArguments(args);
            return f;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mNum = getArguments() != null ? getArguments().getInt("num") : 1;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_pager_list, container, false);
            View tv = v.findViewById(R.id.text);
            ((TextView)tv).setText("-" + mNum + "-");
            return v;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            List<Headers> aHeaders = new ArrayList<Headers>();
            ArrayList<MasterLists.List> mHeaders = listHead.get(mNum).getList();

            if(!mHeaders.isEmpty()) {
                for(MasterLists.List mObj : mHeaders) {
                    Headers header = new Headers(mObj.titleResId,
                                                 mObj.summaryResId,
                                                 mObj.intent,
                                                 mObj.type,
                                                 mObj.linear_layout);
                    aHeaders.add(header);
                }
                if(aHeaders != null) {
                    setListAdapter(new HeaderAdapter(getActivity().getApplicationContext(), aHeaders));
                }
            }
        }
        
        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            ListAdapter adapter = getListAdapter();
            Headers header = (Headers)adapter.getItem(position);
            if (!TextUtils.isEmpty(header.mFragment)) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setClassName(getActivity().getApplicationContext(), header.mFragment);
                startActivity(intent);
            }
        }

        private class Headers {
            public int mTitle;
            public int mSummary;
            public String mFragment;
            public int mType;
            public int mLinearLayout;
            public Headers(int title, int summary, String fragment, int type, int linear_layout) {
                mTitle = title;
                mSummary = summary;
                mFragment = fragment;
                mType = type;
                mLinearLayout = linear_layout;
            }

            public Headers(int title, int summary, String fragment, int type) {
                mTitle = title;
                mSummary = summary;
                mFragment = fragment;
                mType = type;
            }
        }

        private static class HeaderAdapter extends ArrayAdapter<Headers> {
            static final int HEADER_TYPE_CATEGORY = 0;
            static final int HEADER_TYPE_NORMAL = 1;
            static final int HEADER_TYPE_SWITCH = 2;
            private static final int HEADER_TYPE_COUNT = HEADER_TYPE_SWITCH + 1;

            private final PowerWidgetEnabler mWidgetEnabler;

            private static class HeaderViewHolder {
                ImageView icon;
                TextView title;
                TextView summary;
                Switch switch_;
                LinearLayout linear_layout;
            }

            private LayoutInflater mInflater;

            static int getHeaderType(Headers header) {
                return header.mType;
            }

            @Override
            public int getItemViewType(int position) {
                Headers header = getItem(position);
                return getHeaderType(header);
            }

            @Override
            public boolean areAllItemsEnabled() {
                return false; // because of categories
            }

            @Override
            public boolean isEnabled(int position) {
                return getItemViewType(position) != HEADER_TYPE_CATEGORY;
            }

            @Override
            public int getViewTypeCount() {
                return HEADER_TYPE_COUNT;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }

            public HeaderAdapter(Context context, List<Headers> objects) {
                super(context, 0, objects);
                mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            
                mWidgetEnabler = new PowerWidgetEnabler(context, new Switch(context));
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                HeaderViewHolder holder;
                Headers header = getItem(position);
                int headerType = getHeaderType(header);
                View view = null;

                if (convertView == null) {
                    holder = new HeaderViewHolder();
                    
                    switch (headerType) {
                        case HEADER_TYPE_CATEGORY:
                            view = new TextView(getContext(), null, android.R.attr.listSeparatorTextViewStyle);
                            holder.title = (TextView) view;
                            break;

                        case HEADER_TYPE_SWITCH:
                            view = mInflater.inflate(R.layout.preference_header_switch_item, parent, false);
                            holder.icon = (ImageView) view.findViewById(R.id.icon);
                            holder.title = (TextView) view.findViewById(R.id.title);
                            holder.summary = (TextView) view.findViewById(R.id.summary);
                            holder.switch_ = (Switch) view.findViewById(R.id.switchWidget);
                            holder.linear_layout = (LinearLayout) view.findViewById(R.id.switch_linear_layout);

                            // use global booleans to tell if we should disable anything
                            LinearLayout linear_layout = (LinearLayout) view.findViewById(R.id.pref_linear_layout);
                            if (!Global.bootanimations_switch_on) {
                                Log.d(TAG, "Found Global.bootanimations_switch_on to be ON");
                                //linear_layout.setClickable(true);
                            } else {
                                Log.d(TAG, "Found Global.bootanimations_switch_on to be OFF");
                                //linear_layout.setClickable(false);
                            }
                            break;

                        case HEADER_TYPE_NORMAL:
                            view = mInflater.inflate(R.layout.preference_header_item, parent, false);
                            holder.icon = (ImageView) view.findViewById(R.id.icon);
                            holder.title = (TextView) view.findViewById(R.id.pref_title);
                            holder.summary = (TextView) view.findViewById(R.id.pref_summary);
                            holder.linear_layout = (LinearLayout) view.findViewById(R.id.pref_linear_layout);
                            break;
                    }
                    view.setTag(holder);
                } else {
                    view = convertView;
                    holder = (HeaderViewHolder) view.getTag();
                }

                switch (headerType) {
                    case HEADER_TYPE_CATEGORY:
                        holder.title.setText(mContext.getResources().getString(header.mTitle));
                        break;
                    case HEADER_TYPE_SWITCH:
                        if (header.mTitle == R.string.title_expanded_widget) {
                             mWidgetEnabler.setSwitch(holder.switch_);
                        }

                        //$FALL-THROUGH$
                    case HEADER_TYPE_NORMAL:
                        //holder.icon.setImageResource(header.iconRes);
                        holder.title.setText(mContext.getResources().getString(header.mTitle));
                        if (header.mSummary != 0) {
                            holder.summary.setVisibility(View.VISIBLE);
                            holder.summary.setText(mContext.getResources().getString(header.mSummary));
                        } else {
                            holder.summary.setVisibility(View.GONE);
                        }
                        break;
                }

                return view;
            }
        
            public void resume() {
                mWidgetEnabler.resume();
            }
        
            public void pause() {
                mWidgetEnabler.pause();
            } 
        }
    }
}
