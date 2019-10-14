package com.nikolai.mynews;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SharedPreferencesWrapper {
    public static final String TAG = SharedPreferencesWrapper.class.getSimpleName();
    private static final String PREFERENCE_FILE_KEY = "MyNews";
    static final String LAST_UPDATED = "lastUpdated";
    static final String IS_CHECKED = "is_checked";
    private final SharedPreferences sharedPref;

    public SharedPreferencesWrapper(SharedPreferences sharedPreferences) {
        sharedPref = sharedPreferences;
    }

    public SharedPreferencesWrapper(Context context) {
        sharedPref = context.getSharedPreferences(
                PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
    }

    public void saveSwitchState(boolean isChecked) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(IS_CHECKED, isChecked);
        editor.apply();
    }

    public void saveLastUpdate(Date lastUpdated) {
        SharedPreferences.Editor editor = sharedPref.edit();
        SimpleDateFormat dateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss yyyy", Locale.US);
        editor.putString(LAST_UPDATED, dateFormat.format(lastUpdated));
        editor.apply();
    }

    public boolean getSaveSwitchState() {
        SharedPreferences editor = sharedPref;
        return editor.getBoolean(IS_CHECKED,false);
    }

    public Date getLastSeenUpdate() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss yyyy", Locale.US);
        String now = sharedPref.getString(LAST_UPDATED, "1970-01-01T00:00:00Z");
        String new_now = now.replaceAll(" EDT", "");

        try {
            Log.d(TAG, "lastseenupdate: now:" + now);
            date = dateFormat.parse(new_now);
            Log.d(TAG, "lastseenupdate: after parsing:" + date.toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
