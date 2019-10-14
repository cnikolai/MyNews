package com.nikolai.mynews;

import java.util.Calendar;
import java.util.Date;

/**
 * Model class containing personal information that will be saved to SharedPreferences.
 */

public class SharedPreferenceEntry {

        private static String mLastUpdated;
        private static Boolean mIsChecked;

    public SharedPreferenceEntry(String lastUpdated, Boolean isChecked) {
            mLastUpdated = lastUpdated;
            mIsChecked = isChecked;
        }

    public static String getmLastUpdated() {
        return mLastUpdated;
    }

    public static Boolean getmIsChecked() {
        return mIsChecked;
    }

}
