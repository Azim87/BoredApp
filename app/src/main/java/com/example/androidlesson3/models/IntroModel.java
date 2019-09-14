package com.example.androidlesson3.models;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

public class IntroModel {
    @StringRes
    private int title;

    @StringRes
    private int description;

    @StringRes
    private int sectionNumber;

    @DrawableRes
    private int photo;

    public IntroModel(int title, int description, int sectionNumber, int photo) {
        this.title = title;
        this.description = description;
        this.sectionNumber = sectionNumber;
        this.photo = photo;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getDescription() {
        return description;
    }

    public void setDescription(int description) {
        this.description = description;
    }

    public int getSectionNumber() {
        return sectionNumber;
    }

    public void setSectionNumber(int sectionNumber) {
        this.sectionNumber = sectionNumber;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}

