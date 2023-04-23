package com.time.timing;

import android.os.Parcel;
import android.os.Parcelable;


public class ShiftModel implements Parcelable {
    private int Shift;
    private long Timestamp;
    private String ShiftStartTime;
    private String ShiftEndTime;
    private String NumberOFWorkerOnTheShift;
    private String AdditionalHoursAdd;
    private String MinimumShiftParWorker;

    public ShiftModel() {
    }

    public ShiftModel(int shift, long timestamp, String shiftStartTime, String shiftEndTime, String numberOFWorkerOnTheShift, String additionalHoursAdd, String minimumShiftParWorker) {
        Shift = shift;
        Timestamp = timestamp;
        ShiftStartTime = shiftStartTime;
        ShiftEndTime = shiftEndTime;
        NumberOFWorkerOnTheShift = numberOFWorkerOnTheShift;
        AdditionalHoursAdd = additionalHoursAdd;
        MinimumShiftParWorker = minimumShiftParWorker;
    }

    protected ShiftModel(Parcel in) {
        Shift = in.readInt();
        Timestamp = in.readLong();
        ShiftStartTime = in.readString();
        ShiftEndTime = in.readString();
        NumberOFWorkerOnTheShift = in.readString();
        AdditionalHoursAdd = in.readString();
        MinimumShiftParWorker = in.readString();
    }

    public static final Creator<ShiftModel> CREATOR = new Creator<ShiftModel>() {
        @Override
        public ShiftModel createFromParcel(Parcel in) {
            return new ShiftModel(in);
        }

        @Override
        public ShiftModel[] newArray(int size) {
            return new ShiftModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(Shift);
        parcel.writeLong(Timestamp);
        parcel.writeString(ShiftStartTime);
        parcel.writeString(ShiftEndTime);
        parcel.writeString(NumberOFWorkerOnTheShift);
        parcel.writeString(AdditionalHoursAdd);
        parcel.writeString(MinimumShiftParWorker);
    }

    public int getShift() {
        return Shift;
    }

    public void setShift(int shift) {
        Shift = shift;
    }

    public long getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(long timestamp) {
        Timestamp = timestamp;
    }

    public String getShiftStartTime() {
        return ShiftStartTime;
    }

    public void setShiftStartTime(String shiftStartTime) {
        ShiftStartTime = shiftStartTime;
    }

    public String getShiftEndTime() {
        return ShiftEndTime;
    }

    public void setShiftEndTime(String shiftEndTime) {
        ShiftEndTime = shiftEndTime;
    }

    public String getNumberOFWorkerOnTheShift() {
        return NumberOFWorkerOnTheShift;
    }

    public void setNumberOFWorkerOnTheShift(String numberOFWorkerOnTheShift) {
        NumberOFWorkerOnTheShift = numberOFWorkerOnTheShift;
    }

    public String getAdditionalHoursAdd() {
        return AdditionalHoursAdd;
    }

    public void setAdditionalHoursAdd(String additionalHoursAdd) {
        AdditionalHoursAdd = additionalHoursAdd;
    }

    public String getMinimumShiftParWorker() {
        return MinimumShiftParWorker;
    }

    public void setMinimumShiftParWorker(String minimumShiftParWorker) {
        MinimumShiftParWorker = minimumShiftParWorker;
    }
}
