package com.nikolai.mynews.Controllers.Activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

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
    private final int notificationId = 1;
    private ConstraintLayout mConstraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // hide unwanted components from view
        mBeginDateViewText =(TextView) findViewById(R.id.begin_date_txt);
        mEndDateViewText =(TextView) findViewById(R.id.end_date_txt);
        mBeginDateEditText =(EditText) findViewById(R.id.begin_date);
        mEndDateEditText =(EditText) findViewById(R.id.end_date);
        btnSearch = (Button) findViewById(R.id.search_button);
        mBeginDateViewText.setVisibility(View.GONE);
        mEndDateViewText.setVisibility(View.GONE);
        mBeginDateEditText.setVisibility(View.GONE);
        mEndDateEditText.setVisibility(View.GONE);
        btnSearch.setVisibility(View.GONE);
        mNotificationsSwitch = (Switch) findViewById(R.id.notifications_switch);

        chkArts = (CheckBox) findViewById(R.id.arts);
        chkBusiness = (CheckBox) findViewById(R.id.business);
        chkEntrepreneurs = (CheckBox) findViewById(R.id.entrepreneurs);
        chkPolitics = (CheckBox) findViewById(R.id.politics);
        chkSports = (CheckBox) findViewById(R.id.sports);
        chkTravel = (CheckBox) findViewById(R.id.travel);
        chkScience = (CheckBox) findViewById(R.id.science);
        chkTechnology = (CheckBox) findViewById(R.id.technology);
        chkWorld = (CheckBox) findViewById(R.id.world);

        //createNotificationChannel();

        //final RelativeLayout relativeLayout = findViewById(R.id.relativeLayout);
        mConstraintLayout = (ConstraintLayout) findViewById(R.id.constraint_layout);

        mNotificationsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.v("Switch State = ", ""+isChecked);
                if (isChecked) {
                    if (validateSearch()) {
                        createNotificationChannel();
                        addNotification();
                        Snackbar snackbar = Snackbar
                                .make(mConstraintLayout, "Notifications Set", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                    else {
                        //make switch false
                        mNotificationsSwitch.setChecked(false);
                    }
                }

            }

        });
    }

    private boolean validateSearch() {
        mSearchQueryTerm = (TextInputEditText) findViewById(R.id.search_query_term);
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
//
//         //Add as notification
//        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        manager.notify(0, builder.build());

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(notificationId, builder.build());

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
