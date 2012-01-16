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

package com.liquid.settings.lists;

import android.util.Log;

import com.liquid.settings.R;
import com.liquid.settings.Global;

import java.lang.Integer;
import java.util.ArrayList;

public class LiquidNomNomList extends MasterLists {
    public static ArrayList<MasterLists.List> mList = null;
    public static final String TAG = "LiquidSettings :LiquidNomNomList";

    public LiquidNomNomList() {
        //we can only dyanically set android:id to an int so we parce our real name and use an variable for reference
        //as of yet this doesn't quite work
        mList = new ArrayList<MasterLists.List>();
        mList.add(new MasterLists.List(R.string.bootanimation_switch_title, R.string.bootanimation_switch_title, "com.liquid.settings.switches.BootAnimSwitch", TYPE_SWITCH));
        mList.add(new MasterLists.List(R.string.bootanimation_title, R.string.bootanimation_summary, "com.liquid.settings.activities.BootAnims", TYPE_NORMAL));
    }

    public ArrayList<MasterLists.List> getList() {
        return mList;
    }
}
