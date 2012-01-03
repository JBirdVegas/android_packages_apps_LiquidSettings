
package com.liquid.rootkit.lists;

import com.liquid.rootkit.R;
import java.util.ArrayList;

public class InterfaceList extends MasterLists {
    public static ArrayList<MasterLists.List> mList = null;

    public InterfaceList() {
        //mList.put(new MasterLists.List(ResID, ResID, Intent, Type));
        mList = new ArrayList<MasterLists.List>();
        mList.add(new MasterLists.List(R.string.title_expanded_widget, 0, "com.liquid.rootkit.activities.PowerWidget", TYPE_SWITCH));
    }

    public ArrayList<MasterLists.List> getList() {
        return mList;
    }

}
