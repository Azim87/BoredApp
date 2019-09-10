package com.example.androidlesson3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.androidlesson3.data.IBoredApiClient;
import com.example.androidlesson3.data.RequestActionOptions;
import com.example.androidlesson3.models.BoredAction;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import static com.example.androidlesson3.R.drawable.icon_blue;

public class MainActivity extends AppCompatActivity {

    private static final String SAVE_INTRO = "intro";
    private boolean isChanged = true;
    private String selected;
    float minPrice;

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

    @BindView(R.id.seekBar_1)
    SeekBar priceSeekBar;

    @BindView(R.id.seekBar_2)
    SeekBar accessibilitySeekBar;
    //endregion

    private static final String[] category =
            {"Type", "education", "recreational", "social", "DIY", "charity", "cooking", "relaxation", "music", "busywork"};

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
        priceSeekBar.setProgress((int) 0.0);
        priceSeekBar.setMax((int) 1.0);

        minPrice = priceSeekBar.getProgress();


        //endregion
    }
        //region Like/Dislike

    @OnClick(R.id.like_image_view)
    void changeLikeImage() {
        imageLikeView.setOnClickListener(view -> {
            int icon;
            if (isChanged) {
                isChanged = false;
                icon = R.drawable.filled_icon;
            } else {
                isChanged = true;
                icon = icon_blue;
            }
            imageLikeView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), icon));
        });

    }
    //endregion

        //region Show/hide views

    private void showLoading() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void hideLoading() {
        progressBar.setVisibility(View.VISIBLE);
        activity.setVisibility(View.INVISIBLE);
        accessibility.setVisibility(View.INVISIBLE);
        type.setVisibility(View.INVISIBLE);
        participants.setVisibility(View.INVISIBLE);
        price.setVisibility(View.INVISIBLE);
        link.setVisibility(View.GONE);
        imageView.setVisibility(View.INVISIBLE);
        rangePrice.setVisibility(View.INVISIBLE);
        rangeAccessibility.setVisibility(View.INVISIBLE);
        rangeParticipants.setVisibility(View.INVISIBLE);
    }

    private void showError() {
        progressBar.setVisibility(View.GONE);
        activity.setVisibility(View.INVISIBLE);
        accessibility.setVisibility(View.INVISIBLE);
        type.setVisibility(View.INVISIBLE);
        participants.setVisibility(View.INVISIBLE);
        price.setVisibility(View.INVISIBLE);
        link.setVisibility(View.GONE);
        imageView.setVisibility(View.VISIBLE);
        Toast.makeText(MainActivity.this, "No internet connection",
                Toast.LENGTH_LONG).show();
    }
    //endregion

        //region Refresh views

    @OnClick(R.id.refresh_button)
    public void refreshAction() {
        refreshButton.setOnClickListener(view -> {

            hideLoading();
            RequestActionOptions actionOptions = new RequestActionOptions(
                selected,
                    minPrice,
                    0.6f,
                    1f,
                    4f);

            App.boredApiClient.getBoredAction(actionOptions, new IBoredApiClient.BoredActionCallback() {
                @Override
                public void onSuccess(BoredAction action) {
                    Log.d("ololo", "onresponce: " + selected);
                    Log.d("ololo", "onresponce: " + actionOptions.getMinPrice());
                    showLoading();

                    activity.setText(action.getActivity());
                    accessibility.setText(String.valueOf(action.getAccessibility()));
                    type.setText(selected);
                    participants.setText(String.valueOf(action.getParticipants()));
                    price.setText(String.valueOf(action.getPrice()));
                    link.setText(action.getLink());
                    imageLikeView.setImageDrawable(getResources().getDrawable(icon_blue));

                    activity.setVisibility(View.VISIBLE);
                    accessibility.setVisibility(View.VISIBLE);
                    type.setVisibility(View.VISIBLE);
                    participants.setVisibility(View.VISIBLE);
                    price.setVisibility(View.VISIBLE);
                    link.setVisibility(View.GONE);
                    progressBar.setVisibility(View.INVISIBLE);
                    rangePrice.setVisibility(View.VISIBLE);
                    rangeAccessibility.setVisibility(View.VISIBLE);
                    rangeParticipants.setVisibility(View.VISIBLE);

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
}
