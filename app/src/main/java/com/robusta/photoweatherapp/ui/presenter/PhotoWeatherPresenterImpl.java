package com.robusta.photoweatherapp.ui.presenter;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import com.robusta.photoweatherapp.R;
import com.robusta.photoweatherapp.business.PhotoWeatherBusiness;
import com.robusta.photoweatherapp.model.dto.Photo;
import com.robusta.photoweatherapp.model.dto.WeatherResponse;

import java.util.List;

import io.reactivex.MaybeObserver;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by Eslam Hussein on a10/18/2016.
 */

public class PhotoWeatherPresenterImpl extends PhotoWeatherPresenter {
    public static final String TAG = "PresenterImpl";

    PhotoWeatherBusiness business;

    public PhotoWeatherPresenterImpl(PhotoWeatherBusiness business) {
        this.business = business;
    }

    @Override
    public void loadWeather(String lat, String lon) {

        getView().showLoading();

        business.getWeather(lat, lon).subscribe(new MaybeObserver<WeatherResponse>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onSuccess(@NonNull WeatherResponse weatherResponse) {

                if (!isViewAttached())
                    return;

                getView().showWeatherData(weatherResponse);
                getView().hideLoading();
                getView().enableCamera();


            }

            @Override
            public void onError(@NonNull Throwable e) {
                if (!isViewAttached())
                    return;

                getView().showInlineError(e.getMessage());
                getView().hideLoading();

            }

            @Override
            public void onComplete() {

            }
        });

    }

    @Override
    public void prepareImageToShare(WeatherResponse weatherResponse, Uri imageUri) {
        getView().showBlockingLoading();


        business.prepareImageToShare(weatherResponse, imageUri).subscribe(new Observer<Bitmap>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Bitmap bitmap) {

                if (!isViewAttached())
                    return;

                getView().shareImageToFaceBook(bitmap);
                getView().hideBlockingLoading();

            }

            @Override
            public void onError(@NonNull Throwable e) {
                if (!isViewAttached())
                    return;
                getView().showInlineError(e.getMessage());
                getView().hideBlockingLoading();


            }

            @Override
            public void onComplete() {
                if (!isViewAttached())
                    return;

                getView().hideBlockingLoading();


            }
        });
    }

    @Override
    public void getImageHistory() {
        business.getImageHistory().subscribe(new SingleObserver<List<Photo>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onSuccess(@NonNull List<Photo> photos) {
                if (!isViewAttached())
                    return;
                getView().showImagesHistory(photos);

            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "onError() returned: " + e.getMessage());

                if (!isViewAttached())
                    return;
                getView().showInlineError(e.getMessage());

            }
        });
    }

    @Override
    public void getImage(Photo photo) {

        business.getImage(photo).subscribe(new SingleObserver<Bitmap>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onSuccess(@NonNull Bitmap bitmap) {


                if (!isViewAttached())
                    return;
                getView().showImageFullSize(bitmap);
            }

            @Override
            public void onError(@NonNull Throwable e) {

                if (!isViewAttached())
                    return;
                getView().showImageFullSizeError(R.drawable.image_not_found);

            }
        });

    }


}
