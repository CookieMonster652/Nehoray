package com.time.timing.Utils;

import java.util.Locale;

public class CurrentLanguage {

    public static String GetCurrentLaunguge(){
        var currentLang = Locale.getDefault().getLanguage();
        return currentLang;
    }
}
