package com.example.androidlesson3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.androidlesson3.adapters.ViewPagerAdapter;
import com.example.androidlesson3.main.MainActivity;
import com.example.androidlesson3.models.IntroModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class IntroActivity extends AppCompatActivity {

    //region initViews
    private ViewPagerAdapter viewPagerAdapter;
    private ImageView[] mDots;
    private int dotsCount;

    @BindView(R.id.main_view_pager)
    ViewPager pager;

    @BindView(R.id.view_pager_dots_layout)
    LinearLayout linearLayoutDots;

    @BindView(R.id.skip_button)
    Button skipButton;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        ButterKnife.bind(this);

        ArrayList<IntroModel> models = new ArrayList<>();

        models.add(new IntroModel(R.string.app_name1, R.string.app_desc1, 0, R.drawable.android_1));
        models.add(new IntroModel(R.string.app_name2, R.string.app_desc1, 1, R.drawable.android_2));
        models.add(new IntroModel(R.string.app_name3, R.string.app_desc1, 2, R.drawable.android_3));
        models.add(new IntroModel(R.string.app_name4, R.string.app_desc1, 3, R.drawable.android_4));

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), models);

        pager.setAdapter(viewPagerAdapter);
        dotsCount = viewPagerAdapter.getCount();

        slideDotes();
        skipOnPages();
    }

    //region SkipOnIntroPages
    @OnClick(R.id.skip_button)
    public void skipOnPages() {
        skipButton.setOnClickListener(v -> {
            if (pager.getCurrentItem() == viewPagerAdapter.getCount() -1 ){
                startActivity(new Intent(this, MainActivity.class));
            }else {
                pager.setCurrentItem(viewPagerAdapter.getCount() +1);
            }
        });
    }
    //endregion

    //region ViewPagerDotes
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
    //endregion
}
