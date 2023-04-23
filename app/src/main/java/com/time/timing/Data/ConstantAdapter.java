package com.time.timing.Data;

import com.time.timing.UI.ManagerRequest.ShuftNameAdapter;

public class ConstantAdapter {

    public static ShuftNameAdapter shuftNameAdapter;
    public static ShuftNameAdapter getShuftNameAdapter(){
        if(shuftNameAdapter == null){
            return new ShuftNameAdapter();

        }
        return shuftNameAdapter;

    }
}
