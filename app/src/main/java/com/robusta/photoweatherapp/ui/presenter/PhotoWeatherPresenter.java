package com.robusta.photoweatherapp.ui.presenter;

import android.graphics.Bitmap;
import android.net.Uri;

import com.robusta.base.presenter.BasePresenter;
import com.robusta.photoweatherapp.model.dto.Photo;
import com.robusta.photoweatherapp.model.dto.WeatherResponse;
import com.robusta.photoweatherapp.ui.view.PhotoWeatherView;

/**
 * Created by Eslam Hussein on a10/18/2016.
 */

public abstract class PhotoWeatherPresenter extends BasePresenter<PhotoWeatherView> {
    public abstract void loadWeather(String lat, String lon);

    public abstract void prepareImageToShare(WeatherResponse weatherResponse, Uri imageUri);

    public abstract void getImageHistory();

    public abstract void getImage(Photo photo);
}
