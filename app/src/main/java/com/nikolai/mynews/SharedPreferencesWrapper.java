package com.nikolai.mynews;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SharedPreferencesWrapper implements SharedPreferencesWrapperInterface {
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

    public void storeIntData(String currentWeekday, int currentPosition) {
        SharedPreferences.Editor preferencesEditor = sharedPref.edit();
        preferencesEditor.putInt(currentWeekday, currentPosition);
        preferencesEditor.apply();
    }
    public void storeStringData(String currentWeekdayMoodNote, String moodnote) {
        SharedPreferences.Editor preferencesEditor = sharedPref.edit();
        preferencesEditor.putString(currentWeekdayMoodNote, moodnote);
        preferencesEditor.apply();
    }

    public int retrieveIntData(String currentWeekday, int defaultValue) {
        int mPreferencesInt = sharedPref.getInt(currentWeekday, defaultValue);
        return mPreferencesInt;
    }

    public String retrieveStringData(String currentWeekday, String defaultValue) {
        String mPreferencesString = sharedPref.getString(currentWeekday, defaultValue);
        return mPreferencesString;
    }

    public void removeData(String data) {
        SharedPreferences.Editor preferencesEditor = sharedPref.edit();
        preferencesEditor.remove(data);
        preferencesEditor.apply();
    }

    public boolean contains(String data) {
        return sharedPref.contains(data);
    }

    @Override
    public void saveSwitchState(boolean isChecked) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(IS_CHECKED, isChecked);
        editor.apply();
    }

    @Override
    public void saveLastUpdate(Date lastUpdated) {
        SharedPreferences.Editor editor = sharedPref.edit();
        SimpleDateFormat dateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss yyyy", Locale.US);
        editor.putString(LAST_UPDATED, dateFormat.format(lastUpdated));
        editor.apply();
    }

    @Override
    public boolean getSaveSwitchState() {
        SharedPreferences editor = sharedPref;
        return editor.getBoolean(IS_CHECKED,false);
    }

    @Override
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
