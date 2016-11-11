package com.benjagarrido.guedr.model;

/**
 * Created by benjamingarridobarreiro on 7/11/16.
 */

public class Forecast {
    // Members  m-
    // Static   s-
    private float mMaxTemp;
    private float mMimTemp;
    private float mHumidity;
    private String mDescription;
    private int mIcon;

    public Forecast(float maxTemp, float mimTemp, float humidity, String description, int icon) {
        mMaxTemp = maxTemp;
        mMimTemp = mimTemp;
        mHumidity = humidity;
        mDescription = description;
        mIcon = icon;
    }

    public void setMaxTemp(float maxTemp) {
        mMaxTemp = maxTemp;
    }

    public void setMimTemp(float mimTemp) {
        mMimTemp = mimTemp;
    }

    public void setHumidity(float humidity) {
        mHumidity = humidity;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public void setIcon(int icon) {
        mIcon = icon;
    }

    public float getMaxTemp() {
        return mMaxTemp;
    }

    public float getMimTemp() {
        return mMimTemp;
    }

    public float getHumidity() {
        return mHumidity;
    }

    public String getDescription() {
        return mDescription;
    }

    public int getIcon() {
        return mIcon;
    }
}
