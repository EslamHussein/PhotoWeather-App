package com.robusta.photoweatherapp.business;

import com.robusta.photoweatherapp.model.dto.WeatherResponse;

import io.reactivex.Maybe;
import io.reactivex.Observable;

/**
 * Created by Eslam Hussein on 10/27/17.
 */

public interface PhotoWeatherRepo {

    Maybe<WeatherResponse> getWeather(String lat, String lon);

}
