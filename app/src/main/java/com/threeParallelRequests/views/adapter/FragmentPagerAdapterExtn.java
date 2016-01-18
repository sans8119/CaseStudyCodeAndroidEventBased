package com.threeParallelRequests.views.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;

import com.threeParallelRequests.views.fragment.MainActivityFragment;

public class FragmentPagerAdapterExtn extends FragmentStatePagerAdapter {
    private Context mContext;
    private String[] data;
    private static final int TAB_10th_CHAR=0;
    private static final int TAB_EVERY_10th_CHAR=1;
    private static final int TAB_WORD_COUNT=2;

    public void setData(String[] data) {
        this.data = data;
    }

    public FragmentPagerAdapterExtn(AppCompatActivity context) {
        super(context.getSupportFragmentManager());
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        MainActivityFragment fragment = new MainActivityFragment();
        Bundle args = new Bundle();
        args.putInt("page_position", position + 1);
        String string = "no data here";
        if (position == TAB_10th_CHAR) {
            if (string != null && string.length() > 0) {
                string = data[0];
                string = (string.split("\n")[0]);
            }
        } else if (position == TAB_EVERY_10th_CHAR) {
            string = data[0];
        } else if (position == TAB_WORD_COUNT) {
            string = data[1];
        }
        fragment.data = string;
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case TAB_10th_CHAR:
                return "10th Char";
            case TAB_EVERY_10th_CHAR:
                return "Every 10th Char";
            case TAB_WORD_COUNT:
                return "Word Count";
        }
        return "";
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}
