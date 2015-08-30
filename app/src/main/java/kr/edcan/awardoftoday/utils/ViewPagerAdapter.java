package kr.edcan.awardoftoday.utils;

/**
 * Created by kotohana5706 on 2015. 8. 30..
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import kr.edcan.awardoftoday.activity.Tab_Achieve;
import kr.edcan.awardoftoday.activity.Tab_Award;
import kr.edcan.awardoftoday.activity.Tab_Home;
import kr.edcan.awardoftoday.activity.Tab_Want;

/**
 * Created by Edwin on 15/02/2015.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    String Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm, String mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Tab_Home();
            case 1:
                return new Tab_Achieve();
            case 2:
                return new Tab_Want();
            case 3:
                return new Tab_Award();
            default:
                return null;
        }
    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public String getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}