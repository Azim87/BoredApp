package com.example.androidlesson3.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidlesson3.App;
import com.example.androidlesson3.IntroActivity;
import com.example.androidlesson3.R;
import com.example.androidlesson3.data.IBoredApiClient;
import com.example.androidlesson3.models.ActionRequestOptions;
import com.example.androidlesson3.models.BoredAction;
import com.innovattic.rangeseekbar.RangeSeekBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.androidlesson3.R.drawable.icon_blue;

public class MainActivity extends AppCompatActivity {

    private static final String SAVE_INTRO = "intro";
    private boolean isChanged = true;
    private String selected;

    //region    Init views
    @BindView(R.id.refresh_button)
    Button refreshButton;

    @BindView(R.id.bored_activity)
    TextView activity;


    @BindView(R.id.bored_type)
    TextView type;

    @BindView(R.id.like_image_view)
    ImageView imageLikeView;

    @BindView(R.id.bored_image)
    ImageView imageView;

    @BindView(R.id.price)
    TextView rangePrice;

    @BindView(R.id.participants)
    TextView rangeParticipants;

    @BindView(R.id.accessibility)
    TextView rangeAccessibility;

    @BindView(R.id.refresh_progress_bar)
    ProgressBar progressBarLoading;

    @BindView(R.id.main_spinner)
    Spinner mainSpinner;

    @BindView(R.id.accessibility_progress)
    ProgressBar progressBarAccess;

    @BindView(R.id.participants_image)
    ImageView participantsImageView;

    @BindView(R.id.price_image)
    ImageView priceImageView;

    @BindView(R.id.seekBar_12)
    RangeSeekBar seekBarPrice;

    @BindView(R.id.seekBar_2)
    RangeSeekBar seekBarAccess;

    //endregion

    private static final String[] category =
            {"Type", "education", "recreational", "social", "diy", "charity", "cooking", "relaxation", "music", "busywork"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        refreshAction();
        changeLikeImage();

        SharedPreferences preferences = getSharedPreferences(SAVE_INTRO, MODE_PRIVATE);
        boolean shown = preferences.getBoolean(SAVE_INTRO, true);

        if (shown) {
            startActivity(new Intent(this, IntroActivity.class));
            finish();
        }

        //region Spinner adapter

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item, category) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                    tv.setTextSize(20);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mainSpinner.setAdapter(adapter);
        mainSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selected = (String) mainSpinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        //endregion;
    }
    //region Like/Dislike

    @OnClick(R.id.like_image_view)
    void changeLikeImage() {
        imageLikeView.setOnClickListener(view -> {
            Animation anim = AnimationUtils.loadAnimation(this, R.anim.like_anim);
            view.startAnimation(anim);
            if (isChanged) {
                imageLikeView.setImageResource(R.drawable.filled_icon);
            } else {
                imageLikeView.setImageResource(R.drawable.icon_blue);
            }
            isChanged = !isChanged;
        });

        seekBarPrice.setMaxThumbValue(1);
        seekBarPrice.setMinThumbValue(0);

        seekBarAccess.setMinThumbValue(1);
        seekBarAccess.setMaxThumbValue(0);


    }
    //endregion
    //region Show/hide views


    private void hideLoadingViews() {
        showProgress();
        activity.setVisibility(View.INVISIBLE);
        type.setVisibility(View.INVISIBLE);
        imageView.setVisibility(View.INVISIBLE);
        participantsImageView.setVisibility(View.INVISIBLE);
        priceImageView.setVisibility(View.INVISIBLE);
        progressBarAccess.setVisibility(View.INVISIBLE);
        rangePrice.setVisibility(View.INVISIBLE);
        rangeAccessibility.setVisibility(View.INVISIBLE);
        rangeParticipants.setVisibility(View.INVISIBLE);
    }

    private void showError() {
        hideProgress();
        activity.setVisibility(View.INVISIBLE);
        type.setVisibility(View.INVISIBLE);
        imageView.setVisibility(View.VISIBLE);
        priceImageView.setVisibility(View.INVISIBLE);
        participantsImageView.setVisibility(View.INVISIBLE);
        Toast.makeText(MainActivity.this, "No internet connection",
                Toast.LENGTH_LONG).show();
    }

    private void showViewsOnSuccess() {
        activity.setVisibility(View.VISIBLE);
        type.setVisibility(View.VISIBLE);
        participantsImageView.setVisibility(View.VISIBLE);
        priceImageView.setVisibility(View.VISIBLE);
        progressBarLoading.setVisibility(View.INVISIBLE);
        progressBarAccess.setVisibility(View.VISIBLE);
        rangePrice.setVisibility(View.VISIBLE);
        rangeAccessibility.setVisibility(View.VISIBLE);
        rangeParticipants.setVisibility(View.VISIBLE);
    }
    //endregion

    //region Refresh views

    @OnClick(R.id.refresh_button)
    public void refreshAction() {
        refreshButton.setOnClickListener(view -> {
            hideLoadingViews();

            float minPrice = seekBarPrice.getMinThumbValue() / 100.0f;
            float maxPrice = seekBarPrice.getMaxThumbValue() / 100.0f;

            float minAccess = seekBarAccess.getMinThumbValue() /100.0f;
            float maxAccess = seekBarAccess.getMaxThumbValue() /100.0f;

            ActionRequestOptions requestOptions = new ActionRequestOptions(
                    selected,
                    minPrice,
                    maxPrice,
                    minAccess,
                    maxAccess
            );

            Log.d("ohoho", "minprice " + minPrice + "maxpcie " + maxPrice + "minaccess " + minAccess + "maxaccess " + maxAccess);

            App.boredApiClient.getBoredAction(requestOptions, new IBoredApiClient.BoredActionCallback() {
                @Override
                public void onSuccess(BoredAction action) {
                    showViewsOnSuccess();
                    showLoadingProgress();

                    /* participants */
                    if (action.getParticipants() == 0) {
                        Toast.makeText(MainActivity.this, "No any participants", Toast.LENGTH_SHORT).show();
                    }
                    else if (action.getParticipants() == 1) {
                        participantsImageView.setImageResource(R.drawable.employee);
                    }
                    else if (action.getParticipants() == 2) {
                        participantsImageView.setImageResource(R.drawable.employees);
                    }
                    else if (action.getParticipants() == 3) {
                        participantsImageView.setImageResource(R.drawable.employee2);
                    }
                    else if (action.getParticipants() == 4) {
                        participantsImageView.setImageResource(R.drawable.group);

                    }
                    else participantsImageView.setImageResource(R.drawable.group);

                    /*price*/
                    if (action.getPrice() == 0.0){
                        priceImageView.setImageResource(R.drawable.free_image);
                    }
                    else {
                        priceImageView.setImageResource(R.drawable.dollar_pay);
                    }

                    float round = action.getAccessibility() * 100;
                    progressBarAccess.setProgress((int) round);

                    activity.setText(action.getActivity());
                    type.setText(selected);
                    imageLikeView.setImageDrawable(getResources().getDrawable(icon_blue));

                    Log.d("ohoho", "action " + action.toString());

                    Toast.makeText(MainActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Exception e) {
                    showError();
                }
            });
        });
    }
    //endregion

    private void hideProgress() {
        progressBarLoading.setVisibility(View.GONE);
    }

    private void showLoadingProgress() {
        progressBarLoading.setVisibility(View.INVISIBLE);
    }

    private void showProgress() {
        progressBarLoading.setVisibility(View.VISIBLE);
    }

}
