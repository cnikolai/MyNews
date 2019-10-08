package com.nikolai.mynews.Controllers.Activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.nikolai.mynews.R;
import com.nikolai.mynews.SharedPreferencesWrapper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static com.nikolai.mynews.Constants.NOTIFICATION_ID;

/**
 * the layout for the notifcations and the search activity: the base layout is shared between the two views
 */
public class NotificationsActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "1";
    private TextView mBeginDateViewText;
    private TextView mEndDateViewText;
    private EditText mBeginDateEditText;
    private EditText mEndDateEditText;
    private CheckBox chkArts, chkBusiness, chkEntrepreneurs, chkPolitics, chkSports, chkTravel, chkScience, chkTechnology, chkWorld;
    private Button btnSearch;
    private TextInputEditText mSearchQueryTerm;
    private String searchqueryterm;
    private Switch mNotificationsSwitch;
    private ConstraintLayout mConstraintLayout;
    SharedPreferencesWrapper sharedPreferencesWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sharedPreferencesWrapper = new SharedPreferencesWrapper(this);


        // hide unwanted components from view
        mBeginDateViewText = findViewById(R.id.begin_date_txt);
        mEndDateViewText = findViewById(R.id.end_date_txt);
        mBeginDateEditText = findViewById(R.id.begin_date);
        mEndDateEditText = findViewById(R.id.end_date);
        btnSearch = findViewById(R.id.search_button);
        mBeginDateViewText.setVisibility(View.GONE);
        mEndDateViewText.setVisibility(View.GONE);
        mBeginDateEditText.setVisibility(View.GONE);
        mEndDateEditText.setVisibility(View.GONE);
        btnSearch.setVisibility(View.GONE);
        mNotificationsSwitch = findViewById(R.id.notifications_switch);
        mNotificationsSwitch.setChecked(sharedPreferencesWrapper.getSaveSwitchState());
        Log.v("Switch State := ", ""+sharedPreferencesWrapper.getSaveSwitchState());

        chkArts = findViewById(R.id.arts);
        chkBusiness = findViewById(R.id.business);
        chkEntrepreneurs = findViewById(R.id.entrepreneurs);
        chkPolitics = findViewById(R.id.politics);
        chkSports = findViewById(R.id.sports);
        chkTravel = findViewById(R.id.travel);
        chkScience = findViewById(R.id.science);
        chkTechnology = findViewById(R.id.technology);
        chkWorld = findViewById(R.id.world);

        mConstraintLayout = findViewById(R.id.constraint_layout);

        mNotificationsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.v("Switch State = ", ""+isChecked);
                if (isChecked) {
                    sharedPreferencesWrapper.saveSwitchState(isChecked); //using shared prefs
                    if (validateSearch()) {
                        createNotificationChannel();
                        addNotification();
                    }
                    else {
                        //make switch false
                        mNotificationsSwitch.setChecked(false);
                        sharedPreferencesWrapper.saveSwitchState(false); //using shared prefs
                    }
                }
                else {
                    mNotificationsSwitch.setChecked(false);
                    sharedPreferencesWrapper.saveSwitchState(false); //using shared prefs
                }
            }

        });
    }

    private boolean validateSearch() {
        mSearchQueryTerm = findViewById(R.id.search_query_term);
        searchqueryterm = mSearchQueryTerm.getText().toString().trim();
        if (searchqueryterm.isEmpty()) {
            Toast.makeText(NotificationsActivity.this, "Please enter a search term",
                Toast.LENGTH_SHORT).show();
            return false;
        }
        if (chkArts.isChecked() == false && chkBusiness.isChecked() == false && chkEntrepreneurs.isChecked() == false && chkPolitics.isChecked() == false && chkSports.isChecked() == false && chkTravel.isChecked() == false && chkScience.isChecked() == false && chkTechnology.isChecked() == false && chkWorld.isChecked() == false) {
            Toast.makeText(NotificationsActivity.this, "Please select at least one search category",
                Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void addNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_menu_white_24dp)
                        .setContentTitle("My News Notification")
                        .setContentText("A New Article Has Been Released")
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Intent notificationIntent = new Intent(this, SearchActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(NOTIFICATION_ID, builder.build());

    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "My News Notifications";
            String description = "My News Notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
