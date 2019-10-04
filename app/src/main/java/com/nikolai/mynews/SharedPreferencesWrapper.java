package com.nikolai.mynews;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class SharedPreferencesWrapper {
    private static final String PREFERENCE_FILE_KEY = "MyNews";
    private static final String LAST_UPDATED = "lastUpdated";
    private final SharedPreferences sharedPref;

    public SharedPreferencesWrapper(Context context) {
        sharedPref = context.getSharedPreferences(
                PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
    }

    public void saveLastUpdate(Date lastUpdated) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(LAST_UPDATED, lastUpdated.toString());
        editor.commit();
    }

    public Date getLastSeenUpdate() {
        Date date = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        //LocalDateTime now = LocalDateTime.now();
        String now = sharedPref.getString(LAST_UPDATED, "1970-01-01T00:00");
        //dateFormat.format(now);
        try {
            date = dateFormat.parse(now);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
