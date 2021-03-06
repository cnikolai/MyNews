package com.nikolai.mynews.Adapters;

import android.util.Log;

import com.nikolai.mynews.Controllers.Fragments.MostPopularFragment;
import com.nikolai.mynews.Controllers.Fragments.SearchResultsFragment;
import com.nikolai.mynews.Controllers.Fragments.TopStoriesFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Created by Cynthia Nikolai on 7/11/2019.
 */

/**
 * The page that first displays when we start up the program.  There are three main "tabs"
 */
public class PageAdapter extends FragmentPagerAdapter {

    private static final String TAG = "FragmentPagerAdapter";
    //Default Constructor
    public PageAdapter(FragmentManager mgr) {
        super(mgr);
    }

    //there are three main tabs
    @Override
    public int getCount() {
        return(3);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                Log.d(TAG, "inside getItem: TopStoriesFragment");
                return TopStoriesFragment.newInstance();
            case 1:
                Log.d(TAG, "inside getItem: MostPopularFragment");
                return MostPopularFragment.newInstance();
            case 2:
                Log.d(TAG, "inside getItem: SearchResultsFragment");
                return SearchResultsFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Top Stories";
            case 1:
                return "Most Popular";
            case 2:
                return "Search Results";
            default:
                return null;
        }
    }
}
