package com.robusta.photoweatherapp.ui.view;

import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;

import com.robusta.base.view.MvpView;
import com.robusta.photoweatherapp.model.dto.Photo;
import com.robusta.photoweatherapp.model.dto.WeatherResponse;

import java.util.List;

/**
 * Created by Eslam Hussein on 10/27/17.
 */

public interface PhotoWeatherView extends MvpView {


    void showLoading();

    void hideLoading();

    void showBlockingLoading();

    void hideBlockingLoading();

    void showInlineError(String error);

    void showWeatherData(WeatherResponse weatherResponse);

    void enableCamera();

    void shareImageToFaceBook(Bitmap bitmap);

    void showPermissionDenied();

    void showImagesHistory(List<Photo> photos);

    void showImageFullSize(Bitmap bitmap);

    void showImageFullSizeError(@DrawableRes int tempImageResId);


}
