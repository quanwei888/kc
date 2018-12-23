package com.kanci.ui.book.wordlist;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by qw on 18-12-23.
 */

public class FragmentAdapter extends FragmentPagerAdapter {
    List<Fragment> fragments;
    List<String> tabTitles;

    public FragmentAdapter(FragmentManager fm, List<Fragment> fragments, List<String> tabTitles) {
        super(fm);
        this.fragments = fragments;
        this.tabTitles = tabTitles;
    }

    @Override
    public Fragment getItem(int pos) {
        return fragments.get(pos);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int pos) {
        return tabTitles.get(pos);
    }
}
