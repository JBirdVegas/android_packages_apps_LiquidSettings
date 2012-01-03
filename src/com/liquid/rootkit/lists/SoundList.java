
package com.liquid.rootkit.lists;

import java.util.ArrayList;

public class SoundList extends MasterLists {
    public static ArrayList<MasterLists.List> mList = null;

    public SoundList() {
        mList = new ArrayList<MasterLists.List>();
        //mList.put(new MasterLists.List(ResID, ResID, Intent, Type));   
    }

    public ArrayList<MasterLists.List> getList() {
        return mList;
    }

}
