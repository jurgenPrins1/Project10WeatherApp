package com.example.weatherapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {

    @GET("weather?appid=e1dd8f4c3cbc23a29067dc98c2c316b0&units=metric")
    Call<OpenWeatherMap>getWeatherWithLocation(@Query("lat") double lat, @Query("lon") double lon);

    @GET("weather?appid=e1dd8f4c3cbc23a29067dc98c2c316b0&units=metric")
    Call<OpenWeatherMap>getWeatherWithCity(@Query("q") String name);


}
