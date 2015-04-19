package com.yebimom.android.yebimom.ui.login;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.ArrayList;

/**
 * com.yebimom.android.yebimom.ui.login Need Comment!
 */
public class LoginViewPagerAdapter extends FragmentStatePagerAdapter {

    private final static String TAG = LoginViewPagerAdapter.class.getSimpleName();

    private ArrayList<Fragment> arrayList;

    public LoginViewPagerAdapter(FragmentManager fm) {
        super(fm);

        arrayList = new ArrayList<>();
        arrayList.add(new LoginMainFragment());
        arrayList.add(new LoginFragment());
    }

    @Override
    public Fragment getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

}