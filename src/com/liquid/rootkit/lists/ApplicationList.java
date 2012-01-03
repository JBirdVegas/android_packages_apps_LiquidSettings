
package com.liquid.rootkit.lists;

import java.util.ArrayList;

public class ApplicationList extends MasterLists {
    public static ArrayList<MasterLists.List> mList = null;

    public ApplicationList() {
        mList = new ArrayList<MasterLists.List>();
        //mList.put(new MasterLists.List(ResID, ResID, Intent, Type));   
    }

    public ArrayList<MasterLists.List> getList() {
        return mList;
    }
}

