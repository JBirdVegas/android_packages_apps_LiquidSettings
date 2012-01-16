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

import com.liquid.settings.R;

import java.util.ArrayList;

public abstract class MasterLists {
    public static final int TYPE_CATEGORY = 0;
    public static final int TYPE_NORMAL = 1;
    public static final int TYPE_SWITCH = 2;

    public static class List {
        public int titleResId;
        public int summaryResId;
        public String intent;
        public int type;
        public int linear_layout;

        //allow assignment of androidId
        public List(int arg1, int arg2, String arg3, int arg4, int arg5) {
            titleResId = arg1;
            summaryResId = arg2;
            intent = arg3;
            type = arg4;
            linear_layout = arg5;
        }

        public List(int arg1, int arg2, String arg3, int arg4) {
            titleResId = arg1;
            summaryResId = arg2;
            intent = arg3;
            type = arg4;
        }
    }
    public abstract ArrayList<List> getList();    
}
