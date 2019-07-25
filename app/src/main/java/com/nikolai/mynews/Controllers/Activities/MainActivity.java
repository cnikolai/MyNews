package com.nikolai.mynews.Controllers.Activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.nikolai.mynews.Adapters.PageAdapter;
import com.nikolai.mynews.Controllers.Fragments.SearchResultsFragment;
import com.nikolai.mynews.R;

public class MainActivity extends AppCompatActivity {

    private ViewPager pager;
    private PageAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Access the support ActionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "End of Activity!", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        //Configure ViewPager
        this.configureViewPagerAndTabs();
    }

    private void configureViewPagerAndTabs(){
        //Get ViewPager from layout
        pager = (ViewPager) findViewById(R.id.activity_main_viewpager);
        //Set Adapter PageAdapter and glue it together
        pagerAdapter = new PageAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        //pager.setAdapter(new PageAdapter(getSupportFragmentManager()));

        //Get TabLayout from layout
        TabLayout tabs= (TabLayout)findViewById(R.id.activity_main_tabs);
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
                Intent intent = new Intent(MainActivity.this.getBaseContext(), SearchActivity.class);
                intent.putExtra("title", "Search");
                //1001 = unique identifier
                startActivityForResult(intent, 1001);
                break;
            case R.id.notifications:
                // open notifications activity
                break;
            case R.id.about:
                //open about dialog
                break;
            case R.id.help:
                //open help dialog
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((resultCode == RESULT_OK) && requestCode == 1001) {
            Toast.makeText(this, "Inside Main Activity onActivityResult", Toast.LENGTH_SHORT).show();
            pager.setCurrentItem(2);
            SearchResultsFragment searchResultsFragment = (SearchResultsFragment) pagerAdapter.instantiateItem(pager,2);
            searchResultsFragment.setSearch(data.getExtras());
        }

    }
}
