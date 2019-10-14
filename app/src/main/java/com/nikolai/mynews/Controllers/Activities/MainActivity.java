package com.nikolai.mynews.Controllers.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.nikolai.mynews.Adapters.PageAdapter;
import com.nikolai.mynews.Controllers.Fragments.SearchResultsFragment;
import com.nikolai.mynews.R;
import com.nikolai.mynews.Workers.ArticleWorker;

import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

/**
 * this class contains the main menu items for the toolbar
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ViewPager pager;
    private PageAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "inside onCreate: ");
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Access the support ActionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Configure ViewPager
        this.configureViewPagerAndTabs();
        planWorker();
    }

    private void planWorker() {
        WorkManager mWorkManager;
        mWorkManager = WorkManager.getInstance(this.getApplicationContext());
        mWorkManager.enqueue(new PeriodicWorkRequest.Builder(ArticleWorker.class, 1, TimeUnit.DAYS).build());
    }

    private void configureViewPagerAndTabs(){
        //Get ViewPager from layout
        pager = findViewById(R.id.activity_main_viewpager);
        //Set Adapter PageAdapter and glue it together
        pagerAdapter = new PageAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);

        //Get TabLayout from layout
        TabLayout tabs= findViewById(R.id.activity_main_tabs);
        //Glue TabLayout and ViewPager together
        tabs.setupWithViewPager(pager);
        //Design purpose. Tabs have the same width
        tabs.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.search:
                // open search activity
                Intent search_intent = new Intent(MainActivity.this.getBaseContext(), SearchActivity.class);
                //search_intent.putExtra("title", "Search");
                //1001 = unique identifier
                startActivityForResult(search_intent, 1001);
                break;
            case R.id.notifications:
                // open notifications activity
                Intent notifications_intent = new Intent(MainActivity.this.getBaseContext(), NotificationsActivity.class);
                //notifications_intent.putExtra("title", "Notifications");
                //1002 = unique identifier
                startActivityForResult(notifications_intent, 1002);
                break;
            case R.id.about:
                //open about activity
                Intent about_intent = new Intent(MainActivity.this.getBaseContext(), AboutActivity.class);
                startActivity(about_intent);
                break;
            case R.id.help:
                //open help activity
                Intent help_intent = new Intent(MainActivity.this.getBaseContext(), HelpActivity.class);
                startActivity(help_intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((resultCode == RESULT_OK) && requestCode == 1001) {
            Log.d(TAG, "onActivityResult: inside Main Activity onActivityResult search intent");
            pager.setCurrentItem(2);
            SearchResultsFragment searchResultsFragment = (SearchResultsFragment) pagerAdapter.instantiateItem(pager,2);
            searchResultsFragment.setSearch(data.getExtras());
            if (data.getExtras() != null) {
                Log.d(TAG, "inside extras not null");
                for (String key : data.getExtras().keySet()) {
                    Object value = data.getExtras().get(key);
                    Log.d(TAG, String.format("%s %s (%s)", key,
                            value.toString(), value.getClass().getName()));
                }
            }
        }
        else {
            if ((resultCode == RESULT_OK) && requestCode == 1002) {
                Log.d(TAG, "onActivityResult: inside Main Activity onActivityResult notification intent");
                pager.setCurrentItem(2);
                SearchResultsFragment searchResultsFragment = (SearchResultsFragment) pagerAdapter.instantiateItem(pager,2);
                searchResultsFragment.setSearch(data.getExtras());
            }
        }

    }
}
