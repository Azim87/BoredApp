package com.example.androidlesson3.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.androidlesson3.main.MainActivity;
import com.example.androidlesson3.R;
import com.example.androidlesson3.core.CoreFragment;
import com.example.androidlesson3.models.IntroModel;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

public class BoredActionFragment extends CoreFragment {
    private final static String ARG_TITLE = "argTitle";
    private final static String ARG_DESC = "argDesc";
    private final static String ARG_PHOTO = "argPhoto";
    private static final String ARG_SECTION_NUMBER = "page_section";
    private static final String SAVE_INTRO = "intro";



    public static Fragment newInstance(IntroModel model) {
        Fragment fragment = new BoredActionFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TITLE, model.getTitle());
        args.putInt(ARG_DESC, model.getDescription());
        args.putInt(ARG_PHOTO, model.getPhoto());
        args.putInt(ARG_SECTION_NUMBER , model.getSectionNumber());
        fragment.setArguments(args);
        return fragment;
    }

    //region viewsId
    @BindView(R.id.intro_title)
    TextView textViewTitle;

    @BindView(R.id.intro_desc)
    TextView textViewDescription;

    @BindView(R.id.intro_photo)
    ImageView imageViewPhoto;

    @BindView(R.id.finish_button)
    Button finishButton;
    //endregion

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this, view);

        textViewTitle.setText(getArguments().getInt(ARG_TITLE));
        textViewDescription.setText(getArguments().getInt(ARG_DESC));
        imageViewPhoto.setImageResource(getArguments().getInt(ARG_PHOTO));
        int number =  getArguments().getInt(ARG_SECTION_NUMBER);

        switch (number){
            case 0:
            case 1:
            case 2:
                finishButton.setVisibility(View.GONE);
                break;
            case 3:
                finishButton.setVisibility(View.VISIBLE);
                break;
        }
        finishButton.setOnClickListener(view1 -> {
            startActivity(new Intent(getContext(), MainActivity.class));
            getActivity().finish();
            saveState();
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.bored_action_fragment_list;
    }

    private void saveState() {
        SharedPreferences preferences = getContext().getSharedPreferences(SAVE_INTRO, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(SAVE_INTRO, false);
        editor.apply();
    }
}
