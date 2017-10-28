package com.robusta.photoweatherapp.business;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;

import com.robusta.App;
import com.robusta.photoweatherapp.R;
import com.robusta.photoweatherapp.model.cloud.WeatherCloudRepoImpl;
import com.robusta.photoweatherapp.model.dto.Photo;
import com.robusta.photoweatherapp.model.dto.WeatherResponse;
import com.robusta.photoweatherapp.model.file.PhotoFileRepoImpl;
import com.robusta.utils.ImageUtils;
import com.robusta.utils.TextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Eslam Hussein on 10/27/17.
 */

public class PhotoWeatherBusiness {
    WeatherCloudRepoImpl weatherCloudRepo;
    PhotoFileRepoImpl photoFileRepo;

    public PhotoWeatherBusiness(WeatherCloudRepoImpl weatherCloudRepo, PhotoFileRepoImpl photoFileRepo) {
        this.weatherCloudRepo = weatherCloudRepo;
        this.photoFileRepo = photoFileRepo;
    }

    public Maybe<WeatherResponse> getWeather(String lat, String lon) {
        return weatherCloudRepo.getWeather(lat, lon).
                observeOn(AndroidSchedulers.mainThread()).
                subscribeOn(Schedulers.io());
    }


    public Observable<Bitmap> prepareImageToShare(final WeatherResponse weatherResponse, final Uri imageUri) {
        return Observable.fromCallable(new Callable<Bitmap>() {
            @Override
            public Bitmap call() throws Exception {
                return MediaStore.Images.Media.getBitmap(App.get().getContentResolver(), imageUri);
            }
        }).map(new Function<Bitmap, Bitmap>() {
            @Override
            public Bitmap apply(@NonNull Bitmap bitmap) throws Exception {

                List<String> strings = new ArrayList<String>();
                strings.add(TextUtils.formattingCountry(weatherResponse.getSys().getCountry()));
                strings.add(TextUtils.formattingWeatherTemp(weatherResponse.getMain().getTemp()));
                strings.add(TextUtils.formattingWeatherWind(weatherResponse.getWind().getSpeed()));
                Bitmap bitmapToSave = ImageUtils.drawTextToBitmap(bitmap, strings);
                photoFileRepo.saveImage(bitmapToSave);
                return bitmapToSave;
            }
        }).observeOn(AndroidSchedulers.mainThread()).
                subscribeOn(Schedulers.computation());


    }

    public Single<List<Photo>> getImageHistory() {
        File[] files = photoFileRepo.getHistory();
        if (files == null)
            files = new File[0]; //  // TODO: 10/28/17 need more enhancement


        return Observable.fromArray(files).flatMap(new Function<File, Observable<Photo>>() {
            @Override
            public Observable<Photo> apply(@NonNull File file) throws Exception {

                int thumbnail_width_size = (int) TextUtils.getDimension(R.dimen.thumbnail_width_size);
                int thumbnail_height_size = (int) TextUtils.getDimension(R.dimen.thumbnail_height_size);

                Bitmap thumbImage = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(file.getAbsolutePath()),
                        thumbnail_width_size, thumbnail_height_size);

                Photo photo = new Photo(file.getName(), file.getAbsolutePath(), thumbImage);
                return Observable.just(photo);

            }
        }).toList().observeOn(AndroidSchedulers.mainThread()).
                subscribeOn(Schedulers.computation());

    }

    public Single<Bitmap> getImage(final Photo photo) {

        return Single.fromCallable(new Callable<Bitmap>() {
            @Override
            public Bitmap call() throws Exception {

                File imgFile = photoFileRepo.getImage(photo);
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                return myBitmap;

            }
        }).observeOn(AndroidSchedulers.mainThread()).
                subscribeOn(Schedulers.computation());
    }

}
