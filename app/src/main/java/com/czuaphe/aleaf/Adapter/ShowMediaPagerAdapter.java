package com.czuaphe.aleaf.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.czuaphe.aleaf.Bean.Media;
import com.czuaphe.aleaf.Fragment.ImageFragment;

import java.util.List;

/**
 * Created by Czuaphe on 2017/5/9.
 */

public class ShowMediaPagerAdapter extends FragmentStatePagerAdapter {


    private List<Media> mediaList;
    private SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();


    public ShowMediaPagerAdapter(FragmentManager fm, List<Media> list) {
        super(fm);
        mediaList = list;
    }

    @Override
    public int getCount() {
        return mediaList.size();
    }

    @Override
    public Fragment getItem(int position) {
        Media media = mediaList.get(position);

        return ImageFragment.getInstance(media);

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

}
