package com.example.androidlesson3;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.androidlesson3.fragments.BoredActionFragment;
import com.example.androidlesson3.models.IntroModel;
import java.util.List;


public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<IntroModel> models;


    public ViewPagerAdapter(FragmentManager fm,
                            List<IntroModel> models) {
        super(fm);
        this.models = models;
    }

    @Override
    public Fragment getItem(int position) {
        return BoredActionFragment.newInstance(models.get(position));
    }

    @Override
    public int getCount() {
        return models.size();
    }
}
