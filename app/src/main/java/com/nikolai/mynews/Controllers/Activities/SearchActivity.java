package com.nikolai.mynews.Controllers.Activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.nikolai.mynews.Controllers.Fragments.SearchResultsFragment;
import com.nikolai.mynews.R;

import java.util.Calendar;

public class SearchActivity extends AppCompatActivity {

    private DatePickerDialog picker;
    private EditText mBeginDateEditText;
    private EditText mEndDateEditText;
    private CheckBox chkArts, chkBusiness, chkEntrepreneurs, chkPolitics, chkSports, chkTravel;
    private Button btnSearch;
    private TextInputEditText mSearchQueryTerm;
    private String searchqueryterm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_articles);

        //TODO: add a toolbar to the view
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        // Access the support ActionBar
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(SearchActivity.this, "End of Activity!", Toast.LENGTH_LONG).show();
//                finish();
//            }
//          });

        //calendar spinner
        mBeginDateEditText =(EditText) findViewById(R.id.begin_date);
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
                                mBeginDateEditText.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
        //calendar spinner
        mEndDateEditText =(EditText) findViewById(R.id.end_date);
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
                                mEndDateEditText.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
        addListenerOnButton();
    }

    public void addListenerOnButton() {

        chkArts = (CheckBox) findViewById(R.id.arts);
        chkBusiness = (CheckBox) findViewById(R.id.business);
        chkEntrepreneurs = (CheckBox) findViewById(R.id.entrepreneurs);
        chkPolitics = (CheckBox) findViewById(R.id.politics);
        chkSports = (CheckBox) findViewById(R.id.sports);
        chkTravel = (CheckBox) findViewById(R.id.travel);
        btnSearch = (Button) findViewById(R.id.search_button);

        btnSearch.setOnClickListener(new View.OnClickListener() {

            //Run when button is clicked
            @Override
            public void onClick(View v) {

                //TODO: come back to this
                StringBuffer result = new StringBuffer();
                result.append("Arts check: ").append(chkArts.isChecked());
                result.append("\nBusiness check: ").append(chkBusiness.isChecked());
                result.append("\nEntrepreneurs check:").append(chkEntrepreneurs.isChecked());
                result.append("\nPolitics check:").append(chkPolitics.isChecked());
                result.append("\nSports check:").append(chkSports.isChecked());
                result.append("\nTravel check:").append(chkTravel.isChecked());

                //Toast.makeText(SearchActivity.this, result.toString(),
                       // Toast.LENGTH_LONG).show();
                if (validateSearch()) {
                    Toast.makeText(SearchActivity.this, "here",
                            Toast.LENGTH_LONG).show();
                    startSearchFragment();
                }
            }
        });
    }

    public boolean validateSearch() {
        //TODO: check that start and end date, if filled out, are end date is greater than start date
        mSearchQueryTerm = (TextInputEditText) findViewById(R.id.search_query_term);
        searchqueryterm = mSearchQueryTerm.getText().toString().trim();
        if (searchqueryterm.isEmpty()) {
            Toast.makeText(SearchActivity.this, "Please enter a search term",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (chkArts.isChecked() == false && chkBusiness.isChecked() == false && chkEntrepreneurs.isChecked() == false && chkPolitics.isChecked() == false && chkSports.isChecked() == false && chkTravel.isChecked() == false) {
            Toast.makeText(SearchActivity.this, "Please select at least one search category",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void startSearchFragment() {
        //SearchResultsFragment.newInstance();
        Bundle bundle = new Bundle();
        bundle.putBoolean("chkArts",chkArts.isChecked());
        bundle.putBoolean("chkBusiness",chkBusiness.isChecked());
        bundle.putBoolean("chkEntrepreneurs",chkEntrepreneurs.isChecked());
        bundle.putBoolean("chkPolitics",chkPolitics.isChecked());
        bundle.putBoolean("chkSports",chkSports.isChecked());
        bundle.putBoolean("chkTravel",chkTravel.isChecked());
        bundle.putString("mEndDateEditText",mEndDateEditText.toString());
        bundle.putString("mBeginDateEditText",mBeginDateEditText.toString());
        bundle.putString("searchqueryterm",searchqueryterm);
                // set Fragment Arguments
        SearchResultsFragment fragobj = new SearchResultsFragment();
        fragobj.setArguments(bundle);
    }
}
