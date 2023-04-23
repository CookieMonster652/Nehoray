
package com.time.timing.Data;

import com.google.gson.annotations.SerializedName;


public class Author {

    @SerializedName("data")
    private Data mData;

    public Data getData() {
        return mData;
    }

    public void setData(Data data) {
        mData = data;
    }

}
