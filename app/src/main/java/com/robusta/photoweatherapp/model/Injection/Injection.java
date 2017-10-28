package com.robusta.photoweatherapp.model.Injection;

import com.robusta.photoweatherapp.business.PhotoWeatherBusiness;
import com.robusta.photoweatherapp.model.cloud.WeatherCloudRepoImpl;
import com.robusta.photoweatherapp.model.file.PhotoFileRepoImpl;

/**
 * Created by Eslam Hussein on 10/28/17.
 */

public class Injection {

    public static PhotoWeatherBusiness providePhotoWeatherBusiness(WeatherCloudRepoImpl cloudRepo,
                                                                   PhotoFileRepoImpl photoFileRepo) {
        return new PhotoWeatherBusiness(cloudRepo, photoFileRepo);
    }

    public static WeatherCloudRepoImpl provideWeatherCloudRepoImpl() {
        return new WeatherCloudRepoImpl();
    }

    public static PhotoFileRepoImpl providePhotoFileRepoImpl() {
        return new PhotoFileRepoImpl();
    }

}
