package com.robusta.photoweatherapp.model.cloud;

import com.robusta.photoweatherapp.model.dto.WeatherResponse;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Eslam Hussein on 10/27/17.
 */

public interface WeatherServices {

    @GET("weather")
    Maybe<WeatherResponse> getWeather(@Query("lat") String lat, @Query("lon") String lon,
                                      @Query("appid") String appId);
}
