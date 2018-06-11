package com.tvnsoftware.drcare.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tvnsoftware.drcare.fragment.GuiFragment;

/**
 * Created by Thieusike on 7/31/2017.
 */

public class GuiAdapter extends FragmentPagerAdapter {
    public GuiAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return GuiFragment.init(position);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
