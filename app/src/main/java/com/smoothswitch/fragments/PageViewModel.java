package com.smoothswitch.fragments;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.arch.core.util.Function;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.smoothswitch.R;

public class PageViewModel extends ViewModel {

    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private LiveData<String> mText = Transformations.map(mIndex, new Function<Integer, String>() {
        @Override
        public String apply(Integer input) {
            return "Hello world from section: " + input;
        }
    });

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public LiveData<String> getText() {
        return mText;
    }

    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public static class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final Context mContext;
        @StringRes
        private static final int[] TAB_TITLES = new int[]{R.string.tab_0_title,
                R.string.tab_1_title, R.string.tab_2_title, R.string.tab_3_title};

        public SectionsPagerAdapter(Context context, FragmentManager fm) {
            super(fm);
            mContext = context;
        }

        @Override
        public Fragment getItem(int index) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a MyPlacesFragment (defined as a static inner class below).
            switch (index) {
                case 0:
                    return new HomeFragment();
                case 1:
                    return new MyPlacesFragment();
                case 2:
                    return new PlacePickerFragment();
                case 3:
                    return new MyPlacesFragment();
                default:
                    return null;

            }
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int index) {
            return mContext.getResources().getString(TAB_TITLES[index]);
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}