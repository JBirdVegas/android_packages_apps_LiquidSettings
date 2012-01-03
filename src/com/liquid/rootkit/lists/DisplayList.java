
package com.liquid.rootkit.lists;

import com.liquid.rootkit.R;

import java.util.ArrayList;

public class DisplayList extends MasterLists {
    public static ArrayList<MasterLists.List> mList = null;

    public DisplayList() {
        //mList.put(new MasterLists.List(ResID, ResID, Intent, Type));
        mList = new ArrayList<MasterLists.List>();
        mList.add(new MasterLists.List(R.string.backlight_title, R.string.backlight_summary, "com.liquid.rootkit.activities.Backlight", TYPE_NORMAL));
    }

    public ArrayList<MasterLists.List> getList() {
        return mList;
    }

}

