package com.example.androidlesson3.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import com.example.androidlesson3.App;
import com.example.androidlesson3.IntroActivity;
import com.example.androidlesson3.R;
import com.example.androidlesson3.data.IBoredApiClient;
import com.example.androidlesson3.models.BoredAction;

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

    @BindView(R.id.bored_accessibility)
    TextView accessibility;

    @BindView(R.id.bored_type)
    TextView type;

    @BindView(R.id.bored_participants)
    TextView participants;

    @BindView(R.id.bored_price)
    TextView price;

    @BindView(R.id.bored_link)
    TextView link;

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
    ProgressBar progressBar;

    @BindView(R.id.main_spinner)
    Spinner mainSpinner;

    @BindView(R.id.accessibility_progress)
    ProgressBar progressBarAccess;

    @BindView(R.id.participants_image)
    ImageView participantsImageView;

    @BindView(R.id.price_image)
    ImageView priceImageView;


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
                selected = mainSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        //endregion
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

    }
    //endregion
    //region Show/hide views


    private void hideLoading() {
        showProgress();
        activity.setVisibility(View.INVISIBLE);
        accessibility.setVisibility(View.INVISIBLE);
        type.setVisibility(View.INVISIBLE);
        participants.setVisibility(View.INVISIBLE);
        price.setVisibility(View.INVISIBLE);
        link.setVisibility(View.INVISIBLE);
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
        accessibility.setVisibility(View.INVISIBLE);
        type.setVisibility(View.INVISIBLE);
        participants.setVisibility(View.INVISIBLE);
        price.setVisibility(View.INVISIBLE);
        link.setVisibility(View.GONE);
        imageView.setVisibility(View.VISIBLE);
        priceImageView.setVisibility(View.INVISIBLE);
        participantsImageView.setVisibility(View.INVISIBLE);
        Toast.makeText(MainActivity.this, "No internet connection",
                Toast.LENGTH_LONG).show();
    }
    //endregion

    //region Refresh views

    @OnClick(R.id.refresh_button)
    public void refreshAction() {
        refreshButton.setOnClickListener(view -> {
            hideLoading();
            App.boredApiClient.getBoredAction(new IBoredApiClient.BoredActionCallback() {
                @Override
                public void onSuccess(BoredAction action) {
                    showLoading();
                    showViewsOnSuccess();
                    if (action.getParticipants() == 1) {
                        participantsImageView.setImageResource(R.drawable.employee);
                    } else if (action.getParticipants() == 2) {
                        participantsImageView.setImageResource(R.drawable.employees);
                    } else if (action.getParticipants() == 3) {
                        participantsImageView.setImageResource(R.drawable.employee2);
                    } else if (action.getParticipants() == 4) {
                        participantsImageView.setImageResource(R.drawable.group);
                    } else participantsImageView.setImageResource(R.drawable.group);

                    float num = action.getAccessibility() * 100;
                    int a = Math.round(num);

                    progressBarAccess.setProgress(a);
                    activity.setText(action.getActivity());
                    accessibility.setText(String.valueOf(a));
                    type.setText(selected);
                    participants.setText(String.valueOf(action.getParticipants()));
                    price.setText(String.valueOf(action.getPrice()));
                    link.setText(action.getLink());
                    imageLikeView.setImageDrawable(getResources().getDrawable(icon_blue));

                    Toast.makeText(MainActivity.this, "Updated",
                            Toast.LENGTH_SHORT).show();
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
        progressBar.setVisibility(View.GONE);
    }

    private void showLoading() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void showViewsOnSuccess() {
        activity.setVisibility(View.VISIBLE);
        accessibility.setVisibility(View.VISIBLE);
        type.setVisibility(View.VISIBLE);
        participants.setVisibility(View.VISIBLE);
        price.setVisibility(View.VISIBLE);
        link.setVisibility(View.GONE);
        participantsImageView.setVisibility(View.VISIBLE);
        priceImageView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        progressBarAccess.setVisibility(View.VISIBLE);
        rangePrice.setVisibility(View.VISIBLE);
        rangeAccessibility.setVisibility(View.VISIBLE);
        rangeParticipants.setVisibility(View.VISIBLE);
    }
}
