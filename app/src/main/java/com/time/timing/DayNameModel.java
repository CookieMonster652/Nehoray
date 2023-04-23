package com.time.timing;

import java.util.List;

import lombok.Data;

@Data
public class DayNameModel {
    private String DayName;
    private List<ShiftTimeModel> models = null;
}
