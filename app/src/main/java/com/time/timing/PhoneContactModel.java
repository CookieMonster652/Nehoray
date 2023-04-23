package com.time.timing;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class PhoneContactModel {
    private @NonNull String Name;
    private @NonNull String PhoneNumber;
}
