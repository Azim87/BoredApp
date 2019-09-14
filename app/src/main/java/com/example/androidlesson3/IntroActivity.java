package com.example.androidlesson3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.androidlesson3.adapters.ViewPagerAdapter;
import com.example.androidlesson3.models.IntroModel;
import java.util.ArrayList;


public class IntroActivity extends AppCompatActivity {
    private ViewPager pager;
    private ArrayList<IntroModel> models;
    private LinearLayout linearLayoutDots;
    private ImageView[] mDots;
    private int dotsCount;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);


        pager = findViewById(R.id.main_view_pager);
        linearLayoutDots = findViewById(R.id.view_pager_dots_layout);

        models = new ArrayList<>();

        models.add(new IntroModel(R.string.app_name1, R.string.app_desc1, 0, R.drawable.android_1));
        models.add(new IntroModel(R.string.app_name2, R.string.app_desc1, 1, R.drawable.android_2));
        models.add(new IntroModel(R.string.app_name3, R.string.app_desc1, 2, R.drawable.android_3));
        models.add(new IntroModel(R.string.app_name4, R.string.app_desc1, 3, R.drawable.android_4));
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), models);

        pager.setAdapter(viewPagerAdapter);
        dotsCount = viewPagerAdapter.getCount();
        slideDotes();
    }

    private void slideDotes() {
        if (linearLayoutDots != null) {
            linearLayoutDots.removeAllViews();
        }
        mDots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            mDots[i] = new ImageView(this);
            mDots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonactive_dots));

            LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            lParams.setMargins(4, 0, 4, 0);
            linearLayoutDots.addView(mDots[i], lParams);
            mDots[0].setImageDrawable(getResources().getDrawable(R.drawable.active_dots));

            pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    for (int i = 0; i < dotsCount; i++) {
                        mDots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonactive_dots));
                    }
                    mDots[position].setImageDrawable(getResources().getDrawable(R.drawable.active_dots));
                }

                @Override
                public void onPageSelected(int position) {
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
        }
    }
}
