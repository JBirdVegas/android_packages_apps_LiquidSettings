
package com.liquid.rootkit.lists;

import java.util.ArrayList;

public class TabletList extends MasterLists {
    public static ArrayList<MasterLists.List> mList = null;

    public TabletList() {
        mList = new ArrayList<MasterLists.List>();
        //mList.put(new MasterLists.List(ResID, ResID, Intent, Type));   
    }

    public ArrayList<MasterLists.List> getList() {
        return mList;
    }
}
