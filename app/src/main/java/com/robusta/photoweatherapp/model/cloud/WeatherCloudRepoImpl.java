package com.robusta.photoweatherapp.model.cloud;

import com.robusta.base.repo.cloud.BaseCloudRepo;
import com.robusta.base.repo.cloud.CloudConfig;
import com.robusta.photoweatherapp.business.PhotoWeatherRepo;
import com.robusta.photoweatherapp.model.dto.WeatherResponse;

import io.reactivex.Maybe;
import io.reactivex.Observable;

/**
 * Created by Eslam Hussein on 10/27/17.
 */

public class WeatherCloudRepoImpl extends BaseCloudRepo implements PhotoWeatherRepo {

    @Override
    public Maybe<WeatherResponse> getWeather(String lat, String lon) {

        return create(WeatherServices.class).getWeather(lat, lon, CloudConfig.APP_ID);
    }
}
