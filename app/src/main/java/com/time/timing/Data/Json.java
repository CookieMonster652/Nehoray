
package com.time.timing.Data;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Json {

    @SerializedName("data")
    private List<Datum> mData;
    @SerializedName("included")
    private List<Included> mIncluded;

    public List<Datum> getData() {
        return mData;
    }

    public void setData(List<Datum> data) {
        mData = data;
    }

    public List<Included> getIncluded() {
        return mIncluded;
    }

    public void setIncluded(List<Included> included) {
        mIncluded = included;
    }

}
