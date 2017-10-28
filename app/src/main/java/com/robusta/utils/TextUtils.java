package com.robusta.utils;


import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.StringRes;

import com.robusta.App;
import com.robusta.photoweatherapp.R;


public class TextUtils {

    private static final String EMPTY_STRING_PATTERN = "^$|\\s+";

    public static String getString(@StringRes int resId) {
        return App.get().getString(resId);
    }

    public static float getDimension(@DimenRes int resId) {
        return App.get().getResources().getDimension(resId);
    }


    public static float getColor(@ColorRes int resId) {
        return App.get().getResources().getColor(resId);
    }


    public static boolean isEmptyString(String str) {
        if (str == null || str.length() == 0 ||
                str.matches(EMPTY_STRING_PATTERN)) {
            return true;
        }
        return false;
    }


    public static String formattingCountry(String country) {
        return String.format(getString(R.string.welcome_to_country), country);
    }

    public static String formattingWeatherTemp(double temp) {

        return String.format(getString(R.string.weather_temp), String.valueOf(temp));

    }

    public static String formattingWeatherWind(double windSpeed) {

        return String.format(getString(R.string.wind_speed), String.valueOf(windSpeed));

    }

}
