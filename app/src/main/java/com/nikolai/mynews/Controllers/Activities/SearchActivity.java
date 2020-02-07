package com.nikolai.mynews.Controllers.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.nikolai.mynews.Controllers.Fragments.SearchResultsFragment;
import com.nikolai.mynews.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/**
 * elements to enable one to search the NY Times API
 */
public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "SearchActivity";
    private DatePickerDialog picker;
    private EditText mBeginDateEditText;
    private EditText mEndDateEditText;
    private CheckBox chkArts, chkBusiness, chkEntrepreneurs, chkPolitics, chkSports, chkTravel, chkScience, chkTechnology, chkWorld;
    private Button btnSearch;
    private TextInputEditText mSearchQueryTerm;
    private String searchqueryterm;
    private Switch mNotificationsSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Access the support ActionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //calendar spinner
        mBeginDateEditText = findViewById(R.id.begin_date);
        mBeginDateEditText.setInputType(InputType.TYPE_NULL);
        mBeginDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(SearchActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                //using temp String for internationalization
                                String temp = (monthOfYear + 1) + "/" + dayOfMonth + "/" + year;
                                mBeginDateEditText.setText(temp);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
        //calendar spinner
        mEndDateEditText = findViewById(R.id.end_date);
        mEndDateEditText.setInputType(InputType.TYPE_NULL);
        mEndDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(SearchActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                //using temp String for internationalization
                                String temp = (monthOfYear + 1) + "/" + dayOfMonth + "/" + year;
                                mEndDateEditText.setText(temp);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
        mNotificationsSwitch = findViewById(R.id.notifications_switch);
        mNotificationsSwitch.setVisibility(View.GONE);
        addListenerOnButton();
    }

    private void addListenerOnButton() {

        chkArts = findViewById(R.id.arts);
        chkBusiness = findViewById(R.id.business);
        chkEntrepreneurs = findViewById(R.id.entrepreneurs);
        chkPolitics = findViewById(R.id.politics);
        chkSports = findViewById(R.id.sports);
        chkTravel = findViewById(R.id.travel);
        chkScience = findViewById(R.id.science);
        chkTechnology = findViewById(R.id.technology);
        chkWorld = findViewById(R.id.world);
        btnSearch = findViewById(R.id.search_button);

        btnSearch.setOnClickListener(new View.OnClickListener() {

            //Run when button is clicked
            @Override
            public void onClick(View v) {

                StringBuilder result = new StringBuilder();
                result.append("Arts check: ").append(chkArts.isChecked());
                result.append("\nBusiness check: ").append(chkBusiness.isChecked());
                result.append("\nEntrepreneurs check:").append(chkEntrepreneurs.isChecked());
                result.append("\nPolitics check:").append(chkPolitics.isChecked());
                result.append("\nSports check:").append(chkSports.isChecked());
                result.append("\nTravel check:").append(chkTravel.isChecked());
                result.append("\nScience check:").append(chkScience.isChecked());
                result.append("\nTechnology check:").append(chkTechnology.isChecked());
                result.append("\nWorld check:").append(chkWorld.isChecked());

                if (validateSearch()) {
                    startSearchFragment();
                }
            }
        });
    }

    private boolean validateSearch() {
        mSearchQueryTerm = findViewById(R.id.search_query_term);
        searchqueryterm = mSearchQueryTerm.getText().toString().trim();
        if (searchqueryterm.isEmpty()) {
            Toast.makeText(SearchActivity.this, "Please enter a search term",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (chkArts.isChecked() == false && chkBusiness.isChecked() == false && chkEntrepreneurs.isChecked() == false && chkPolitics.isChecked() == false && chkSports.isChecked() == false && chkTravel.isChecked() == false && chkWorld.isChecked() == false && chkScience.isChecked() == false && chkTechnology.isChecked() == false) {
            Toast.makeText(SearchActivity.this, "Please select at least one search category",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!TextUtils.isEmpty(mBeginDateEditText.getText().toString()) && !TextUtils.isEmpty(mEndDateEditText.getText().toString())) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date start_date = null;
            Date end_date = null;
            try {
                start_date = sdf.parse(mBeginDateEditText.getText().toString());
                end_date = sdf.parse(mEndDateEditText.getText().toString());

            } catch (ParseException e) {
                e.printStackTrace();
            }

            long startDate = start_date.getTime();
            long endDate = end_date.getTime();
            if (!validateStartandEndDate(startDate, endDate)) {
                Toast.makeText(SearchActivity.this, "Please enter a start date that is before an end date",
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    private void startSearchFragment() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("chkArts",chkArts.isChecked());
        bundle.putBoolean("chkBusiness",chkBusiness.isChecked());
        bundle.putBoolean("chkEntrepreneurs",chkEntrepreneurs.isChecked());
        bundle.putBoolean("chkPolitics",chkPolitics.isChecked());
        bundle.putBoolean("chkSports",chkSports.isChecked());
        bundle.putBoolean("chkTravel",chkTravel.isChecked());
        bundle.putBoolean("chkScience",chkScience.isChecked());
        bundle.putBoolean("chkTechnology",chkTechnology.isChecked());
        bundle.putBoolean("chkWorld",chkWorld.isChecked());
        Log.d(TAG, "startSearchFragment: "+mBeginDateEditText.getText().toString());
        Log.d(TAG, "startSearchFragment: "+mEndDateEditText.getText().toString());
        if (!TextUtils.isEmpty(mBeginDateEditText.getText().toString())) {
            bundle.putString("mBeginDateEditText", reformatDate(mBeginDateEditText.getText().toString()));
            Log.d(TAG, "startSearchFragment: here5");

        }
        else {
            bundle.putString("mBeginDateEditText", "19000101");

        }
        if (!TextUtils.isEmpty(mEndDateEditText.getText().toString())) {
            bundle.putString("mEndDateEditText", reformatDate(mEndDateEditText.getText().toString()));
            Log.d(TAG, "startSearchFragment: here6");

        }
        else {
            bundle.putString("mEndDateEditText", "20500101");
        }
        bundle.putString("searchqueryterm",searchqueryterm);
                // set Fragment Arguments
        SearchResultsFragment fragobj = new SearchResultsFragment();
        fragobj.setArguments(bundle);
        //called stop activity and passes values back to calling activity
        Intent intent = new Intent();
        intent.putExtras(bundle);
        setResult(RESULT_OK,intent);
        finish();
    }

    //takes in format MM/DD/YYYY
    //returns YYYYMMDD
    private String reformatDate(String Date) {
        Log.d(TAG, "reformatDate: " + Date);
        String[] unformattedDate = Date.split("/");
        if (unformattedDate[0].length() == 1) {
            unformattedDate[0] = "0" + unformattedDate[0];
        }
        if (unformattedDate[1].length() == 1) {
            unformattedDate[1] = "0" + unformattedDate[1];
        }
        //assumes that it is the year 2000 date
        if (unformattedDate[2].length() == 2) {
            unformattedDate[2] = "20" + unformattedDate[2];
        }
        return unformattedDate[2] + unformattedDate[0] + unformattedDate[1];
    }

    private boolean validateStartandEndDate(long startDate, long endDate) {
        Log.d(TAG, "validateStartandEndDate: "+ (startDate <= endDate));
        return startDate <= endDate;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
